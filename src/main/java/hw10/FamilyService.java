package hw10;

import hw10.Human.*;
import hw10.Pets.*;
import hw10.exceptions.FamilyOverflowException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static hw10.Texts.*;

public class FamilyService {
    private static final int MAX_FAMILY_SIZE = 10;
    private final FamilyDao familyDao;

    public FamilyService(FamilyDao familyDao) {
        this.familyDao = familyDao;
    }

    public List<Family> getAllFamilies() {
        return familyDao.getAllFamilies().stream().collect(Collectors.toList());
    }

    public void displayAllFamilies() {
        List<Family> families = getAllFamilies();
        if (families.isEmpty()) out.printf(errorNoFamilies);
        else IntStream.range(0, families.size())
                .forEach(i -> out.printf("\n%s %d: \n%s\n", familyText, i + 1, families.get(i)));
    }

    public List<Family> getFamiliesBiggerThen(int count) {
        List<Family> result = getAllFamilies().stream()
                .filter(f -> f.countFamily() > count)
                .collect(Collectors.toList());
        out.printf(familyBiggerText, count);
        result.forEach(out::println);
        if (result.isEmpty()) out.printf(noFamilyBiggerText + textBorder, count);
        return result;
    }

    public List<Family> getFamiliesLessThen(int count) {
        List<Family> result = getAllFamilies().stream()
                .filter(f -> f.countFamily() < count)
                .collect(Collectors.toList());
        out.printf(familyLessText, count);
        result.forEach(out::println);
        if (result.isEmpty()) out.printf(noFamilyLessText + textBorder, count);
        return result;
    }

    public int countFamiliesWithMemberNumber(int count) {
        long countFamilies = getAllFamilies().stream()
                .filter(f -> f.countFamily() == count)
                .count();
        out.printf(countFamilyText, count, countFamilies);
        out.printf(textBorder);
        return (int) countFamilies;
    }

    public Family createNewFamily(Woman mother, Man father) {
        return Optional.ofNullable(mother)
                .filter(m -> m instanceof Woman)
                .flatMap(m -> Optional.ofNullable(father)
                        .filter(f -> f instanceof Man)
                        .map(f -> {
                            Family family = new Family((Woman) m, (Man) f);
                            familyDao.saveFamily(family);
                            out.printf(createFamilyText, family);
                            return family;
                        }))
                .orElseGet(() -> {
                    out.printf(errorMemberTypeText);
                    return null;
                });
    }

    public  boolean deleteFamilyByIndex (int index) {
        boolean deleted = familyDao.deleteFamily(index);
        out.printf(deleted ? deleteFamilyText : errorFamilyIndexText, index);
        out.printf(textBorder);
        return deleted;
    }

    public Family bornChild(Family family, String maleName, String femaleName, String birthDayString) {
        return Optional.ofNullable(family)
                .filter(f -> f.getMother() instanceof Woman)
                .filter(f -> f.countChildren() < MAX_FAMILY_SIZE)
                .map(f -> ((Woman) f.getMother()).bornChild(birthDayString))
                .map(child -> {
                    if (child instanceof Man) child.setName(maleName);
                    else child.setName(femaleName);
                    familyDao.saveFamily(family);
                    out.printf(textBorder);
                    out.printf(bornChildText, child);
                    return family;
                })
                .orElseGet(() -> {
                    if (family == null || family.getMother() == null) {
                        out.printf(textBorder);
                        out.printf(errorBornChildText);
                        return null;
                    } else {
                        throw new FamilyOverflowException(String.format(errorFamilySize, MAX_FAMILY_SIZE));
                    }
                });
    }

    public Family adoptChild(Family family, Human child) {
        return Optional.ofNullable(family)
                .filter(f -> f.countChildren() < MAX_FAMILY_SIZE)
                .map(f ->  {
                    f.addFamilyMember(child);
                    familyDao.saveFamily(f);
                    out.printf(textBorder);
                    out.printf(adoptChildText, child);
                    out.printf(textBorder);
                    return f;
                })
                .orElseGet(() -> {
                    if (family == null) {
                        out.printf(textBorder);
                        out.printf(errorBornChildText);
                    } else {
                        throw new FamilyOverflowException(String.format(errorFamilySize, MAX_FAMILY_SIZE));
                    }
                    return null;
                });
    }

