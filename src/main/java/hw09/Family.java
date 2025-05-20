package hw09;

import hw09.Human.*;
import hw09.Pets.*;

import java.util.*;

import static hw09.Texts.*;

public class Family {

    static {
        out.printf(loadingClassText, familyText);
        out.printf(creatingObjectText, familyText);
        out.printf(textBorder);
    }

    private Woman mother;
    private Man father;
    private List<Human> children;
    private Set<Pet> pets;


    public Family(Woman mother, Man father) {
        this.mother = mother;
        this.father = father;
        this.children = new ArrayList<>();
        this.pets = new HashSet<>();
        mother.setFamily(this);
        father.setFamily(this);
    }

    public Human getMother() { return mother; }
    public Human getFather() { return father; }
    public List<Human> getChildren() { return children; }
    public Set<Pet> getPet() { return pets; }

    public void setMother(Woman mother) { this.mother = mother; }
    public void setFather(Man father) { this.father = father; }
    public void setChildren(List<Human> children) {
        this.children = children != null ? children : new ArrayList<>();
    }
    public void setPet(Set<Pet> pets) {
        this.pets = pets != null ? pets : new HashSet<>();
    }

    public void addFamilyMember(Object member) {

        switch (member) {
            case null -> {
                return;
            }
            case Human human -> {
                children.add(human);
                human.setFamily(this);
            }
            case Pet pet -> pets.add(pet);
            default -> {
                out.printf(errorMemberTypeText);
            }
        }
    }

    public boolean deleteChildByIndex(int index) {

        if (index < 0 || index >= children.size()) { return false; }

        children.get(index).setFamily(null);
        children.remove(index);

        return true;
    }

    public boolean deleteChildByObject(Human child) {

        if (child == null || child.getFamily() == null || !child.getFamily().equals(this)) { return false; }
        if (children.contains(child)) {
            int index = children.indexOf(child);
            children.get(index).setFamily(null);
            children.remove(child);
        return true;
        }

        return false;
    }

    public boolean deletePet(Pet pet) {
        if (pet != null) {
            pets.remove(pet);
            return true;
        }
        return false;
    }

    public int countChildren() { return children.size(); }

    public int countFamily() { return 2 + children.size(); }

    public int countPets() { return pets.size(); }

    @Override
    public String toString() {

        StringJoiner childrenJoiner = new StringJoiner(",\n", "[", "]");

        children.forEach(child -> {
            if (child instanceof Man){
                childrenJoiner.add(String.format("\nMan%s", child.toString()));
            } else {
                childrenJoiner.add(String.format("\nWoman%s", child.toString()));
            }
        });

        StringJoiner petsJoiner = new StringJoiner(",\n", "[", "]");
        pets.forEach(pet -> petsJoiner.add(pet.toString()));

        StringJoiner familyJoiner = new StringJoiner
                (", \n\n", "\nFamily\n{ mother = Woman", String.format("}%s", textBorder));
        familyJoiner
                .add(String.format("%s", mother))
                .add(String.format("father = Man%s", father))
                .add(String.format("children = %s", childrenJoiner))
                .add(String.format("pets = %s", pets != null ? petsJoiner : null));

        return familyJoiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Family family)) return false;

        return Objects.equals(mother, family.mother)
                && Objects.equals(father, family.father)
                && children.containsAll(family.children)
                && pets.containsAll(family.pets);
    }

    @Override

    public int hashCode() {
        return Objects.hash(mother, father);
    }
}
