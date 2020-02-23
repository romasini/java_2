package lesson1.part2;

public class Cat extends Animal{

    private Color color = Color.RED;
    private CatAttribute catAttribute;

    public static class CatAttribute {
        private String meal;
        private int weight;
        private Color colorEyes;
    }

    public Cat(String name, Color color, CatAttribute catAttribute) {
        super(name);
        this.color = color;
        this.catAttribute = catAttribute;
    }

    @Override
    public void animalInfo() {

        System.out.println("Cat name is" + super.getName() + "; color "+ color);
    }

    @Override
    public void voice() {
        System.out.println("Meow");
    }

    public CatAttribute getCatAttribute() {
        return catAttribute;
    }
}
