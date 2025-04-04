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

import java.util.*;

@Singleton
public class AuthController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Config config;

    private final WSClient wsClient;

    private static final String H_CAPTCHA_RESPONSE = "h-captcha-response";
    private static final String H_CAPTCHA_SECRET = "hcaptcha.secret";
    private static final String H_CAPTCHA_SITE_KEY = "hcaptcha.site.key";
    private static final String SITE_VERIFY_URL = "https://api.hcaptcha.com/siteverify";

    private final String hcaptchaSecret;
    private final String hcaptchaSiteKey;

    @Inject
    public AuthController(Config config, WSClient wsClient) {
        this.config = config;
        this.wsClient = wsClient;
        hcaptchaSecret = config.getString(H_CAPTCHA_SECRET);
        hcaptchaSiteKey = config.getString(H_CAPTCHA_SITE_KEY);
    }

    public Result index() {
        String message = "hcaptchaSecret len: " + hcaptchaSecret.length();
        return ok(views.html.login.render(message, hcaptchaSiteKey));
    }

    private Optional<String> getToken(Http.Request request, String fieldName) {
        Optional<String> result = Optional.empty();
        String[] fields = request.body().asFormUrlEncoded().get(fieldName);
        if (fields != null && fields.length > 0) {
           result = Optional.of(fields[0]); 
        }
        return result;
    }

    private Optional<String> callSiteVerify(Optional<String> maybeToken) throws Exception {

        // guard:
        if (maybeToken.isEmpty()) {
            return Optional.empty();
        }

        String token = maybeToken.get();

        String paramsFormat = "secret=%s&response=%s";
        String params = String.format(paramsFormat, hcaptchaSecret, token);
        System.out.println("TRACER params: " + params);

        WSResponse response = wsClient.url(SITE_VERIFY_URL)
                                      .setContentType("application/x-www-form-urlencoded")
                                      .post(params).toCompletableFuture().get();
        return Optional.of(response.asJson().toString());
    }

    public Result authenticate(Http.Request request) throws Exception {

        Optional<String> maybeToken = getToken(request, H_CAPTCHA_RESPONSE);

        Optional<String> maybeMessage = callSiteVerify(maybeToken);

        boolean isOk = maybeMessage.isPresent();

        if (isOk) {
            String message = maybeMessage.get();
            return ok(views.html.landing.render(message));
        } else {
            return ok(views.html.denied.render(""));
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
