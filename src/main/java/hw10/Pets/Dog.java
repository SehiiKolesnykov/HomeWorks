package hw10.Pets;

import java.util.Set;

import static hw10.Texts.*;

public final class Dog extends Pet implements CanFoul {

    public Dog(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.DOG, nickname, age, trickLevel, habits);
    }

    @Override
    public void foul() {
        out.printf(dogFoulText);
    }

    @Override
    public void respond() {
        out.printf(dogRespondText, getNickname());
    }
}
