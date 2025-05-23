package hw10.Human;

import hw10.Family;
import hw10.Pets.Pet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static hw10.Texts.*;

public final class Man extends Human {

    public Man(String name, String surname, String birthDayString, int iq, Map<String,String> schedule, Family family) {
        super(name, surname, dateFormaterStringToTimestamp(birthDayString), iq, schedule, family);

    }

    public Man(String name, String surname, String birthDayString, int iq) {
        super(name, surname, dateFormaterStringToTimestamp(birthDayString), iq,new HashMap<>(), null);
    }

    @Override
    public void greetPet() {

        Set<Pet> pets = getFamily() != null ? getFamily().getPet() : null;

        if (pets != null) {
            pets.forEach(pet -> {
                out.printf(manGreetPetText, pet.getNickname());
            });
        }
    }

    public void repairCar() {
        out.printf(manRepairCarText);
    }
}
