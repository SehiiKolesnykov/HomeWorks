package hw06.Human;

import hw06.DayOfWeek;
import hw06.Family;
import hw06.Pets.Pet;
import hw06.Texts;

import java.util.Random;

interface HumanCreator {
    Random random = new Random();
    Human bornChild(int year);
}

public final class Woman extends Human implements HumanCreator {

    public Woman() { super(); }

    public Woman(String name, String surname, int year) {
        super(name, surname, year);
    }

    public Woman(String name, String surname, int year, Family family) {
        super(name, surname, year, family);
    }

    public Woman(String name, String surname, int year, int iq, String[][] schedule, Family family) {
        super(name, surname, year, iq, schedule, family);
    }

    @Override
    public void greetPet() {
        Pet pet = getFamily() != null ? getFamily().getPet() : null;
        if (pet != null) {
            out.printf(Texts.womanGreetPetText, pet.getNickname());
        }
    }

    @Override
    public Human bornChild(int year) {
        Family family = getFamily();
        Man father = (Man) family.getFather();
        if (family == null || family.getFather() == null) {
            return null;
        }
        boolean isMale = random.nextBoolean();
        String name = isMale ? MaleNames.getRandomMaleName().name() : FemaleNames.getRandomFemaleName().name();
        String surname = father.getSurname();
        int iq = (getIq() + father.getIq()) / 2;
        String[][] schedule = {{DayOfWeek.MONDAY.name(), "Walk outside"}};

        Human child = isMale ?
                new Man(name, surname, year, iq, schedule, family)
                :
                new Woman(name, surname, year, iq, schedule, family);

        return child;
    }

    public void makeup() {
        out.printf(Texts.womanMakeupText);
    }
}
