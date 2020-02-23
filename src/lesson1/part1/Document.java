package lesson1.part1;

import javax.print.Doc;
import javax.print.attribute.standard.DocumentName;
import java.util.Objects;

public class Document {

    public static final String DEFAULT_TITLE = "Unknown";//константа

    String title;//доступ по умолчанию  - доступ для классов в одном пакете
    protected String content;//доступен для классов в одном пакете и для подклассов

    int count;

    public Document(String title, String content) {//конструктор
        this.title = title;
        this.content = content;
    }

    public Document(String content){
        this(DEFAULT_TITLE, content); //перегруженный конструктор, который вызывает первый конструктор
    }


    //ГЕТТЕРЫ и СЕТТЕРЫ через Alt+Insert
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override //класс является подклассом Object, поэтому может переопределить toString
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(title, document.title) &&
                Objects.equals(content, document.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }

    public static void main(String[] args) {
        Document doc1 = new Document("my title", "my content");
        Document doc2 = new Document("my title", "my content");

        System.out.println(doc1==doc2);
        System.out.println(doc1.equals(doc2));
    }
}
