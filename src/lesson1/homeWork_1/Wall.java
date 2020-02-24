package lesson1.homeWork_1;

public class Wall implements Barrier{

    private final int height;
    private final String title;

    public Wall(String title, int height) {
        this.height = height;
        this.title = title;
    }

    @Override
    public boolean getBarrier(Runner runner) {
        boolean result = (runner.getHeight() >= this.height);
        if (!result) {
            System.out.println(this.getTitle() + " не поддалась участнику " + runner.getName());
        }else {
            System.out.println(runner.getName() + " успешно преодолел препятствие " + this.getTitle() );
        }
        return result;
    }

    @Override
    public String getTitle() {
        return "Стена " + this.title;
    }

}
