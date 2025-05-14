package hw07.Pets;

import hw07.*;

import java.util.Set;

public final class Bird extends Pet implements CanFoul {

    public Bird(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.BIRD, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(Texts.birdRespondText, getNickname());
    }

    @Override
    public void foul() {
        out.printf(Texts.birdFoulText);
    }
}
