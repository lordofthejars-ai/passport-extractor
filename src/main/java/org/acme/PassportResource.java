package org.acme;



import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.translate.TranslateException;
import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.acme.image.ImageStorage;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/passport")
public class PassportResource {

    @Inject
    ImageFactory imageFactory;

    @Inject
    PassportExtractor passportExtractor;

    @Inject
    ImageStorage imageStorage;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Passport scan(@RestForm("picture") FileUpload picture) throws IOException, TranslateException {
        Passport p = passportExtractor.extract(getImage(picture));
        return persistPassport(p);
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/name")
    public byte[] getFirstNameImage() {
        return imageStorage.loadName();
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/surname")
    public byte[] getSurnameImage() {
        return imageStorage.loadSurname();
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/expire")
    public byte[] getExpireImage() {
        return imageStorage.loadExpireDate();
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/number")
    public byte[] getPassportNumberImage() {
        return imageStorage.loadPassportNumber();
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/boundaries")
    public byte[] getBoundariesImage() {
        return imageStorage.loadPassportBoundaries();
    }

    private Image getImage(FileUpload picture) throws IOException {
        final byte[] content = Files.readAllBytes(picture.uploadedFile());
        return this.imageFactory.fromInputStream(new ByteArrayInputStream(content));
    }

    private Passport persistPassport(Passport p) {
        try {
            QuarkusTransaction.begin();
            p.persist();
            QuarkusTransaction.commit();
            return p;
        } catch (Throwable e) {
            QuarkusTransaction.rollback();
            throw e;
        }
    }
}
