package org.acme.image;

import ai.djl.modality.cv.ImageFactory;
import ai.djl.opencv.OpenCVImageFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
class ImageConfiguration {

    @Produces
    public Path outputDir() throws IOException {
        Path outputPath = Paths.get("target");
        Files.createDirectories(outputPath);

        return outputPath;
    }

    @Produces
    public ImageFactory createImageFactory() {
        return OpenCVImageFactory.getInstance();
    }

    @Produces
    public ImageTransformation createImageProcessorWorkflow() {
        GreyScaleImageTransformation greyScaleImageTransformation = new GreyScaleImageTransformation();
        BinarizationImageTransformation binarizationImageTransformation = new BinarizationImageTransformation();
        greyScaleImageTransformation.setNext(binarizationImageTransformation);


        return greyScaleImageTransformation;
    }

}
