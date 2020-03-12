package lesson3.homeWork_3;

import java.util.Arrays;

/*
Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
В этот телефонный справочник с помощью метода add() можно добавлять записи.
С помощью метода get() искать номер телефона по фамилии.
Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
тогда при запросе такой фамилии должны выводиться все телефоны.
 */

public class TestYellowBook {
    public static void main(String[] args) {
        YellowBook yellowBook = new YellowBook();
        yellowBook.add("Petrov","89995556633");
        yellowBook.add("Sidorov","89995546633");
        yellowBook.add("Petrov","87995556633");
        yellowBook.add("Ivanov","89995556623");
        yellowBook.add("Petrov","89995556633");
        yellowBook.add("Sidorov","89995556633");

        String[] phonesPetrov = yellowBook.get("Petrov");
        System.out.println("Petrov:");
        System.out.println(phonesPetrov.length==0?"No phones": Arrays.toString(phonesPetrov));

        String[] phonesSidorov = yellowBook.get("Sidorov");
        System.out.println("Sidorov:");
        System.out.println(phonesSidorov.length==0?"No phones": Arrays.toString(phonesSidorov));

        String[] phonesAndreev = yellowBook.get("Andreev");
        System.out.println("Andreev:");
        System.out.println(phonesAndreev.length==0?"No phones": Arrays.toString(phonesAndreev));

    }
}
