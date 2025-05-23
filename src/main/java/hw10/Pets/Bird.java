package hw10.Pets;

import java.util.Set;

import static hw10.Texts.*;

public final class Bird extends Pet implements CanFoul {

    public Bird(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.BIRD, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(birdRespondText, getNickname());
    }

    @Override
    public void foul() {
        out.printf(birdFoulText);
    }
}
