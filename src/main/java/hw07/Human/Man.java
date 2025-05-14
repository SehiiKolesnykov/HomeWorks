package hw07.Human;

import hw07.*;
import hw07.Pets.*;

import java.util.Map;
import java.util.Set;

public final class Man extends Human {

    public Man(String name, String surname, int year, int iq, Map<String,String> schedule, Family family) {
        super(name, surname, year, iq, schedule, family);
    }

    @Override
    public void greetPet() {

        Set<Pet> pets = getFamily() != null ? getFamily().getPet() : null;

        if (pets != null) {
            pets.forEach(pet -> {
                out.printf(Texts.manGreetPetText, pet.getNickname());
            });
        }
    }

    public void repairCar() {
        out.printf(Texts.manRepairCarText);
    }
}
