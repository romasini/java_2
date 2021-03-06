package lesson1.homeWork_1;

public class Robot implements Runner{

    private final String name;
    private final int height;
    private final int length;

    public Robot(String name, int height, int length) {
        this.name = name;
        this.height = height;
        this.length = length;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public String getName() {
        return "Робот " + name;
    }

    @Override
    public void canRun() {
        System.out.println("Может пробежать "+ this.getLength());
    }

    @Override
    public void canJump() {
        System.out.println("Может прыгнуть на "+ this.getHeight());
    }
}
