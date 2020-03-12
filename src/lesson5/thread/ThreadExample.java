package lesson5.thread;

public class ThreadExample {

    private static int count;

    public static void main(String[] args) throws InterruptedException {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                print(10);
//            }
//        });
        System.out.println(Thread.currentThread().getName());
        count = 1000;
//        printCount();
//        printCount();
        Thread thread1 = new Thread(() -> printCount(), "T1");
        Thread thread2 = new Thread(() -> printCount(), "T2");
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final value is " + count);
    }

    private static void printCount() {
        for(int i = 0; i < 500; i++) {
            count--;
//            synchronized (ThreadExample.class) {
//                count = count - 1;
//            }

//            System.out.println(Thread.currentThread().getName() + ": "+ count--);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(Thread.currentThread().getName() + ": "+ count);
    }
}
