package hw11.Human;

import java.util.List;
import java.util.Random;

public enum MaleNames  {

    James,
    Colin,
    Jack,
    Ben,
    Rick;

    private static final Random RANDOM = new Random();

    public static MaleNames getRandomMaleName() {

        List<MaleNames> names = List.of(MaleNames.values());
        return names.get(RANDOM.nextInt(names.size()));
    }

}
