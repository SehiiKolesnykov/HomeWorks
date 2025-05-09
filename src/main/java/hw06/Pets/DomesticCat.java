package hw06.Pets;

import hw06.Texts;

public final class DomesticCat extends Pet implements CanFoul{

    public DomesticCat() {
        super(Species.CAT);
    }

    public DomesticCat(String nickname) {
        super(Species.CAT, nickname);
    }

    public DomesticCat(String nickname, int age, int trickLevel, String[] habits) {
        super(Species.CAT, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(Texts.catRespondText, getNickname());
    }

    @Override
    public void foul() {
        out.printf(Texts.catFoulText);
    }
}
