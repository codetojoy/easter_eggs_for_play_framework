
package utils;

public class DelayStrategy {
    public static final int DELAY_20_IN_SECONDS = 20; 

    // TODO: use MyRandom for random method

    public int getSimpleMod(int id, int n) {
        return id % n;
    }

    public int getRandom(int min, int max) {
        return new MyRandom().getRandomInclusive(min, max);
    }
}
