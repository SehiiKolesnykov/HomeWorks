package hw07.Pets;

import hw07.*;

import java.util.Set;

public final class Snake extends Pet implements CanFoul {

    public Snake(String nickname, int age, int trickLevel, Set<String> habits) {
        super(Species.SNAKE, nickname, age, trickLevel, habits);
    }

    @Override
    public void respond() {
        out.printf(Texts.snakeRespondText, getNickname());
    }

    @Override
    public void foul() {
        out.printf(Texts.snakeFoulText);
    }
}
