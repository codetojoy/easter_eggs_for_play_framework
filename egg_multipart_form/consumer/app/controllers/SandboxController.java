package controllers;

import play.api.mvc.MultipartFormData;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;
import play.libs.ws.*;

import javax.inject.*;

@Singleton
public class SandboxController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public SandboxController() {
    }

    public Result ping(Http.Request request) throws Exception {
        String message = "ping OK : " + new Date();
        System.out.println("TRACER ping message : " + message);
        return ok(message);
    }

    public Result consume(Http.Request request) throws Exception {
        System.out.println("TRACER consume begin");
        StringBuilder message = new StringBuilder("consume OK : " + new Date() + "\n");

        Http.RequestBody body = request.body();
        Http.MultipartFormData multipartBody = body.asMultipartFormData();
        var formFieldMap = multipartBody.asFormUrlEncoded();

        String[] values1 = formFieldMap.get("foo");
        message.append("TRACER WTF : " + values[0]);

        for (var name : formFieldMap.keySet()) {
            /*
            var tmpValues = formFieldMap.get(name);
            var values = Arrays.asList(tmpValues); 
            var deepValues = Arrays.asList(values.get(0));
            var value = deepValues.get(0);
            message.append("sanity: " + value + "\n");
            message.append("sanity: " + (values instanceof List) + "\n");
            message.append("sanity 2: " + values.size() + "\n");
            message.append("sanity 3: " + values.get(0) + "\n");
            message.append("sanity 4: " + values.get(0).getClass() + "\n");
            message.append("sanity 5: " + (values.get(0) instanceof String[])  + "\n");
            message.append("sanity 6: " + (values.get(0)[0])  + "\n");
            message.append("size: " + values.size() + "\n");
            message.append("package: " + values.getClass().getPackage().toString() + "\n");
            message.append("class: " + values.getClass().getSimpleName() + "\n");
            for (var value : values) {
            message.append("v package: " + value.getClass().getPackage().toString() + "\n");
            message.append("v class: " + value.getClass().getSimpleName() + "\n");
            }
*/
        }

        System.out.println(message.toString());
        return ok(message.toString());
    }
}
