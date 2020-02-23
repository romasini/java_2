package lesson1.homeWork_1;

public class Track implements Barrier{
    private final int length;
    private final String title;

    public Track(String title, int length) {
        this.length = length;
        this.title = title;
    }

    @Override
    public boolean getBarrier(Runner runner) {
        boolean result = (runner.getLength() >= this.length);
        if (!result) {
            System.out.println(this.getTitle() + " не поддалась участнику " + runner.getName());
       }else {
            System.out.println(runner.getName() +" успешно пробежал "+this.getTitle() );
        }
        return result;
    }

    @Override
    public String getTitle() {
        return "Дорога " + this.title;
    }
}
