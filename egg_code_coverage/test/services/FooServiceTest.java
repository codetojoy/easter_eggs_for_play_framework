package services;

import javax.inject.Inject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class FooServiceTest { 
    private final FooService fooService;

    public FooServiceTest() {
        this.fooService = new FooService();
    }

    @Test
    public void testIndex() {
        // test
        String result = fooService.foo("this is a test");

        assertEquals("thisisatest", result);
    }
}
