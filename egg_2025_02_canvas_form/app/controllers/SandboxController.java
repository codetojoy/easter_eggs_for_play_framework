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

public class SandboxController extends Controller {

    @Inject
    public SandboxController() {
    }

    public Result index() {
        return ok(views.html.sandbox.render());
    }

    protected void emitLog(String s) {
        String date = new Date().toString();
        System.out.println("TRACER [" + date + "] : " + s);
    }

    public Result createForm(Http.Request request) {
        var formData = request.body().asMultipartFormData();

        String input1 = request.body().asFormUrlEncoded().get("input1")[0];
        emitLog("input1: " + input1);

        String input2 = request.body().asFormUrlEncoded().get("input2")[0];
        emitLog("input2: " + input2);

        String signText = request.body().asFormUrlEncoded().get("sign-text")[0];
        emitLog("sign-text: " + signText);

        String signatureId = "unknown";

        var fieldArrForSig = request.body().asFormUrlEncoded().get("signature-id");
        if (fieldArrForSig != null && fieldArrForSig.length > 0) {
            signatureId = request.body().asFormUrlEncoded().get("signature-id")[0];
        }

        emitLog("signature-id: " + signatureId);

        return ok(views.html.sandboxReceive.render());
    }

    public Result signature(Http.Request request) {
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
        String signatureId = "";

        if (signature != null) {
            String fileName = signature.getFilename();
            long fileSize = signature.getFileSize();
            String contentType = signature.getContentType();
            TemporaryFile file = signature.getRef();

            signatureId = UUID.randomUUID().toString();
            file.copyTo(Paths.get(dataPath + "/" + "signature.png"), true);
            ok = true;
        }

        emitLog("TRACER signature POST OK with id: " + signatureId);

        if (ok) {
            ObjectNode node = Json.newObject();
            node.put("id", signatureId);
            return ok(node);
        } else {
            return internalServerError("internal error");
        }
    }
}

