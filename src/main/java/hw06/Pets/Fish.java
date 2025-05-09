package hw06.Pets;

import hw06.Texts;

public final class Fish extends Pet {

    public Fish() {
        super(Species.CAT);
    }

    public Fish(String nickname) {
        super(Species.CAT, nickname);
    }

    public Fish(String nickname, int age, int trickLevel, String[] habits) {
        super(Species.FISH, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(Texts.fishRespondText, getNickname());
    }
}
