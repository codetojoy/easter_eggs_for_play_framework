package services;

import services.ConfigService;
import org.junit.*;
import play.inject.guice.GuiceApplicationBuilder;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class ConfigServiceTest {
    private ConfigService configService;

    public ConfigServiceTest() {
        this.configService = new ConfigService();
    }

    @Ignore
    @Test
    public void testIsValidHash_happypath() {
        var input = "abcdef";
        var hash = "ae0666f161fed1a5dde998bbd0e140550d2da0db27db1d0e31e370f2bd366a57";

        // test
        var result = configService.isValidHash(input, hash);

        // assertTrue(result);
    }

    @Test
    public void testIsValidHash_no() {
        // test
        var result = configService.isValidHash("abcdef", "abcdef");

        assertFalse(result);
    }

    @Test
    public void testIsValidRegex_multiline_case1() {
        // test
        var result = configService.isValidRegex("a\nb\nc\n123",".*");

        assertTrue(result);
    }

    @Test
    public void testIsValidRegex_basic() {
        // test
        var result = configService.isValidRegex("abc123","...\\d\\d\\d");

        assertTrue(result);
    }
}
