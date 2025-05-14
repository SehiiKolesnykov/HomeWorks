package hw07.Pets;

import hw07.Texts;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public abstract class Pet {

    public static final PrintStream out = System.out;

    static {
        out.printf(Texts.loadingClassText, Texts.petText);
        out.printf(Texts.creatingObjectText, Texts.petText);
        out.printf(Texts.textBorder);
    }

    private Species species;
    private String nickname;
    private int age;
    private int trickLevel;
    private Set<String> habits;

    protected Pet(Species species, String nickname, int age, int trickLevel, Set<String> habits) {
        this.species = species;
        this.nickname = nickname;
        this.age = age;
        this.trickLevel = trickLevel;
        this.habits = habits != null ? new HashSet<>(habits) : new HashSet<>();
    }

    public Species getSpecies() { return species; }
    public String getNickname() { return nickname; }
    public int getAge() { return age; }
    public int getTrickLevel() { return trickLevel; }
    public Set<String> getHabits() { return habits; }

    public void setSpecies(Species species) {this.species = species;}
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setAge(int age) { this.age = age; }
    public void setTrickLevel(int trickLevel) { this.trickLevel = trickLevel; }
    public void setHabits(Set<String> habits) {
        this.habits = habits != null ? new HashSet<>(habits) : new HashSet<>(); }

    public void eat() {
        out.println(Texts.eatText);
    }

    public abstract void respond();

    @Override
    public String toString() {

        StringJoiner habitsJoiner = new StringJoiner(", ", "[", "]");
        if (habits != null) {
            habits.forEach(habitsJoiner::add);
        }

        StringJoiner petJoiner = new StringJoiner(",\n", "\n{", "}");
        petJoiner
                .add(String.format("species: '%s'", species.toString()))
                .add(String.format("nickname: '%s'", nickname))
                .add(String.format("age: %s", age))
                .add(String.format("trickLevel: %s", trickLevel))
                .add(String.format("habits: %s", habitsJoiner))
                .add(String.format("canFly: %s", species.canFly()))
                .add(String.format("numberOfLegs: %s", species.numberOfLegs()))
                .add(String.format("hasFur: %s", species.hasFur()));

        return petJoiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet pet)) return false;
        return age == pet.age
                && trickLevel == pet.trickLevel
                && Objects.equals(species, pet.species)
                && Objects.equals(nickname, pet.nickname)
                && Objects.equals(habits, pet.habits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, nickname, age, trickLevel, habits);
    }

}
