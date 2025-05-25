package hw11.Pets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

import static hw11.Texts.*;

public final class Snake extends Pet implements CanFoul {

    @JsonCreator
    public Snake(@JsonProperty("nickname") String nickname,
                 @JsonProperty("age") int age,
                 @JsonProperty("trickLevel") int trickLevel,
                 @JsonProperty("habits") Set<String> habits) {
        super(Species.SNAKE, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(snakeRespondText, getNickname());
    }

    @Override
    public void foul() {
        out.printf(snakeFoulText);
    }
}
