package lesson1.homeWork_1;

public class Race {

    public static void main(String[] args) {

        //трасс
        System.out.println("Сегодня бегуны должны пройти трассу, состоящую из ");

        Barrier[] barriers = {  new Track("грязная", 50),
                                new Wall("низкая", 1),
                                new Track("извилистая", 150),
                                new Wall("средняя", 2),
                                new Track("финишная", 300)
                                };

        for (Barrier barrier: barriers){
            System.out.println(barrier.getTitle() + " ");
        }

        //список участников
        Runner[] runners = {
                new Cat("Barsik",1,30),
                new Human("Peter",2,100),
                new Robot("UltraKiller",10,1000),
                new Robot("R2-D2",0,70)
        };

        System.out.println();
        System.out.println("Участники ");

        for (Runner runner: runners){
            System.out.println(runner.getName());
            runner.canJump();
            runner.canRun();
        }

        System.out.println();
        System.out.println("Гонка началась!!!");

        for (Barrier barrier: barriers){
            for (int i=0; i<runners.length; i++) {
                if (runners[i] == null) continue;//выбывшие участники дальше не бегут
                if (!barrier.getBarrier(runners[i])){
                    System.out.println(runners[i].getName() + " выбывает из гонки");
                    runners[i] = null;
                }
            }
        }

        System.out.println("Наши чемпионы:");
        for (Runner runner: runners){
            if (runner ==null) continue;
            System.out.println(runner.getName() + " ");
        }



    }

}
