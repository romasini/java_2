package lesson1.part2;

public enum Color {
    BLACK("Черный"),
    WHITE("Белый"),
    RED("Красный"),
    GREY("Серый");

    private String russianColor;

    Color(String russianColor) {
        this.russianColor = russianColor;
    }

    public String getRussianColor() {
        return russianColor;
    }
}
