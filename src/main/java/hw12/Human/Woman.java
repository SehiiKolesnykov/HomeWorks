package hw12.Human;

import hw12.*;
import hw12.Pets.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static hw12.Texts.*;

interface HumanCreator {
    Random random = new Random();
    Human bornChild(String birthDayString);
}

public final class Woman extends Human implements HumanCreator {

    public Woman(String name, String surname, String birthDayString, int iq, Map<String,String> schedule, Family family) {
        super(name, surname, dateFormaterStringToTimestamp(birthDayString), iq, schedule, family);
    }

    public Woman(String name, String surname, String birthDayString, int iq) {
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
