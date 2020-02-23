package lesson1.part1;

public class DocumentEx2 {
    String title;
    String content;

    void printInfo(){
        System.out.println(title + System.lineSeparator() + content);//разделитель строк, заданный в системе
    }

    public static void main(String[] args) {
        DocumentEx2 doc1 = new DocumentEx2();
        doc1.title = "doc 1";
        doc1.content = "content doc 1";

        doc1.printInfo();

        DocumentEx2 doc2 = new DocumentEx2();
        doc2.title = "doc 2";
        doc2.content = "content doc 2";

        doc2.printInfo();
    }
}
