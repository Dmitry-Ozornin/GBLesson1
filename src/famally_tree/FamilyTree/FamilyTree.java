package ru.famally_tree.FamilyTree;

import ru.famally_tree.Human.Human;
import ru.famally_tree.Human.SortByAgeComporator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FamilyTree implements Serializable {

    private List<Human> humans;
    private long humansId;

    public FamilyTree() {
        this(new ArrayList<>());
    }

    public FamilyTree(List<Human> humans) {
        this.humans = humans;

    }

    public boolean addHumans(Human human) {
        if (human == null) {
            return false;
        }
        if (!humans.contains(human)) {
            humans.add(human);
            human.setId(humansId++);

            addToParent(human);
            addToChildren(human);

            return true;
        }
        return false;
    }

    private void addToParent(Human human) {
        for (Human parent : human.getParents()) {
            parent.addChildren(human);
        }
    }

    private void addToChildren(Human human) {
        for (Human child : human.getChildren()) {
            child.addParent(human);
        }
    }

    public List<Human> getSibLins(int id) {
        Human human = getById(id);
        if (human == null) {
            return null;
        }
        List<Human> res = new ArrayList<>();
        for (Human parent : human.getParents()) {
            for (Human child : parent.getChildren()) {
                if (!child.equals(human)) {
                    res.add(child);
                }
            }
        }
        return res;
    }

    public List<Human> getByName(String name) {
        List<Human> res = new ArrayList<>();
        for (Human human : humans) {
            if (human.getName().equals(name)) {
                res.add(human);
            }
        }
        return res;
    }

    public boolean setWedding(long humanId1, long humanId2) {
        if (checkId(humanId1) && checkId(humanId2)) {
            Human human1 = getById(humanId1);
            Human human2 = getById(humanId2);
            return setWedding(humanId1, humanId2);
        }
        return false;
    }

    public boolean setWedding(Human human1, Human human2) {
        if (human1.getSpouse() == null && human2.getSpouse() == null) {
            human1.setSpouse(human2);
            human2.setSpouse(human1);
            return true;
        } else {
            return false;
        }
    }

    public boolean setDivorse(long humanId1, long humanId2) {
        if (checkId(humanId1) && checkId(humanId2)) {
            Human human1 = getById(humanId1);
            Human human2 = getById(humanId2);
            return setDivorse(human1, human2);
        }
        return false;
    }

    public boolean setDivorse(Human human1, Human human2) {
        if (human1.getSpouse() != null && human2.getSpouse() != null) {
            human1.setSpouse(null);
            human2.setSpouse(null);
            return setDivorse(human1, human2);
        }
        return false;
    }

    public boolean remove(long humansId) {
        if (checkId(humansId)) {
            Human human = getById(humansId);
            return humans.remove(human);
        }
        return false;

    }

    public boolean checkId(long id) {
        return id < humansId && id >= 0;
    }

    public Human getById(long id) {
        for (Human human : humans) {
            if (human.getId() == id) {
                return human;
            }
        }
        return null;
    }
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();

    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();


    }

    public void sortByAge(){
        humans.sort(new SortByAgeComporator());
    }

    public void sortByName(){
        Collections.sort(humans);
    }


    @Override
    public String toString() {
        return info();
    }

    public String info() {
        StringBuilder sb = new StringBuilder();
        sb.append("Древо: ");
        sb.append(humans.size());
        sb.append(" людей: \n");
        for (Human human : humans) {
            sb.append(human);
            sb.append("\n");
        }
        return sb.toString();

    }



}
