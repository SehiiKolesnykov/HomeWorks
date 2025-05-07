package hw05;

import java.io.PrintStream;
import java.util.Objects;

public class Pet {

    static PrintStream out = System.out;

    static {
        out.println("\nLoading new class: Pet...");
        out.println("Creating new object: Pet...");
        out.println("--------------------------------------");
    }

    private static final String eatText = "\nЯ ї'м!";
    private static final String respondText = "\nПривіт, хазяїн. Я - %s. Я скучив!\n";
    private static final String foulText = "\nПотрібно добре замісти сліди...";

    private Species species;
    private String nickname;
    private int age;
    private int trickLevel;
    private String[] habits;

    public Pet(){};

    public Pet(Species species, String nickname) {
        this.species = species;
        this.nickname = nickname;
    }

    public Pet(Species species, String nickname, int age, int trickLevel, String[] habits) {
        this.species = species;
        this.nickname = nickname;
        this.age = age;
        this.trickLevel = trickLevel;
        this.habits = habits;
    }

    public Species getSpecies() { return species; }
    public String getNickname() { return nickname; }
    public int getAge() { return age; }
    public int getTrickLevel() { return trickLevel; }
    public String[] getHabits() { return habits; }

    public void setSpecies(Species species) {this.species = species;}
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setAge(int age) { this.age = age; }
    public void setTrickLevel(int trickLevel) { this.trickLevel = trickLevel; }
    public void setHabits(String[] habits) { this.habits = habits; }

    public void eat() {
        out.println(eatText);
    }

    public void respond() {
        out.printf(respondText, nickname);
    }

    public void foul() {
        out.println(foulText);
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            out.println("Finalize Pet: " + this.toString());
        } finally {
            super.finalize();
        }
    }

    @Override
    public String toString() {
        String habitsString = habits != null ? "[" + String.join(", ", habits) + "]" : "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("\n{");
        sb.append(species);
        sb.append(" ,\nnickname:'");
        sb.append(nickname);
        sb.append("', \nage:");
        sb.append(age);
        sb.append(", \ntrickLevel=");
        sb.append(trickLevel);
        sb.append(", \nhabits=");
        sb.append(habitsString);
        sb.append(", \ncanFly=");
        sb.append(species.canFly());
        sb.append(", \nnumberOfLegs=");
        sb.append(species.numberOfLegs());
        sb.append(", \nhasFur=");
        sb.append(species.hasFur());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet pet)) return false;
        return age == pet.age
                && trickLevel == pet.trickLevel
                && Objects.equals(species, pet.species)
                && Objects.equals(nickname, pet.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, nickname, age, trickLevel);
    }

}
