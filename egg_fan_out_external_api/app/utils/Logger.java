
package utils;

public class Logger {
   public static void log(String msg) {
        long threadId = Thread.currentThread().getId();
        System.err.println("TRACER tid: " + threadId + " " + msg);
    }
}
