package servidor;

import java.util.Random;

public class Randomize {

    public static int random(int size) {
        Random random = new Random();
        int position = random.nextInt(size);
        return position;
    }

    public static boolean random() {
        Random random = new Random();
        return random.nextBoolean();
    }

}
