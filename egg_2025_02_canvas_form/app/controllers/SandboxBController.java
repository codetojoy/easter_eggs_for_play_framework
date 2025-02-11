package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import play.mvc.*;
import play.libs.*;
import play.libs.Files.TemporaryFile;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class SandboxBController extends Controller {

    @Inject
    public SandboxBController() {
    }

    public Result index() {
        return ok(views.html.sandboxB.render());
    }

    protected void emitLog(String s) {
        String date = new Date().toString();
        System.out.println("TRACER [" + date + "] : " + s);
    }

    public Result createForm(Http.Request request) {
        boolean ok = false;

        File tmpFile = new File(".");
        String tmpPath = tmpFile.getAbsolutePath();

        String dataPath = tmpPath + "/tmp_files";
        File dataDir = new File(dataPath);
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            return internalServerError("data directory not found");
        }

        Http.MultipartFormData<TemporaryFile> body = request.body().asMultipartFormData();
        Http.MultipartFormData.FilePart<TemporaryFile> signature = body.getFile("user-signature");
        String hasSignature = (signature == null) ? "no" : "yes";
        emitLog("TRACER signature ?: " + hasSignature);

        String signatureId = "";

        if (signature != null) {
            String fileName = signature.getFilename();
            long fileSize = signature.getFileSize();
            String contentType = signature.getContentType();
            TemporaryFile file = signature.getRef();

            signatureId = UUID.randomUUID().toString();
            file.copyTo(Paths.get(dataPath + "/" + signatureId + ".png"), true);
            ok = true;
        }

        emitLog("TRACER signature POST OK with id: " + signatureId);

        if (ok) {
            emitLog("wrote png file: " + signatureId + ".png");
            return ok(views.html.sandboxReceive.render());
        } else {
            return internalServerError("internal error");
        }
    }
}

