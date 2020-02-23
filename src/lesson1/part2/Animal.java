package lesson1.part2;

public abstract class Animal {

    private final String name;//после создания объекта нельзя менять это свойство, поэтому оно должно быть в конструкторе

    protected Animal(String name) {
        this.name = name;
    }

    public void animalInfo(){
        System.out.println("Animal is " + name);
    }

    public void jump(){
        System.out.println("Animal jumped");
    }

    public String getName(){
        return name;
    }

    public abstract void voice();//абстрактный метод - следовательно класс должен быть абстрактным

    @Override
    public String toString() {
        return "Animal is " + this.name;
    }
}
