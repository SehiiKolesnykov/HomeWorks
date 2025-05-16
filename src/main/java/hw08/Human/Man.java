package hw08.Human;

import hw08.Family;
import hw08.Pets.Pet;
import static hw08.Texts.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
