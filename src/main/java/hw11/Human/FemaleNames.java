package hw11.Human;

import java.util.List;
import java.util.Random;

public enum FemaleNames {

    Maria,
    Emma,
    Lucy,
    Rachel,
    Amelia;

    private static final Random RANDOM = new Random();

    public static FemaleNames getRandomFemaleName() {

        List<FemaleNames> names = List.of(FemaleNames.values());
        return names.get(RANDOM.nextInt(names.size()));
    }
}
