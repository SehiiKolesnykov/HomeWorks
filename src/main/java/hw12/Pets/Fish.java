package hw12.Pets;

import java.util.Set;

import static hw12.Texts.*;

public final class Fish extends Pet {

    public Fish(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.FISH, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(fishRespondText, getNickname());
    }
}
