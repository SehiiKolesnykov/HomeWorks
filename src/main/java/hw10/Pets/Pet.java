package hw10.Pets;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import static hw10.Texts.*;

public abstract class Pet {

    static {
        out.printf(loadingClassText, petText);
        out.printf(creatingObjectText, petText);
        out.printf(textBorder);
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
        out.println(eatText);
    }

    public abstract void respond();

    public String prettyFormat() {
        StringJoiner habitsJoiner = new StringJoiner(", ", "[", "]");
        if (habits != null) {
            habits.forEach(habitsJoiner::add);
        }

        return String.format("{species=%s, nickname='%s', age=%s, trickLevel=%s, habits=%s}",
                species, nickname, age, trickLevel, habitsJoiner);
    }

    @Override
    public String toString() {

        return prettyFormat();
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
