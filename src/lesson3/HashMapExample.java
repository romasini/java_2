package lesson3;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class HashMapExample {
    public static void main(String[] args) {
        Map<String,String> hashMap = new HashMap<>();//без порядка
        hashMap.put("Russia","Moscow");
        //hashMap.put("Russia","Smolensk"); //Москвы не будет
        hashMap.put("France","Paris");
        hashMap.put("Germany","Berlin");
        hashMap.put("Norway","Oslo");
        System.out.println(hashMap);

        Map<String,String> linkMap = new LinkedHashMap<>();//порядок добавления
        linkMap.put("Russia","Moscow");
        linkMap.put("France","Paris");
        linkMap.put("Germany","Berlin");
        linkMap.put("Norway","Oslo");
        System.out.println(linkMap);

        Map<String,String> treeMap = new TreeMap<>();//сортировка по ключу
        treeMap.put("Russia","Moscow");
        treeMap.put("France","Paris");
        treeMap.put("Germany","Berlin");
        treeMap.put("Norway","Oslo");
        System.out.println(treeMap);

        for (String key : treeMap.keySet()) {
            String value = treeMap.get(key);
            System.out.println(key + " - " + value);
        }

        treeMap.forEach((k,v)->{
            System.out.println(k+":"+v);
        });

        for (Map.Entry<String, String> o : treeMap.entrySet()) {
            System.out.println(o.getKey() + ": " + o.getValue());
        }

    }
}
