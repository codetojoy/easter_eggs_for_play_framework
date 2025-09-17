
package utils;

import java.util.Date;
import java.text.SimpleDateFormat;

public class MyLogger {
   public static void log(String msg) {
        String threadId = Thread.currentThread().getName();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        String format = "TRACER [%s] t: %s : %s";
        String fullMessage = String.format(format, timestamp, threadId, msg);
        System.err.println(fullMessage);
   }
}
