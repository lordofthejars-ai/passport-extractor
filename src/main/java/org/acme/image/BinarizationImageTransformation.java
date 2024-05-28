package org.acme.image;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

class BinarizationImageTransformation extends ImageTransformation {

    @Override
    public Mat transform(Mat src) {
        Mat binary = new Mat(src.rows(), src.cols(), src.type());

        Imgproc.threshold(src, binary, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
        //Imgproc.adaptiveThreshold(src, binary, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 11, 2);

        return executeNext(binary);
    }
}
