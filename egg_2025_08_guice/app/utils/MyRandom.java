
package utils;

import java.util.Random;

public class MyRandom {
    public int getRandomInclusive(int min, int max) {
        int x = (max - min) + 1;
        int y = new Random().nextInt(x);
        // y range: [0,(max-min)]
        int result =  min + y;
        return result;
    }
}
