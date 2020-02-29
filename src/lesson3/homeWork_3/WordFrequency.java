package lesson3.homeWork_3;

import java.util.HashMap;
import java.util.Map;

/*
Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
Посчитать сколько раз встречается каждое слово.
 */

public class WordFrequency {

    public static void main(String[] args) {
        String words = "раз создать массив с набором слов должны встречаться повторяющиеся слово найти " +
                "и вывести раз список уникальных слов из которых состоит раз массив дубликаты не " +
                "считаем посчитать сколько раз встречается каждое слово";

        String[] wordsArray = words.split(" ");

        Map<String, Integer> wordsFreq = new HashMap<>();
        for (String word: wordsArray) {
            Integer value = wordsFreq.get(word);
            if (value==null){
                wordsFreq.put(word,1);
            }else{
                wordsFreq.put(word,  ++value);
            }
        }

        wordsFreq.forEach((k,v)->{
            System.out.println(k+":"+v);
        });

    }


}
