package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;
import play.libs.*;

import javax.inject.Singleton;
import javax.inject.Inject;

import com.typesafe.config.Config;

import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;

@Singleton
public class AuthController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Config config;

    private final WSClient wsClient;

    private static final String H_CAPTCHA_RESPONSE = "h-captcha-response";
    private static final String H_CAPTCHA_SECRET = "hcaptcha.secret";
    private static final String SITE_VERIFY_URL = "https://api.hcaptcha.com/siteverify";

    @Inject
    public AuthController(Config config, WSClient wsClient) {
        this.config = config;
        this.wsClient = wsClient;
    }

    public Result index() {
        String hcaptchaSecret = config.getString(H_CAPTCHA_SECRET);
        String message = "hcaptchaSecret len: " + hcaptchaSecret.length();
        return ok(views.html.login.render(message));
    }

    private String getToken(Http.Request request, String fieldName) {
        String token = "";
        String[] fields = request.body().asFormUrlEncoded().get(fieldName);
        if (fields != null && fields.length > 0) {
           token = fields[0]; 
        }
        return token;
    }

    private String callSiteVerify(String token) throws Exception {
        String hcaptchaSecret = config.getString(H_CAPTCHA_SECRET);

        RequestParams requestParams = new RequestParams();
        requestParams.setSecret(hcaptchaSecret);
        requestParams.setResponse(token);
        JsonNode json = Json.toJson(requestParams);
        System.out.println("TRACER json from rp: " + json.toString());

        WSResponse response = wsClient.url(SITE_VERIFY_URL).post(json).toCompletableFuture().get();
        return response.asJson().toString();
    }

    public Result authenticate(Http.Request request) throws Exception {
        boolean isOk = false;

        String token = getToken(request, H_CAPTCHA_RESPONSE);

        String message = "";
        if (!token.isEmpty()) {
            message = callSiteVerify(token);    
            isOk = true;
        }

        if (isOk) {
            return ok(views.html.landing.render(message));
        } else {
            return ok(views.html.denied.render(message));
        }
    }
}

class RequestParams {
    private String secret;
    private String response;

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
}
