package hw08.Pets;

import static hw08.Texts.*;

import java.util.Set;

public final class Fish extends Pet {

    public Fish(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.FISH, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(fishRespondText, getNickname());
    }
}
