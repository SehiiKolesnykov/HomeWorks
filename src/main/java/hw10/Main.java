package hw10;

public class Main {

    public static void main(String[] args) {
        FamilyDao familyDao = new  CollectionFamilyDao();
        FamilyService familyService = new FamilyService(familyDao);
        FamilyController controller = new FamilyController(familyService);
        Menu menu = new Menu(controller);

        menu.run();
    }

}
