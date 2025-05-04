package hw04;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Random;

public class Human {

    static PrintStream out = System.out;

    static {
        out.println("\nLoading new class: Human");
    }

    static {
        out.println("Creating new object: Human\n");
    }

    private static final String greetPetText = "\nПривіт, %s\n";
    private static final String highTrickiness = "дуже хитрий";
    private static final String lowTrickiness = "майже не хитрий";
    private static final String describePetText = "\nУ мене є %s, їй %d років, він %s.\n";
    private static final String feedTimeText = "\nХм... час годувати %s!\n";
    private static final String noHungryText = "\nДумаю, %s не голодний.\n";

    private String name;
    private String surname;
    private int year;
    private int iq;
    private String[][] schedule;
    private Family family;

    public Human() {};

    public Human(String name, String surname, int year) {
        this.name = name;
        this.surname = surname;
        this.year = year;
    };

    public Human(String name, String surname, int year, Family family) {
        this.name = name;
        this.surname = surname;
        this.year = year;
        this.family = family;
    };

    public Human(String name, String surname, int year, int iq, String[][] schedule, Family family) {
        this.name = name;
        this.surname = surname;
        this.year = year;
        this.iq = iq;
        this.schedule = schedule;
        this.family = family;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public int getYear() { return year; }
    public int getIq() { return iq; }
    public String[][] getSchedule() { return schedule; }
    public Family getFamily() { return family; }

    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setYear(int year) { this.year = year; }
    public void setIq(int iq) { this.iq = iq; }
    public void setSchedule(String[][] schedule) { this.schedule = schedule; }
    public void setFamily(Family family) { this.family = family; }

    public void greetPet() {
        Pet pet = family != null ? family.getPet() : null;
        if (pet != null) { out.printf(greetPetText, pet.getNickname()); }
    }

    public void describePet() {
        Pet pet = family != null ? family.getPet() : null;

        if (pet != null) {

            String trickiness = pet.getTrickLevel() > 50 ? highTrickiness : lowTrickiness;

            out.printf(describePetText, pet.getSpecies(), pet.getAge(), trickiness);
        }
    }

    public boolean feedPet(boolean isTimeToFeed) {

        Pet pet = family != null ? family.getPet() : null;

        if (pet == null) return false;

        boolean isTricky = pet.getTrickLevel() > new Random().nextInt(101);

        if (isTimeToFeed || isTricky) {
            out.printf(feedTimeText, pet.getNickname());
            return true;
        }

        out.printf(noHungryText, pet.getNickname());
        return false;
    }

    @Override
    public String toString() {
        StringBuilder scheduleString = new StringBuilder("[");

        if (schedule != null) {
            for (int i = 0; i < schedule.length; i++) {
                scheduleString
                        .append("\n[")
                        .append(schedule[i][0])
                        .append(", ")
                        .append(schedule[i][1])
                        .append("]");

                if (i < schedule.length - 1) scheduleString.append(", ");
            }
        }

        scheduleString.append("]");

        StringBuilder resultString = new StringBuilder();
        resultString
                .append("Human\n{name='")
                .append(name)
                .append("', \nsurname='")
                .append(surname)
                .append("', \nyear=")
                .append(year)
                .append(", \niq=")
                .append(iq)
                .append(", \nschedule=")
                .append(scheduleString)
                .append("}");

        return resultString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human human)) return false;

        return year == human.year
                && iq == human.iq
                && Objects.equals(name, human.name)
                && Objects.equals(surname, human.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, year, iq);
    }
}
