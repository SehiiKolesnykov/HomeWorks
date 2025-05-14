package hw07.Pets;

import hw07.*;

import java.util.Set;

public final class Dog extends Pet implements CanFoul {

    public Dog(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.DOG, nickname, age, trickLevel, habits);
    }

    @Override
    public void foul() {
        out.printf(Texts.dogFoulText);
    }

    @Override
    public void respond() {
        out.printf(Texts.dogRespondText, getNickname());
    }
}
