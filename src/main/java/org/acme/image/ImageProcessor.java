package org.acme.image;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.BoundingBox;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.Rectangle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

@ApplicationScoped
public class ImageProcessor {

    @Inject
    OcrService ocrService;

    @Inject
    ImageTransformation imageTransformation;

    public String recognize(Image image) {

        Mat imageMat = (Mat) image.getWrappedImage();
        Mat preProcessedImage = this.imageTransformation.transform(imageMat);

        byte[] content = getBytes(preProcessedImage);
        return ocrService.scan(content);

    }

    public Image cropImage(Image img, DetectedObjects.DetectedObject result) {

        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();

        BoundingBox box = result.getBoundingBox();

        Rectangle rectangle = box.getBounds();
        int x = (int) (rectangle.getX() * imageWidth);
        int y = (int) (rectangle.getY() * imageHeight);
        int boxWidth = (int) (rectangle.getWidth() * imageWidth);
        int boxHeight = (int) (rectangle.getHeight() * imageHeight);

        /*int maximum = Math.max(boxWidth, boxHeight);
        int dx = ((maximum - boxWidth) / 2);
        int dy = ((maximum - boxHeight) / 2);*/

        int dx = 0;
        int dy=0;

        Image area = img.getSubImage(x - dx,
            y - dy,
            boxWidth + dx,
            boxHeight + dy);

        return area;

    }

    private byte[] getBytes(Mat preProcessedImage) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", preProcessedImage, matOfByte);
        return matOfByte.toArray();
    }
}
