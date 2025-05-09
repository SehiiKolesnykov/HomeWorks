package hw06.Pets;

import hw06.Texts;

public final class Bird extends Pet implements CanFoul{

    public Bird() {
        super(Species.CAT);
    }

    public Bird(String nickname) {
        super(Species.CAT, nickname);
    }

    public Bird(String nickname, int age, int trickLevel, String[] habits) {
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
