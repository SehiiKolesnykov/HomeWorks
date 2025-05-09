package hw06.Pets;

import hw06.Texts;

public final class Dog extends Pet implements CanFoul{

    public Dog() {
        super(Species.DOG);
    }

    public Dog(String nickname) {
        super(Species.DOG, nickname);
    }

    public Dog(String nickname, int age, int trickLevel, String[] habits) {
        super(Species.DOG, nickname, age, trickLevel, habits);
    }

    @Override
    public void foul() {
        out.printf(Texts.dogFoulText);
    }

    @Override
    public void respond() {
        out.printf(Texts.dogRespondText, getNickname());
    }
}
