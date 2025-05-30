package hw11;

import hw11.Human.*;
import hw11.Pets.Pet;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class FamilyController {
    private final FamilyService familyService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    public List<Family> getAllFamilies() {
        return familyService.getAllFamilies();
    }

    public void displayAllFamilies() {
        familyService.displayAllFamilies();
    }


    public List<Family> getFamiliesBiggerThan(int count) {
        return familyService.getFamiliesBiggerThen(count);
    }

    public List<Family> getFamiliesLessThan(int count) {
        return familyService.getFamiliesLessThen(count);
    }

    public int countFamilyWithMemberNumber(int count) {
        return familyService.countFamiliesWithMemberNumber(count);
    }

    public Family createNewFamily(Woman mother, Man father) {
        return familyService.createNewFamily(mother, father);
    }

    public boolean deleteFamilyByIndex(int index) {
        return familyService.deleteFamilyByIndex(index);
    }

    public Family bornChild(Family family, String maleName, String femaleName, String birthDay) {
        return familyService.bornChild(family, maleName, femaleName, birthDay);
    }

    public Family adoptChild(Family family, Human child) {
        return familyService.adoptChild(family, child);
    }

    public void deleteAllChildrenOlderThan(int age) {
        familyService.deleteAllChildrenOlderThan(age);
    }

    public int count(){
        return familyService.count();
    }

    public Family getFamilyById(int index) {
        return familyService.getFamilyById(index);
    }

    public Set<Pet> getPets(int index) {
        return familyService.getPets(index);
    }

    public void addPet(int index, Pet pet) {
        familyService.addPet(index, pet);
    }

    public void loadData() {
        familyService.loadData();
    }

    public void fillWithTestData() {
        familyService.fillWithTestData();
    }

    public void saveData() {
        familyService.saveData();
    }
}
