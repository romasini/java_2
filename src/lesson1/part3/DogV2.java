package lesson1.part3;
import lesson1.part2.Animal;

public class DogV2 extends Animal implements Pet, Waterfowl{

    private final String color;
    private final String type;

    public DogV2(String name, String color, String type) {
        super(name);
        this.color = color;
        this.type = type;
    }

    @Override
    public void animalInfo() {
        System.out.println("Dos name is " + super.getName() + "; color - " + color + "; type - " + type);
    }

    @Override
    public void voice() {
        System.out.println("Bark!");
    }

    @Override
    public void loveMaster() {
        System.out.println("Bark Bark Bark!");
    }

    @Override
    public boolean isUseful() {
        return true;
    }

    @Override
    public int swim() {
        System.out.println("Swim like a dog");
        return Waterfowl.DEFAULT_SWIM_LENGTH;
    }
}

