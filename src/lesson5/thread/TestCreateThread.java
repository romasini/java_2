package lesson5.thread;

public class TestCreateThread {

    static class MyRunnableClass implements Runnable {

        private int delayInMilli;

        public MyRunnableClass(int delayInMilli) {
            this.delayInMilli = delayInMilli;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(delayInMilli);
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interruped from code");
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + ": new thread: " + i
                            + " with priority: " + Thread.currentThread().getPriority());

                } catch (InterruptedException e) {
                    System.out.println("Interruped from exception");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyRunnableClass(100), "Поток 1");
        thread1.setPriority(Thread.MIN_PRIORITY);

        Thread.currentThread().setPriority(Thread.NORM_PRIORITY);

        Thread thread2 = new Thread(new MyRunnableClass(100), "Поток 2");
//        thread2.setPriority(Thread.MAX_PRIORITY);

//        thread1.start();
        thread2.start();
//        while (thread1.isAlive()) {
//            System.out.println("Поток 1 живой");
//            Thread.sleep(10);
//        }
//        System.out.println("Поток 1 мертвый! Прерываем 2 поток");
        Thread.sleep(250);
        thread2.interrupt();

//        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
//        Thread thread3 = new Thread(TestCreateThread::test);
//        thread3.start();

    }

    private static void test() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
                System.out.println("new thread: " + i);
                System.out.println(Thread.currentThread().getName());
                System.out.println("Приоритет:" + Thread.currentThread().getPriority());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
