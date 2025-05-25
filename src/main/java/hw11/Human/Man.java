package hw11.Human;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hw11.*;
import hw11.Pets.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static hw11.Texts.*;

public final class Man extends Human {

    @JsonBackReference
    private Family family;

    @JsonCreator
    public Man(@JsonProperty("name") String name,
               @JsonProperty("surname") String surname,
               @JsonProperty("birthDay") @JsonDeserialize(using = BirthDayDeserializer.class) String birthDayString,
               @JsonProperty("iq") int iq,
               @JsonProperty("schedule") Map<String,String> schedule,
               @JsonProperty("family")Family family) {
        super(name, surname, dateFormaterStringToTimestamp(birthDayString), iq, schedule, family);

    }


    public Man(String name,
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
                out.printf(manGreetPetText, pet.getNickname());
            });
        }
    }

    public void repairCar() {
        out.printf(manRepairCarText);
    }
}
