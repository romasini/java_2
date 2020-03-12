package lesson3;

import java.util.*;
import java.util.function.Function;

public class SetExample {
    public static void main(String[] args) {
        //список уникальных элементов
        Set<String> hashSet = new HashSet<>();//порядок не гарантирует
        hashSet.add("alpha");
        hashSet.add("beta");
        hashSet.add("alpha");
        hashSet.add("etha");
        hashSet.add("gamma");
        hashSet.add("epsilon");
        hashSet.add("omega");
        hashSet.add("gamma");
        System.out.println(hashSet);

        Set<String> linkSet = new LinkedHashSet<>();//порядок добавления
        linkSet.add("alpha");
        linkSet.add("beta");
        linkSet.add("alpha");
        linkSet.add("etha");
        linkSet.add("gamma");
        linkSet.add("epsilon");
        linkSet.add("omega");
        linkSet.add("gamma");
        System.out.println(linkSet);

        Set<String> treeSet = new TreeSet<>();//отсортированный порядок
        treeSet.add("alpha");
        treeSet.add("beta");
        treeSet.add("alpha");
        treeSet.add("etha");
        treeSet.add("gamma");
        treeSet.add("epsilon");
        treeSet.add("omega");
        treeSet.add("gamma");
        System.out.println(treeSet);

        //Set<Person> personSet = new TreeSet<>();
        //Set<Person> personSet = new TreeSet<>(Comparator.comparing(Person::getName));
        //Set<Person> personSet = new TreeSet<>(Comparator.comparing( person-> person.getName()));
        Set<Person> personSet = new TreeSet<>(Comparator.comparing(new Function<Person, String>() {
            @Override
            public String apply(Person person){
                return person.getName();
            }
        }));
        Person oleg = new Person("Oleg","Petrov",27);
        Person alex = new Person("Alex","Petrov",29);
        personSet.add(oleg);
        personSet.add(alex);

        System.out.println(personSet);
    }
}
