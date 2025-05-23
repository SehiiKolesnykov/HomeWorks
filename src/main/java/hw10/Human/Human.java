package hw10.Human;

import hw10.Family;
import hw10.Pets.Pet;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static hw10.Texts.*;
import static java.time.ZoneOffset.UTC;


public abstract class Human {

    static {
        out.printf(loadingClassText, humanText);
        out.printf(creatingObjectText, humanText);
        out.printf(textBorder);
    }

    private String name;
    private String surname;
    private long birthDay;
    private int iq;
    private Map<String, String> schedule;
    private Family family;

    protected Human(String name, String surname, long birthDay, int iq, Map<String,String> schedule, Family family) {
        this.name = name;
        this.surname = surname;
        this.birthDay = birthDay;
        this.iq = iq;
        this.schedule = schedule != null ? new HashMap<>(schedule) : new HashMap<>();
        this.family = family;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public long getBirthDay() { return birthDay; }
    public int getIq() { return iq; }
    public Map<String,String> getSchedule() { return new HashMap<>(schedule); }
    public Family getFamily() { return family; }

    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setBirthDay(String birthDayString) {

        this.birthDay = dateFormaterStringToTimestamp(birthDayString);
    }
    public void setIq(int iq) { this.iq = iq; }
    public void setSchedule(Map<String,String> schedule) {
        this.schedule = schedule != null ? new HashMap<>(schedule) : new HashMap<>(); }
    public void setFamily(Family family) { this.family = family; }

    public static long dateFormaterStringToTimestamp(String dateString) {
        try {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dateString, formatter);
            if (date.isAfter(currentDate)) {
                out.printf(errorBirthDay);
                return 0;
            }
            return date.atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli();
        } catch (DateTimeParseException e) {
            out.printf(errorDataFormat, dateString);
           return 0;
        }
    }

    public String getBirthdayToString() {

        LocalDate date = Instant.ofEpochMilli(birthDay).atZone(UTC).toLocalDate();
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String describeAge() {

        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = Instant.ofEpochMilli(birthDay).atZone(UTC).toLocalDate();

        if (birthDate.isAfter(currentDate)) {
            return errorBirthDay;
        }

        Period age = Period.between(birthDate, currentDate);

        return String.format("years : %s, month: %s, day: %s", age.getYears(), age.getMonths(), age.getDays());

    }

    public abstract void greetPet();

    private Set<Pet> getPetsFromFamily() {
        return family != null ? family.getPet() : null;
    };

    public void describePets() {

         Set<Pet> pets = getPetsFromFamily();

        if (pets != null) {

            pets.forEach(pet -> {
               String trickiness = pet.getTrickLevel() > 50 ? highTrickiness : lowTrickiness;
                out.printf(describePetText, pet.getSpecies(), pet.getAge(), trickiness);
            });

        }
    }

    private boolean isFeedPet(Pet pet, boolean isTimeToFeed) {

        boolean isTricky = pet.getTrickLevel() > new Random().nextInt(101);

        if (isTimeToFeed || isTricky) {
            out.printf(feedTimeText, pet.getNickname());
            return true;
        }

        out.printf(noHungryText, pet.getNickname());
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

    public String prettyFormat() {
        StringJoiner scheduleJoiner = new StringJoiner(", ", "{", "}");
        if (schedule != null) {
            schedule.forEach( (key,value) -> scheduleJoiner.add(String.format("%s, %s", key, value)));
        }

        return String.format("{name='%s', surname='%s', birthday='%s', iq=%s, schedule=%s}",
                name, surname, getBirthdayToString(), iq, scheduleJoiner);
    }

    @Override
    public String toString() {

        return prettyFormat();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human human)) return false;

        return birthDay == human.birthDay
                && iq == human.iq
                && Objects.equals(name, human.name)
                && Objects.equals(surname, human.surname)
                && Objects.equals(schedule, human.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthDay, iq, schedule);
    }
}
