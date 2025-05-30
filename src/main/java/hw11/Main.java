package hw11;

public class Main {

    public static void main(String[] args) {
        Logger logger = new Logger();
        FamilyDao familyDao = new CollectionFamilyDao(logger);
        FamilyService familyService = new FamilyService(familyDao);
        FamilyController controller = new FamilyController(familyService);
        Menu menu = new Menu(controller);

        menu.run();
    }

}
