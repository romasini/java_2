package lessonJava3_4.homework4;

public class PrintABC {
    private final Object monitor = new Object();
    private final static int MAX_COUNT = 5;
    private char currentLetter = 'A';

    public static void main(String[] args) {
        PrintABC abc = new PrintABC();
        Thread threadA = new Thread(()-> abc.printLetter('A', 'B'));
        Thread threadB = new Thread(()-> abc.printLetter('B', 'C'));
        Thread threadC = new Thread(()-> abc.printLetter('C','A'));

        threadA.start();
        threadB.start();
        threadC.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printLetter(char letter, char nextLetter){
        synchronized (monitor){
            for (int i=0; i < MAX_COUNT; i++){
                while(currentLetter != letter) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(letter);
                currentLetter = nextLetter;
                monitor.notifyAll();
            }
        }
    }
}
