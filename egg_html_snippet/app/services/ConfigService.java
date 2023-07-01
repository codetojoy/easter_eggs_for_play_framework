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

public class ConfigService {

    public Optional<String> buildSnippet(String snippet, String template) {
        Optional<String> result = Optional.empty();

        try {
            Pattern regex = Pattern.compile(template);
            System.out.println("TRACER snippet: " + snippet);
            System.out.println("TRACER template: " + template);
            Matcher matcher = regex.matcher(snippet);
            if (matcher.matches()) {
                result = Optional.of("OK: " + snippet);
            } 
        } catch (Exception ex) {
            System.out.println("caught ex: " + ex.getMessage());
        }

        return result;
    }
}
