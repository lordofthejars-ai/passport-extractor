package org.acme;

import ai.djl.MalformedModelException;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class PassportExtractorTest {


    @Inject
    PassportExtractor passportExtractor;

    @Test
    public void test() throws IOException, TranslateException {

        Image img = ImageFactory.getInstance().fromFile(Paths.get("src/test/resources/p2.jpg"));
        passportExtractor.extract(img);
    }

}
