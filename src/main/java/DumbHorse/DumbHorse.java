package DumbHorse;

import java.util.Random;

public class DumbHorse {
    public static int[][] compute(int[][] board) {
        Random rand = new Random();
        try {
            Thread.sleep(rand.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new int[667][2];
    }
}
