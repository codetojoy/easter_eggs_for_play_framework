package utils;

import java.util.concurrent.TimeUnit;

public class Timer {
    private long beginInNano;
    private long thresholdInMillis;

    private static final String SECONDS_FORMAT = "  %.3f s";
    private static final String MINUTES_FORMAT = "  %.3f min";

    private static final long SIXTY_SECONDS_IN_MILLIS = 60 * 1000L;
    private static final long ONE_SECOND_IN_MILLIS = 1 * 1000L;
    private static final long THRESHOLD_IN_SECONDS = 2;

    public Timer() {
        beginInNano = System.nanoTime();
        thresholdInMillis = THRESHOLD_IN_SECONDS * 1000L;
    }

    public String getElapsed() {
        String result = "N/A";

        long elapsedInMillis = getElapsedInMillis();

        if (elapsedInMillis > SIXTY_SECONDS_IN_MILLIS) {
           double elapsedInMinutes = elapsedInMillis / (1.0 * SIXTY_SECONDS_IN_MILLIS);
           result = String.format(MINUTES_FORMAT, elapsedInMinutes);
        } else {
           double elapsedInSeconds = elapsedInMillis / (1.0 * ONE_SECOND_IN_MILLIS);
           result = String.format(SECONDS_FORMAT, elapsedInSeconds);
        }

        return result;
    }

    protected long getElapsedInMillis() {
        long elapsedNanoTime = System.nanoTime() - beginInNano;
        long elapsedInMillis = TimeUnit.MILLISECONDS.convert(elapsedNanoTime, TimeUnit.NANOSECONDS);
        return elapsedInMillis;
    }
}
