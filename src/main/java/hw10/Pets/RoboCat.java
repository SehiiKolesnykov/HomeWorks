package hw10.Pets;

import java.util.Set;

import static hw10.Texts.*;

public final class RoboCat extends Pet {

    public RoboCat(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.ROBOCAT, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(roboCatRespondText, getNickname());
    }
}
