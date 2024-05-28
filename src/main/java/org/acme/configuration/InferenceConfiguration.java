package org.acme.configuration;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.translator.YoloV8TranslatorFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import java.io.IOException;

@Singleton
public class InferenceConfiguration {

    private ZooModel<Image, DetectedObjects> model;

    @Startup
    public void initializeModel() throws ModelNotFoundException, MalformedModelException, IOException {
        Criteria<Image, DetectedObjects> criteria =
            Criteria.builder()
                .setTypes(Image.class, DetectedObjects.class)
                .optModelUrls("src/main/resources/passport_best.onnx")
                .optEngine("OnnxRuntime")
                .optArgument("width", 640)
                .optArgument("height", 640)
                .optArgument("resize", true)
                .optArgument("toTensor", true)
                .optArgument("applyRatio", true)
                .optArgument("threshold", 0.4f)
                // for performance optimization maxBox parameter can reduce number of
                // considered boxes from 8400
                .optArgument("maxBox", 8400)
                .optTranslatorFactory(new YoloV8TranslatorFactory())
                .optProgress(new ProgressBar())
                .build();

        this.model = criteria.loadModel();
    }

    @Produces
    ZooModel<Image, DetectedObjects> getZooModel() {
        return this.model;
    }

    @Produces
    @RequestScoped
    public Predictor<Image, DetectedObjects> predictor(ZooModel<Image, DetectedObjects> zooModel) {
        System.out.println("Create Predictor");
        return zooModel.newPredictor();
    }

    void close(@Disposes Predictor<Image, DetectedObjects>  predictor) {
        System.out.println("Closes Predictor");
        predictor.close();
    }

}
