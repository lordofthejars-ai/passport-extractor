package org.acme.image;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import nu.pattern.OpenCV;
import org.bytedeco.tesseract.TessBaseAPI;


@ApplicationScoped
class OcrConfiguration {

    static {
        OpenCV.loadLocally();
    }

    @Produces
    public TessBaseAPI create() {
        TessBaseAPI api = new TessBaseAPI();
        api.Init("src/main/resources", "eng");
        return api;
    }

    void close(@Disposes TessBaseAPI tessBaseAPI) {
        tessBaseAPI.End();
    }
}
