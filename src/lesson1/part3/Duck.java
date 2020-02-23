package lesson1.part3;

import lesson1.part2.Animal;

public class Duck extends Animal implements Pet, Waterfowl{
    public static final int SWIM_LENGTH = 150;

    public Duck(String name) {
        super(name);
    }

    @Override
    public void voice() {
        System.out.println("Quack");
    }

    @Override
    public void loveMaster() {
        System.out.println("Quack Quack Quack");
    }

    @Override
    public boolean isUseful() {
        return true;
    }

    @Override
    public int swim() {
        System.out.println("Swim like a duck");
        return SWIM_LENGTH;
    }
}
