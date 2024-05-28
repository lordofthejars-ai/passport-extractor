package org.acme.image;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.opencv.core.Mat;

@ApplicationScoped
public class ImageStorage {

    public static final String PASSPORT_DETECTED_PNG = "passport_detected.png";
    public static final String PASSPORTNUMBER_EX_JPG = "passportnumber_ex.jpg";
    public static final String EXPIRE_EX_JPG = "expire_ex.jpg";
    public static final String SURNAME_EX_JPG = "surname_ex.jpg";
    public static final String FIRSTNAME_EX_JPG = "firstname_ex.jpg";
    @Inject
    Path outputPath;

    public byte[] loadName() {
        try {
            return Files.readAllBytes(outputPath.resolve(FIRSTNAME_EX_JPG));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] loadSurname() {
        try {
            return Files.readAllBytes(outputPath.resolve(SURNAME_EX_JPG));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] loadExpireDate() {
        try {
            return Files.readAllBytes(outputPath.resolve(EXPIRE_EX_JPG));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] loadPassportNumber() {
        try {
            return Files.readAllBytes(outputPath.resolve(PASSPORTNUMBER_EX_JPG));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] loadPassportBoundaries() {
        try {
            return Files.readAllBytes(outputPath.resolve(PASSPORT_DETECTED_PNG));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveName(Image field) {
        OpenCVUtils.save((Mat) field.getWrappedImage(), outputPath.resolve(FIRSTNAME_EX_JPG));
    }

    public void saveSurname(Image field) {
        OpenCVUtils.save((Mat) field.getWrappedImage(), outputPath.resolve(SURNAME_EX_JPG));
    }

    public void saveExpireDate(Image field) {
        OpenCVUtils.save((Mat) field.getWrappedImage(), outputPath.resolve(EXPIRE_EX_JPG));
    }

    public void savePassportNumber(Image field) {
        OpenCVUtils.save((Mat) field.getWrappedImage(), outputPath.resolve(PASSPORTNUMBER_EX_JPG));
    }

    public void savePassportBoundaries(Image img, DetectedObjects detection) {
        if (detection.getNumberOfObjects() > 0) {
            img.drawBoundingBoxes(detection);
            Path output = outputPath.resolve(PASSPORT_DETECTED_PNG);
            try (OutputStream os = Files.newOutputStream(output)) {
                img.save(os, "png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
