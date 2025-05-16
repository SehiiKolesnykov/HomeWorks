package hw08;

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

    // Main //

    public static final String humanInfoText = "\nHuman info: %s";
    public static final String demoHumanMethodText = "\n Demonstrating methods for %s %s\n";
    public static final String demoPetMethodsText = "\nDemonstrating methods for a pet:  %s\n";
    public static final String familyInfoText = "\nInformation about family :\n%s";
    public static final String demoBornChildText = "\nDemonstrating Born Child: \n";
    public static final String demoBornEndText = "\nNew Child is :\n%s\n";

    // Errors //

    public static final String errorHumanTypeText = "\nUnknown Human type: %s";
    public static final String errorBornChildText = "\nNew child can't born! Choose another family or complete this!";
    public static final String errorMemberTypeText = "\nCan't add member of this type!";
    public static final String errorDataFormat = "\nInvalid data format. Expected : dd/MM/yyyy . Received : %s";
    public static final String errorBirthDay = "\nBirthday can't be in the future!";
    public static final String errorAdoptGender = "\n Gender specified incorrectly! Expected : male or female ";

}
