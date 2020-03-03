package lesson3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("F");
        linkedList.add("B");
        linkedList.add("D");
        linkedList.add("E");
        linkedList.add("C");
        System.out.println(linkedList);
        linkedList.addLast("Z");
        linkedList.addFirst("A");
        System.out.println(linkedList);
        linkedList.add(1,"A2");
        System.out.println(linkedList);
        linkedList.remove("F");
        linkedList.remove(2);
        System.out.println(linkedList);
        linkedList.removeFirst();
        linkedList.removeLast();
        System.out.println(linkedList);
        String val = linkedList.get(2);
        linkedList.set(1, val + "changed");
        System.out.println(linkedList);

        List<String> arr = new ArrayList(linkedList); //сделать
        System.out.println(arr);

        List list_arr = new LinkedList(new ArrayList());

        new LinkedList().addAll(new ArrayList());

    }
}
