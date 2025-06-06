package hw11.Human;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hw11.*;
import hw11.Pets.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static hw11.Texts.*;

interface HumanCreator {
    Random random = new Random();
    Human bornChild(String birthDayString);
}

public final class Woman extends Human implements HumanCreator {

    @JsonBackReference
    private Family family;

    @JsonCreator
    public Woman(@JsonProperty("name") String name,
                 @JsonProperty("surname") String surname,
                 @JsonProperty("birthDay") @JsonDeserialize(using = BirthDayDeserializer.class) String birthDayString,
                 @JsonProperty("iq") int iq,
                 @JsonProperty("schedule") Map<String,String> schedule,
                 @JsonProperty("family") Family family) {
        super(name, surname, dateFormaterStringToTimestamp(birthDayString), iq, schedule, family);
    }


    public Woman(String name,
                 String surname,
                 String birthDayString,
                 int iq) {
        super(name, surname, dateFormaterStringToTimestamp(birthDayString), iq,new HashMap<>(), null);
    }

    @Override
    public void greetPet() {

        Set<Pet> pets = getFamily() != null ? getFamily().getPet() : null;

        if (pets != null) {
            pets.forEach(pet -> {
                out.printf(womanGreetPetText, pet.getNickname());
            });
        }
    }

    @Override
    public Human bornChild(String birthDayString) {
        Family family = getFamily();
        if (family == null) { return null;}

        Man father = (Man) family.getFather();
        if (father == null) return null;

        boolean isMale = random.nextBoolean();
        String name = isMale ? MaleNames.getRandomMaleName().name() : FemaleNames.getRandomFemaleName().name();
        String surname = father.getSurname();
        int iq = (getIq() + father.getIq()) / 2;
        Map<String,String> schedule = new HashMap<>(Map.of(DayOfWeek.MONDAY.name(), "Walk outside"));

        Human child = isMale ?
                new Man(name, surname, birthDayString, iq, schedule, family)
                :
                new Woman(name, surname, birthDayString, iq, schedule, family);

        family.addFamilyMember(child);

        return child;
    }

    public void makeup() {
        out.printf(womanMakeupText);
    }
}
