
package utils;

public class MyLog {
    public static void log(String msg) {
        var threadId = Thread.currentThread().getId();
        System.out.println("TRACER [" + threadId + "] " + msg);
    }
}
