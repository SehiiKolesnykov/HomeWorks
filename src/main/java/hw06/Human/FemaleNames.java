package hw06.Human;

import java.util.Random;

public enum FemaleNames {

    Maria,
    Emma,
    Lucy,
    Rachel,
    Amelia;

    private static final Random RANDOM = new Random();

    public static FemaleNames getRandomFemaleName() {

        FemaleNames[] names = FemaleNames.values();
        return names[RANDOM.nextInt(names.length)];
    }
}
