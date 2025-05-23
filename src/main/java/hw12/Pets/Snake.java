package hw12.Pets;

import java.util.Set;

import static hw12.Texts.*;

public final class Snake extends Pet implements CanFoul {

    public Snake(String nickname, int age, int trickLevel, Set<String> habits) {
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
