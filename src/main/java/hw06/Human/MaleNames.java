package hw06.Human;

import java.util.Random;

public enum MaleNames  {

    James,
    Colin,
    Jack,
    Ben,
    Rick;

    private static final Random RANDOM = new Random();

    public static MaleNames getRandomMaleName() {

        MaleNames[] names = MaleNames.values();
        return names[RANDOM.nextInt(names.length)];
    }

}
