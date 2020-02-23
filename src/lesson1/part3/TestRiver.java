package lesson1.part3;

public class TestRiver {
    public static void main(String[] args) {
        River river = new River("Volga", 100);

        Waterfowl duck = new Duck("Duck");
        DogV2 dog = new DogV2("Rex", "Red", "Setter");
        Pet cat = new CatV2("Kitty","Black");

        System.out.println(river.doSwim(dog));
        System.out.println(river.doSwim(duck));

        swim(river, duck, dog);


    }

    private static void swim(River river, Waterfowl... waterfowls){
        for (Waterfowl waterfowl: waterfowls) {
            System.out.println(waterfowl);
            System.out.println(river.doSwim(waterfowl));
        }
    }

}
