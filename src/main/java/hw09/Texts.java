package hw09;

import java.io.PrintStream;

public final class Texts {

    public static final PrintStream out = System.out;

    //  SYSTEM TEXT //

    public static final String creatingObjectText = "\nCreating new object: %s...\n";
    public static final String loadingClassText = "\nLoading new class: %s...";
    public static final String textBorder = "\n--------------------------------------\n";

    public static final String petText = "Pet";
    public static final String humanText = "Human";
    public static final String familyText = "Family";

    // Pet Texts //

    public static final String eatText = "\nЯ ї'м!";

    // DOG //
    public static final String dogFoulText = "\nХазяїн ,гав, ти не вигуляв мене, тому отримай подарунок у вітальні, гав...";
    public static final String dogRespondText = "\nПривіт ,гав, хазяїн. Я пес - %s, гав. Ходімо на прогулянку!\n";

    // ROBOCAT //
    public static final String roboCatRespondText = "Привіт, хазяїн. Я розумний Робот Кіт - %s. Давай гратися!\n";

    // CAT //

    public static final String catFoulText = "\nХазяїн, ти не поприбирав лоток! Не дивуйся, що під ліжком...";
    public static final String catRespondText = "\nПривіт, хазяїн, мур-р-р. Я котик - %s. Погладь мене!\n";

    // Fish //

    public static final String fishRespondText = "\nПривіт, хазяїн. Я рибка - %s!\n";

    // SNAKE //

    public static final String snakeFoulText = "\nХазяїн, пора прибрати у тераріумі!";
    public static final String snakeRespondText = "\nПривіт, хазяїн. Я змія - %s!\n";

    // BIRD //

    public static final String birdFoulText = "\nХазяїн, пора прибрати у клітці!";
    public static final String birdRespondText = "\nПривіт, хазяїн. Я пташка - %s!\n";

    // HUMAN TEXT //

    public static final String greetPetText = "\nПривіт, %s\n";
    public static final String highTrickiness = "дуже хитрий";
    public static final String lowTrickiness = "майже не хитрий";
    public static final String describePetText = "\nУ мене є %s, йому %d років, він %s.\n";
    public static final String feedTimeText = "\nХм... час годувати %s!\n";
    public static final String noHungryText = "\nДумаю, %s не голодний.\n";

    // MAN //

    public static final String manGreetPetText = "\nПривіт, %s! Давай пограємось!";
    public static final String manRepairCarText = "\nЗнову треба ремонтувати це старе відро!";

    // Woman //

    public static final String womanGreetPetText = "\nПривіт, %s! Я за тобою дуже скучила!";
    public static final String womanMakeupText = "\nНаносимо макіяж...\nТепер я виглядаю як справжня королева!";

    // Family service //

    public static final String familyBiggerText = "\nFamilies with more than %d members:\n";
    public static final String noFamilyBiggerText = "\nCan't find families with more than %d members!\n";
    public static final String familyLessText = "\nFamilies with less than %d members:\n";
    public static final String noFamilyLessText = "\nCan't find families with less than %d members!\n";
    public static final String countFamilyText = "\nNumber of families with exactly %d members: %d\n";
    public static final String createFamilyText = "\nNew family created: \n%s\n";
    public static final String deleteFamilyText = "\nFamily at index %d deleted!\n";
    public static final String bornChildText = "\nNew child born: \n%s\n";
    public static final String adoptChildText = "\nChild adopted: \n%s\n";
    public static final String deleteChildrenOlderThanText = "\nAll children older %d years removed!\n";
    public static final String countFamiliesText = "\nTotal number of families: %d\n";
    public static final String getFamilyByIdText = "\nFamily at index %d: \n%s\n";
    public static final String addPetText = "\nPet added to family at index %d: \n%s\n";

    // Main //

    public static final String humanInfoText = "\nHuman info: %s";
    public static final String demoHumanMethodText = "\n Demonstrating methods for %s %s\n";
    public static final String demoPetMethodsText = "\nDemonstrating methods for a pet:  %s\n";
    public static final String familyInfoText = "\nInformation about family :\n%s";
    public static final String demoBornChildText = "\nDemonstrating Born Child: \n";
    public static final String demoBornEndText = "\nNew Child is :\n%s\n";

    // New Main with DAO //

    public static final String demoCreateFamilyText = "Creating new families...";
    public static final String demoAddChildText = "Adding children...";
    public static final String demoAddPetsText = "Adding pets...";
    public static final String demoDisplayAllFamiliesText = "Display all families...";
    public static final String demoGetPetsText = "Getting pets for family at index %d...\n";


    // Errors //

    public static final String errorHumanTypeText = "\nUnknown Human type: %s";
    public static final String errorBornChildText = "\nNew child can't born! Choose another family or complete this!";
    public static final String errorMemberTypeText = "\nCan't add member of this type!";
    public static final String errorDataFormat = "\nInvalid data format. Expected : dd/MM/yyyy . Received : %s";
    public static final String errorBirthDay = "\nBirthday can't be in the future!";
    public static final String errorAdoptGender = "\n Gender specified incorrectly! Expected : male or female ";
    public static final String errorNoFamilies = "\nNo Families found in the database!";
    public static final String errorFamilyIndexText = "\nNo family found at index %d!\n";

}
