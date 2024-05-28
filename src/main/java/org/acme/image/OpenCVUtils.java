package org.acme.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class OpenCVUtils {

    public static void save(Mat image, Path location) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", image, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        //Preparing the Buffered Image
        try {
            Files.write(location, byteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
