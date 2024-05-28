package org.acme;


import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.io.IOException;

import java.nio.file.Paths;
import org.acme.image.ImageProcessor;

import org.junit.jupiter.api.Test;

@QuarkusTest
public class OcrServiceTest {


    @Inject
    ImageFactory imageFactory;

    @Inject
    ImageProcessor imageProcessor;

   @Test
    public void shouldExtractName() throws IOException {
        final Image image = imageFactory.fromFile(Paths.get("src/test/resources/fullnum.jpg"));
        System.out.println(imageProcessor.recognize(image));
    }

}
