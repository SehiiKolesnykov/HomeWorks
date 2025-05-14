package hw07.Human;

import hw07.*;
import hw07.Pets.*;
import hw07.Texts;

import java.io.PrintStream;
import java.util.*;


public abstract class Human {

    static final PrintStream out = System.out;

    static {
        out.printf(Texts.loadingClassText, Texts.humanText);
        out.printf(Texts.creatingObjectText, Texts.humanText);
        out.printf(Texts.textBorder);
    }

    private String name;
    private String surname;
    private int year;
    private int iq;
    private Map<String, String> schedule;
    private Family family;

    protected Human(String name, String surname, int year, int iq, Map<String,String> schedule, Family family) {
        this.name = name;
        this.surname = surname;
        this.year = year;
        this.iq = iq;
        this.schedule = schedule != null ? new HashMap<>(schedule) : new HashMap<>();
        this.family = family;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public int getYear() { return year; }
    public int getIq() { return iq; }
    public Map<String,String> getSchedule() { return new HashMap<>(schedule); }
    public Family getFamily() { return family; }

    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setYear(int year) { this.year = year; }
    public void setIq(int iq) { this.iq = iq; }
    public void setSchedule(Map<String,String> schedule) {
        this.schedule = schedule != null ? new HashMap<>(schedule) : new HashMap<>(); }
    public void setFamily(Family family) { this.family = family; }

    public abstract void greetPet();

    private Set<Pet> getPetsFromFamily() {
        return family != null ? family.getPet() : null;
    };

    public void describePets() {

         Set<Pet> pets = getPetsFromFamily();

        if (pets != null) {

            pets.forEach(pet -> {
               String trickiness = pet.getTrickLevel() > 50 ? Texts.highTrickiness : Texts.lowTrickiness;
                out.printf(Texts.describePetText, pet.getSpecies(), pet.getAge(), trickiness);
            });

        }
    }

    private boolean isFeedPet(Pet pet, boolean isTimeToFeed) {

        boolean isTricky = pet.getTrickLevel() > new Random().nextInt(101);

        if (isTimeToFeed || isTricky) {
            out.printf(Texts.feedTimeText, pet.getNickname());
            return true;
        }

        out.printf(Texts.noHungryText, pet.getNickname());
        return false;

    }

    public boolean feedPet(Pet pet, boolean isTimeToFeed) {
        Set<Pet> pets = getPetsFromFamily();
        if (pets == null || !pets.contains(pet)) return false;

        return isFeedPet(pet, isTimeToFeed);
    }

    public void feedAllPets (boolean isTimeToFeed) {
        Set<Pet> pets = getPetsFromFamily();
        if (pets == null) return;

        pets.forEach(pet -> {
            isFeedPet(pet, isTimeToFeed);
        });
    }



    @Override
    public String toString() {
        StringJoiner scheduleJoiner = new StringJoiner(", ", "[", "]");
        if (schedule != null) {
            schedule.forEach( (key,value) -> scheduleJoiner.add(String.format("\n[%s, %s]", key, value)));
        }

        StringJoiner humanJoiner = new StringJoiner(",\n", "\n{", "}");
        humanJoiner
                .add(String.format("name: '%s'", name))
                .add(String.format("surname: '%s'", surname))
                .add(String.format("year: %s", year))
                .add(String.format("iq: %s", iq))
                .add(String.format("schedule: %s", scheduleJoiner.toString()));

        return humanJoiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human human)) return false;

        return year == human.year
                && iq == human.iq
                && Objects.equals(name, human.name)
                && Objects.equals(surname, human.surname)
                && Objects.equals(schedule, human.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, year, iq, schedule);
    }
}
