package hw07.Pets;

import hw07.*;

import java.util.Set;

public final class Fish extends Pet {

    public Fish(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.FISH, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(Texts.fishRespondText, getNickname());
    }
}
