package hw08.Human;

import static hw08.Texts.*;

public class AdoptChild {

    public static Human createAdoptChild(String name, String surname, String birthDayString, int iq, String gender){
        return switch (gender.toUpperCase()) {
            case "MALE" -> new Man(name, surname, birthDayString, iq);
            case "FEMALE" -> new Woman(name, surname, birthDayString, iq);
            default -> {
                out.printf(errorAdoptGender);
                yield null;
            }
        };
    }
}
