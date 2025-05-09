package hw06.Pets;

import hw06.Texts;

public final class RoboCat extends Pet {

    public RoboCat() {
        super(Species.ROBOCAT);
    }

    public RoboCat(String nickname) {
        super(Species.ROBOCAT, nickname);
    }

    public RoboCat(String nickname, int age, int trickLevel, String[] habits) {
        super(Species.ROBOCAT, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(Texts.roboCatRespondText, getNickname());
    }
}
