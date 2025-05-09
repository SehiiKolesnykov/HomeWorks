package hw06;

import hw06.Human.*;
import hw06.Pets.*;

import java.io.PrintStream;
import java.util.Objects;

public class Family {

    static final PrintStream out = System.out;

    static {
        out.printf(Texts.loadingClassText, Texts.familyText);
        out.printf(Texts.creatingObjectText, Texts.familyText);
        out.printf(Texts.textBorder);
    }

    private Woman mother;
    private Man father;
    private Human[] children;
    private Pet pet;


    public Family(Woman mother, Man father) {
        this.mother = mother;
        this.father = father;
        this.children = new Human[0];
        mother.setFamily(this);
        father.setFamily(this);
    }

    public Human getMother() { return mother; }
    public Human getFather() { return father; }
    public Human[] getChildren() { return children; }
    public Pet getPet() { return pet; }

    public void setMother(Woman mother) { this.mother = mother; }
    public void setFather(Man father) { this.father = father; }
    public void setChildren(Human[] children) { this.children = children; }
    public void setPet(Pet pet) { this.pet = pet; }

    public void addChild(Human child) {

        Human[] newChildren = new Human[children.length + 1];
        System.arraycopy(children, 0, newChildren, 0, children.length);
        newChildren[children.length] = child;
        children = newChildren;
        child.setFamily(this);

    }

    public boolean deleteChild(int index) {

        if (index < 0 || index >= children.length) { return false; }

        children[index].setFamily(null);
        Human[] newChildren = new Human[children.length - 1];
        System.arraycopy(children, 0, newChildren, 0, index);
        System.arraycopy(children, index + 1, newChildren, index, children.length - index - 1);
        children = newChildren;


        return true;
    }

    public boolean deleteChild(Human child) {

        for (int i = 0; i < children.length; i++) {
            if (children[i].equals(child)) {
                children[i].setFamily(null);
                return deleteChild(i);
            }
        }

        return false;
    }

    public int countChildren() { return children.length; }

    public int countFamily() { return 2 + children.length; }

    @Override
    protected void finalize() throws Throwable {
        try {
            out.println("Finalize Family: " + this.toString());
        } finally {
            super.finalize();
        }
    }

    @Override
    public String toString() {

        StringBuilder childrenString = new StringBuilder("[");
        for (int i = 0; i < children.length; i++) {
            if (children[i] instanceof Man) {
                childrenString.append("Man");
            } else {
                childrenString.append("Woman");
            }
            childrenString.append(children[i].toString());
            if (i < children.length - 1) {
                childrenString.append(",\n");
            }
        }
        childrenString.append("]");

        StringBuilder result = new StringBuilder("\nFamily\n{ mother = Woman");
        result
                .append(mother)
                .append(", \n\nfather = Man")
                .append(father)
                .append(", \n\nchildren = ")
                .append(childrenString)
                .append(", \n\npet = ")
                .append(pet != null ? pet : "null")
                .append("}")
                .append(Texts.textBorder);

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Family family)) return false;

        return Objects.equals(mother, family.mother)
                && Objects.equals(father, family.father);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mother, father);
    }
}
