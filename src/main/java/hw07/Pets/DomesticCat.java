package hw07.Pets;

import hw07.*;

import java.util.Set;

public final class DomesticCat extends Pet implements CanFoul {

    public DomesticCat(String nickname, int age, int trickLevel, Set<String> habits) {
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
