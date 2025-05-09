package hw06.Human;

import hw06.Family;
import hw06.Pets.Pet;
import hw06.Texts;

public final class Man extends Human {

    public Man() {
        super();
    }

    public Man(String name, String surname, int year) {
        super(name, surname, year);
    }

    public Man(String name, String surname, int year, Family family) {
        super(name, surname, year, family);
    }

    public Man(String name, String surname, int year, int iq, String[][] schedule, Family family) {
        super(name, surname, year, iq, schedule, family);
    }

    @Override
    public void greetPet() {
        Pet pet = getFamily() != null ? getFamily().getPet() : null;
        if (pet != null) {
            out.printf(Texts.manGreetPetText, pet.getNickname());
        }
    }

    public void repairCar() {
        out.printf(Texts.manRepairCarText);
    }
}
