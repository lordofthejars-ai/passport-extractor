package org.acme;

import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;

import ai.djl.translate.TranslateException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.acme.image.ImageProcessor;
import org.acme.image.ImageStorage;
import org.acme.image.OpenCVUtils;
import org.opencv.core.Mat;

@ApplicationScoped
public class PassportExtractor {

    @Inject
    Predictor<Image, DetectedObjects> predictor;

    @Inject
    ImageProcessor imageProcessor;

    @Inject
    ImageStorage imageStorage;

    public Passport extract(Image img) throws IOException, TranslateException {

        DetectedObjects detection = predictor.predict(img);
        Passport passport = new Passport();

        List<DetectedObjects.DetectedObject> passportItems = detection.items();
        for (DetectedObjects.DetectedObject item : passportItems) {
            switch (item.getClassName()) {
                case "firstname": {
                    final Image field = this.imageProcessor.cropImage(img, item);
                    imageStorage.saveName(field);
                    passport.name = this.imageProcessor.recognize(field).trim();
                    break;
                }

                case "surname": {
                    final Image field = this.imageProcessor.cropImage(img, item);
                    imageStorage.saveSurname(field);
                    passport.surname = this.imageProcessor.recognize(field).trim();
                    break;
                }

                case "passportnumber": {
                    final Image field = this.imageProcessor.cropImage(img, item);
                    imageStorage.savePassportNumber(field);
                    passport.passportNumber = this.imageProcessor.recognize(field).trim();
                    break;
                }

                case "expiredate": {
                    final Image field = this.imageProcessor.cropImage(img, item);
                    imageStorage.saveExpireDate(field);
                    String expireDate = this.imageProcessor.recognize(field).trim();
                    passport.expireDate = LocalDate.parse(expireDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    break;
                }
            }
        }

        imageStorage.savePassportBoundaries(img, detection);

        return passport;
    }
}
