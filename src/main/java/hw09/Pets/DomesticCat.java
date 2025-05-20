package hw09.Pets;

import java.util.Set;

import static hw09.Texts.*;

public final class DomesticCat extends Pet implements CanFoul {

    public DomesticCat(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.CAT, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(catRespondText, getNickname());
    }

    @Override
    public void foul() {
        out.printf(catFoulText);
    }
}
