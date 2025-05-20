package hw09;

import hw09.Human.*;
import hw09.Pets.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static hw09.Texts.*;

public class FamilyService {
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
        return Optional.ofNullable(family.getMother())
                .map(Woman.class::cast)
                .map(mother -> mother.bornChild(birthDayString))
                .map(child -> {
                    if (child instanceof Man) child.setName(maleName);
                    else child.setName(femaleName);
                    familyDao.saveFamily(family);
                    out.printf(textBorder);
                    out.printf(bornChildText, child);
                    return family;
                })
                .orElseGet(() -> {
                    out.printf(textBorder);
                    out.printf(errorBornChildText);
                    return null;
                });
    }

    public Family adoptChild(Family family, Human child) {
        Optional.ofNullable(family)
                .ifPresent(f ->  {
                    f.addFamilyMember(child);
                    familyDao.saveFamily(f);
                    out.printf(textBorder);
                    out.printf(adoptChildText, child);
                    out.printf(textBorder);
                });
        return family;
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
}
