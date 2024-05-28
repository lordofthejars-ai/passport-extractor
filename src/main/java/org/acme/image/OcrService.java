package org.acme.image;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.nio.file.Path;
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;

import static org.bytedeco.leptonica.global.leptonica.*;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;

@ApplicationScoped
class OcrService {

    @Inject
    private TessBaseAPI api;

    // LOCK
    public String scan(byte[] content) {
        PIX image = pixReadMem(content, content.length);
        return getTxt(image);
    }

    // LOCK
    public String scan(Path content) {
        PIX image = pixRead(content.toFile().getAbsolutePath());
        return getTxt(image);
    }

    private String getTxt(PIX image) {
        api.SetImage(image);
        BytePointer outText = api.GetUTF8Text();

        String txt = outText.getString();

        outText.deallocate();
        pixDestroy(image);
        return txt;
    }
}
