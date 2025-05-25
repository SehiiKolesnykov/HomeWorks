package hw11;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import hw11.Human.*;
import hw11.Pets.*;

import java.util.*;

import static hw11.Texts.*;

public class Family {

    static {
        out.printf(loadingClassText, familyText);
        out.printf(creatingObjectText, familyText);
        out.printf(textBorder);
    }

    @JsonManagedReference
    private Woman mother;

    @JsonManagedReference
    private Man father;

    private List<Human> children;
    private Set<Pet> pets;

    @JsonCreator
    public Family(@JsonProperty("mother") Woman mother, @JsonProperty("father") Man father) {
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

    public String prettyFormat() {

        StringJoiner childrenJoiner = new StringJoiner(",", "children:", "\n");
        Optional.ofNullable(children)
                .ifPresentOrElse(
                        c -> c.forEach(child -> {
                            String type = child instanceof Man ? "boy" : "girl";
                            childrenJoiner.add(String.format("\n\t\t%s: %s", type, child.prettyFormat()));
                        }),
                        () -> childrenJoiner.add("[]")
                );

        StringJoiner petsJoiner = new StringJoiner(",\n\t\t ", "pets: \n\t\t[", "]");
        Optional.ofNullable(pets)
                .ifPresentOrElse(
                        p -> p.forEach(pet -> {
                            petsJoiner.add(String.format("%s", pet.prettyFormat()));
                        }),
                        () -> petsJoiner.add("")
                );

        return String.format("family:\n\tmother: %s,\n\tfather: %s,\n%s ",
                mother.prettyFormat(),
                father.prettyFormat(),
                String.format("\t%s\t%s", childrenJoiner, petsJoiner)
        );
    }

    @Override
    public String toString() {
        return prettyFormat();
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
