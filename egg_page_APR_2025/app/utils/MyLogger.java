
package utils;

import org.slf4j.Logger;

public class MyLogger {
   public static void log(String msg) {
        long threadId = Thread.currentThread().threadId();
        System.err.println("TRACER tid: " + threadId + " " + msg);
   }

   public static void log(Logger logger, String msg) {
        long threadId = Thread.currentThread().threadId();
        System.out.println("TRACER tid: " + threadId + " " + msg);
   }
}
