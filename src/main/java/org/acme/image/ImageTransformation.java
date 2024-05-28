package org.acme.image;

import org.opencv.core.Mat;

abstract class ImageTransformation {

    private ImageTransformation next;

    public void setNext(ImageTransformation imageTransformation) {
        this.next = imageTransformation;
    }

    public Mat executeNext(Mat mat) {
        if (this.next != null) {
            return this.next.transform(mat);
        } else {
            return mat;
        }
    }

    public abstract Mat transform(Mat mat);

}
