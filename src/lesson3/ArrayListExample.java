package lesson3;

import java.util.*;

public class ArrayListExample {
    public static void main(String[] args) {
        arrayExample();
        System.out.println();
        arrayListArray();
        System.out.println();
        arrayListExample();
    }

    private static void arrayExample(){

        //увеличение длины массива
        int[] arr = {1,2,3,4};
        //int[10] = 5;//выдаст ошибку, массив не меняет размер
        int[] arrNew = new int[10];
        System.arraycopy(arr, 0, arrNew, 0, arr.length);
        arr = arrNew;
        arrNew = null;
        arr[5] = 3;
        System.out.println(Arrays.toString(arr));

        //
        Integer[] arrInt = new Integer[4];
        Integer[] arrIntNew = Arrays.copyOf(arrInt, 10);
        arrInt = arrIntNew;
        arrIntNew = null;
        arrInt[5] = 6;
        System.out.println(Arrays.toString(arrInt));

    }

    private static void arrayListArray(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        //list.addAll(List.of(1,2));//of работает с более поздних версий
        System.out.println(list);

        Integer[] arr = new Integer[list.size()];
        list.toArray(arr);
        System.out.println(Arrays.toString(arr));

        Integer[] arr1 = list.toArray(new Integer[0]);
        System.out.println(Arrays.toString(arr1));
    }

    private static void arrayListExample(){
        LinkedList<String> newDataName = new LinkedList<>();
        newDataName.add("B");
        newDataName.add("A");
        newDataName.add("C");
        newDataName.add("E");
        newDataName.add("D");
        newDataName.add("E");
        newDataName.add("E");
        System.out.println(newDataName);
        newDataName.sort(Comparator.naturalOrder());//задать компаратор по умолчанию
        System.out.println(newDataName);
        newDataName.add(1,"G1");
        System.out.println(newDataName);
        newDataName.remove("E");//удаляет первый встречный
        System.out.println(newDataName);
        newDataName.remove(2);
        System.out.println(newDataName);
        System.out.println("contains C? " + newDataName.contains("C"));

        for (int i = 0; i < newDataName.size(); i++) {
            System.out.println(newDataName.get(i));//newDataName[i]
        }

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(3);
        data.add(1);
        System.out.println(data);
        data.remove(1);//удалит по индексу, т.к. в коллекции хранятся объекты, а не примитивы
        System.out.println(data);

        data.sort(Comparator.naturalOrder());
        System.out.println(data);

    }
}