    public void deleteAllChildrenOlderThan(int age) {
        getAllFamilies().stream()
                .peek(family -> {
                    List<Human> children = family.getChildren();
                    children.stream()
                            .filter(child -> {
                                LocalDate birthDate = LocalDate.ofInstant(
                                        java.time.Instant.ofEpochMilli(child.getBirthDay()), ZoneId.of("UTC"));
                                return LocalDate.now().minusYears(age).isAfter(birthDate);
                            })
                            .toList()
                            .forEach(child -> family.deleteChildByObject(child));
                })
                .forEach(family -> familyDao.saveFamily(family));
        out.printf(textBorder);
        out.printf(deleteChildrenOlderThanText, age);
        out.printf(textBorder);
    }

    public int count() {
        int count = (int) getAllFamilies().stream().count();
        out.printf(countFamiliesText, count);
        out.printf(textBorder);
        return count;
    }

    public Family getFamilyById(int index) {
        Family family = familyDao.getFamilyByIndex(index);
        Optional.ofNullable(family)
                .ifPresentOrElse(
                        f -> out.printf(getFamilyByIdText, index, f),
                        () -> out.printf(errorFamilyIndexText, index)
                );
        return family;
    }

    public Set<Pet> getPets(int index) {
        return new HashSet<>(Optional.ofNullable(familyDao.getFamilyByIndex(index))
                .map(Family::getPet)
                .orElseGet(Set::of));
    }

    public void addPet(int index, Pet pet) {
        Optional.ofNullable(familyDao.getFamilyByIndex(index))
                .ifPresentOrElse(
                        family -> {
                            family.addFamilyMember(pet);
                            familyDao.saveFamily(family);
                            out.printf(textBorder);
                            out.printf(addPetText, index, pet);
                        },
                        () -> {
                            out.printf(textBorder);
                            out.printf(errorFamilyIndexText, index);
                            out.printf(textBorder);
                        }
                );
    }

    public void fillWithTestData() {
        Map<String,String> defaultSchedule = new HashMap<>();
        defaultSchedule.put(DayOfWeek.MONDAY.name(), "Eat outside");
        defaultSchedule.put(DayOfWeek.FRIDAY.name(), "Walk in the park");

        Woman mother1 = new Woman("Lily", "Potter", "30/01/1960", 90, defaultSchedule, null);
        Man father1 = new Man("James", "Potter", "27/03/1960", 70, defaultSchedule, null);
        Family family1 = new Family(mother1, father1);
        family1.addFamilyMember(new Man("Harry", "Potter", "31/07/1980", 60, defaultSchedule, null));
        family1.addFamilyMember(new Woman("Ginny", "Potter", "28/08/1980", 50, defaultSchedule, null));
        family1.addFamilyMember(new Dog("Spike", 5, 80, Set.of("sleep")));
        family1.addFamilyMember(new DomesticCat("Tom", 3, 40, Set.of("eat")));
        familyDao.saveFamily(family1);

        Woman mother2 = new Woman("Porpentina", "Scamander", "19/08/1901", 90, defaultSchedule, null);
        Man father2 = new Man("Newton", "Scamander", "24/02/1897", 80, defaultSchedule, null);;
        Family family2 = new Family(mother2, father2);
        family2.addFamilyMember(new Man("Ron", "Scamander", "31/07/1925", 60, defaultSchedule, null));
        family2.addFamilyMember(new DomesticCat("Tom", 3, 40, Set.of("eat")));
        familyDao.saveFamily(family2);

        Woman mother3 = new Woman("Molly", "Weasley", "30/10/1949", 90, defaultSchedule, null);
        Man father3 = new Man("Arthur", "Weasley", "06/02/1950", 80, defaultSchedule, null);;
        Family family3 = new Family(mother3, father3);
        familyDao.saveFamily(family3);
    }
}
