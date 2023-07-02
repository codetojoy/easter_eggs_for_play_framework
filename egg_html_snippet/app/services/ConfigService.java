package services;

import utilities.Timer;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.ConfigRepository;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.*;
import java.util.regex.*;
import java.util.concurrent.*;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;


public class ConfigService {

    boolean isValidHash(String snippet, String hash) {
        var result = false;

        try {
            String computedHash = Hashing.sha256().hashString(snippet, StandardCharsets.UTF_8).toString();
            
            if (hash.equals(computedHash)) { 
                result = true;
            } else {
                System.err.println("TRACER internal error on hash");
            } 
        } catch (Exception ex) {
            System.err.println("caught ex: " + ex.getMessage());
        }

        return result;
    }

    boolean isValidRegex(String snippet, String template) {
        var result = false;

        try {
            Pattern regex = Pattern.compile(template, Pattern.DOTALL);
            System.out.println("TRACER snippet: " + snippet);
            System.out.println("TRACER template: " + template);
            Matcher matcher = regex.matcher(snippet);
            if (matcher.matches()) {
                result = true;
            } else {
                System.err.println("TRACER internal error on match");
            } 
        } catch (Exception ex) {
            System.err.println("caught ex: " + ex.getMessage());
        }

        return result;
    }

    boolean isValid(String snippet, String template, String hash) {
        return isValidRegex(snippet, template) && isValidHash(snippet, hash);
    }

    public Optional<String> buildSnippet(String snippet, String template, String hash) {
        Optional<String> result = Optional.empty();

        if (isValid(snippet, template, hash)) {
            var timestamp = new Date().toString();
            result = Optional.of("[" + timestamp + "] OK: " + snippet);
        } 

        return result;
    }
}
