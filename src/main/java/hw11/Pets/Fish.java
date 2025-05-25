package hw11.Pets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

import static hw10.Texts.*;

public final class Fish extends Pet {

    @JsonCreator
    public Fish(@JsonProperty("nickname") String nickname,
                @JsonProperty("age") int age,
                @JsonProperty("trickLevel") int trickLevel,
                @JsonProperty("habits") Set<String> habits) {
        super(Species.FISH, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(fishRespondText, getNickname());
    }
}
