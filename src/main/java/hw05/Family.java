package hw05;

import java.io.PrintStream;
import java.util.Objects;

public class Family {

    static PrintStream out = System.out;

    static {
        out.println("\nLoading new class: Family...");
        out.println("Creating new object: Family...");
        out.println("--------------------------------------");
    }

    private Human mother;
    private Human father;
    private Human[] children;
    private Pet pet;


    public Family(Human mother, Human father) {
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

    public void setMother(Human mother) { this.mother = mother; }
    public void setFather(Human father) { this.father = father; }
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
    public String toString() {

        StringBuilder childrenString = new StringBuilder("[");
        for (int i = 0; i < children.length; i++) {
            childrenString.append(children[i].toString());
            if (i < children.length - 1) {
                childrenString.append(", ");
            }
        }
        childrenString.append("]");

        StringBuilder result = new StringBuilder("\nFamily\n{ mother = ");
        result
                .append(mother)
                .append(", \nfather = ")
                .append(father)
                .append(", \nchildren = ")
                .append(childrenString)
                .append(", \npet = ")
                .append(pet != null ? pet : "null")
                .append("}");

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
