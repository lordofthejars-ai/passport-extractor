package org.acme.image;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

class GreyScaleImageTransformation extends ImageTransformation {

    @Override
    public Mat transform(Mat src) {

        Mat grey = new Mat(src.rows(), src.cols(), src.type());
        Imgproc.cvtColor(src, grey, Imgproc.COLOR_RGB2GRAY, 0);

        return executeNext(grey);
    }
}
