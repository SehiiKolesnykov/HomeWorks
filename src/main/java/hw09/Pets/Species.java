package hw09.Pets;

public enum Species {

    DOG(false,4,true),
    CAT(false, 4,true),
    BIRD(true,2,false),
    FISH(false, 0, false),
    SNAKE(false, 0, false),
    ROBOCAT(false, 4, false),
    UNKNOWN(false, 0, false);

    private final boolean canFly;
    private final int numberOfLegs;
    private final boolean hasFur;

    Species(boolean canFly, int numberOfLegs, boolean hasFur) {
        this.canFly = canFly;
        this.numberOfLegs = numberOfLegs;
        this.hasFur = hasFur;
    }

    public boolean canFly() {
        return canFly;
    }
    public int numberOfLegs() {
        return numberOfLegs;
    }
    public boolean hasFur() {
        return hasFur;
    }
}
