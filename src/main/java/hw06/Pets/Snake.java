package hw06.Pets;

import hw06.Texts;

public final class Snake extends Pet implements CanFoul {

    public Snake() {
        super(Species.CAT);
    }

    public Snake(String nickname) {
        super(Species.CAT, nickname);
    }

    public Snake(String nickname, int age, int trickLevel, String[] habits) {
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
