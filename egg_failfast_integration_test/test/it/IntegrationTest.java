package it;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import v1.post.PostData;
import v1.post.PostRepository;
import v1.post.PostResource;
import com.typesafe.config.Config;

import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static play.test.Helpers.*;

class UnknownConfigException extends Exception {
    UnknownConfigException(String message) {
        super(message);
    }
} 

public class IntegrationTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    void assertKeyIsDefined(Config config, String key) throws UnknownConfigException {
        if (!config.hasPath(key)) {
            throw new UnknownConfigException("could not find value for key: " + key); 
        }
    }

    @Before
    public void before() throws UnknownConfigException {
        var keyFoo = "net.codetojoy.foo";
        var keyBar = "net.codetojoy.bar";
        var keyBaz = "net.codetojoy.baz";
        Config config = app.injector().instanceOf(Config.class);
        assertNotNull(config);
        assertKeyIsDefined(config, keyFoo);
        assertKeyIsDefined(config, keyBar);
        assertKeyIsDefined(config, keyBaz);
/*
        assertTrue(config.hasPath(keyFoo));    
        assertTrue(config.hasPath(keyBar));    
        assertTrue(config.hasPath(keyBaz));    
*/
    }

    @Test
    public void testCanary() {
        // expensive test goes here
        assertEquals(1+2+3+4, 10);
    }
}
