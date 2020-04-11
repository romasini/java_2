package lessonJava3_4.homework4;

public class PrintABC_ver_2 {
    private final Object monitor = new Object();
    private final static int MAX_COUNT = 5;
    private final static int MAX_LETTER_GROUP = 3;//по три
    private final static int START_POSITION = 1;
    private int currentLetterPosition = START_POSITION;

    public static void main(String[] args) {
        PrintABC_ver_2 abc = new PrintABC_ver_2();
        Thread threadA = new Thread(()-> abc.printLetter('A', 1));
        Thread threadB = new Thread(()-> abc.printLetter('B', 2));
        Thread threadC = new Thread(()-> abc.printLetter('C', 3));

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

    public void printLetter(char letter, int positionLetter){
        synchronized (monitor){
            for (int i=0; i < MAX_COUNT; i++){
                while(currentLetterPosition != positionLetter) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(letter);
                currentLetterPosition = (currentLetterPosition>=MAX_LETTER_GROUP)? START_POSITION : positionLetter + 1;
                monitor.notifyAll();
            }
        }
    }
}
