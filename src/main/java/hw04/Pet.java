package hw04;

import java.io.PrintStream;
import java.util.Objects;

public class Pet {

    static PrintStream out = System.out;

    static {
        out.println("\nLoading new class: Pet");
    }

    static {
        out.println("Creating new object: Pet\n");
    }

    private static final String eatText = "\nЯ ї'м!";
    private static final String respondText = "\nПривіт, хазяїн. Я - %s. Я скучив!\n";
    private static final String foulText = "\nПотрібно добре замісти сліди...";

    private String species;
    private String nickname;
    private int age;
    private int trickLevel;
    private String[] habits;

    public Pet(){};

    public Pet(String species,String nickname) {
        this.species = species;
        this.nickname = nickname;
    }

    public Pet(String species, String nickname, int age, int trickLevel, String[] habits) {
        this.species = species;
        this.nickname = nickname;
        this.age = age;
        this.trickLevel = trickLevel;
        this.habits = habits;
    }

    public String getSpecies() { return species; }
    public String getNickname() { return nickname; }
    public int getAge() { return age; }
    public int getTrickLevel() { return trickLevel; }
    public String[] getHabits() { return habits; }

    public void setSpecies(String species) {this.species = species;}
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
