package hw07.Human;

import hw07.Pets.*;
import hw07.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

interface HumanCreator {
    Random random = new Random();
    Human bornChild(int year);
}

public final class Woman extends Human implements HumanCreator {

    public Woman(String name, String surname, int year, int iq, Map<String,String> schedule, Family family) {
        super(name, surname, year, iq, schedule, family);
    }

    @Override
    public void greetPet() {

        Set<Pet> pets = getFamily() != null ? getFamily().getPet() : null;

        if (pets != null) {
            pets.forEach(pet -> {
                out.printf(Texts.womanGreetPetText, pet.getNickname());
            });
        }
    }

    @Override
    public Human bornChild(int year) {
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
                new Man(name, surname, year, iq, schedule, family)
                :
                new Woman(name, surname, year, iq, schedule, family);

        family.addFamilyMember(child);

        return child;
    }

    public void makeup() {
        out.printf(Texts.womanMakeupText);
    }
}
