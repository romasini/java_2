package lesson1.part2;

public class TestAnimal {

    public static void main(String[] args) {
        //обход значений Перечисления
        for (Color value: Color.values()) {
            System.out.println(value.getRussianColor());
        }

        Cat.CatAttribute catAttribute = new Cat.CatAttribute();
        Animal cat = new Cat("Kitty", Color.BLACK, catAttribute);
        Animal dog = new Dog("Rex", "red","setter");

        //cat.getCatAttribute();// - не работает, т.к. у Animal нет такого метода, на закастить объект - явно привести к классу Cat
        ((Cat)cat).getCatAttribute();

        System.out.println("instanceof: " + (cat instanceof Dog));//проверка на класс

        infoAndJump(cat);
        infoAndJump(dog);
    }

    private static void infoAndJump(Animal animal) {
        animal.animalInfo();
        animal.jump();
        animal.voice();
        System.out.println();
    }
}
