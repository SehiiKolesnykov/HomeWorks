package hw06.Human;

import hw06.Family;
import hw06.Pets.Pet;
import hw06.Texts;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Random;

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
    private String[][] schedule;
    private Family family;

    protected Human() {};

    protected Human(String name, String surname, int year) {
        this.name = name;
        this.surname = surname;
        this.year = year;
    };

    protected Human(String name, String surname, int year, Family family) {
        this(name , surname , year);
        this.family = family;
    };

    protected Human(String name, String surname, int year, int iq, String[][] schedule, Family family) {
        this(name , surname , year, family);
        this.iq = iq;
        this.schedule = schedule;
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

    public abstract void greetPet();

    public void describePet() {
         Pet pet = family != null ? family.getPet() : null;

        if (pet != null) {

            String trickiness = pet.getTrickLevel() > 50 ? Texts.highTrickiness : Texts.lowTrickiness;

            out.printf(Texts.describePetText, pet.getSpecies(), pet.getAge(), trickiness);
        }
    }

    public boolean feedPet(boolean isTimeToFeed) {

        Pet pet = family != null ? family.getPet() : null;
        if (pet == null) return false;

        boolean isTricky = pet.getTrickLevel() > new Random().nextInt(101);

        if (isTimeToFeed || isTricky) {
            out.printf(Texts.feedTimeText, pet.getNickname());
            return true;
        }

        out.printf(Texts.noHungryText, pet.getNickname());
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            out.println("Finalize Human: " + this.toString());
            out.println("\n--------------------------------------");
        } finally {
            super.finalize();
        }
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
                .append("\n{name='")
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
