package hw11.Pets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

import static hw11.Texts.*;

public final class Dog extends Pet implements CanFoul {

    @JsonCreator
    public Dog(@JsonProperty("nickname") String nickname,
               @JsonProperty("age") int age,
               @JsonProperty("trickLevel") int trickLevel,
               @JsonProperty("habits") Set<String> habits) {
        super(Species.DOG, nickname, age, trickLevel, habits);
    }

    @Override
    public void foul() {
        out.printf(dogFoulText);
    }

    @Override
    public void respond() {
        out.printf(dogRespondText, getNickname());
    }
}
