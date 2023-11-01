
import com.fasterxml.jackson.databind.JsonNode;
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

import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class TimerTest {
    @Test
    public void testCanary() {
        Timer timer = new Timer(1);

        // test
        try { 
            Thread.sleep(1400);
        } catch (Exception ex) {
        }

        assertTrue(timer.exceedsThreshold());
    }
}
