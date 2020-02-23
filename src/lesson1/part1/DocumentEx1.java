package lesson1.part1;

public class DocumentEx1 {
    private String title;//доступ только внутри этого класса
    private String content;

    public static void main(String[] args) {
        DocumentEx1 doc = new DocumentEx1();
        doc.title = "первый док";
        doc.content = "контент первый док";

        System.out.println(doc.title);
        System.out.println(doc.content);
    }
}
