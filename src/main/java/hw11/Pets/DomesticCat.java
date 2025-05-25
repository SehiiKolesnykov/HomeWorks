package hw11.Pets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

import static hw11.Texts.*;

public final class DomesticCat extends Pet implements CanFoul {

    @JsonCreator
    public DomesticCat(@JsonProperty("nickname") String nickname,
                       @JsonProperty("age") int age,
                       @JsonProperty("trickLevel") int trickLevel,
                       @JsonProperty("habits") Set<String> habits) {
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
