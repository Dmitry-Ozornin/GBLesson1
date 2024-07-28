package ru.famally_tree.Human;

import java.util.Comparator;

public class SortByAgeComporator implements Comparator<Human> {


    @Override
    public int compare(Human o1, Human o2) {
        return o2.getAge() - o1.getAge();
    }
}
