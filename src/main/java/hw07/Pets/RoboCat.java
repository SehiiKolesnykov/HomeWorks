package hw07.Pets;

import hw07.*;

import java.util.Set;

public final class RoboCat extends Pet {

    public RoboCat(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.ROBOCAT, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(Texts.roboCatRespondText, getNickname());
    }
}
