package lesson5.homeWork_5;

import java.util.Arrays;

public class TestThread {

    private final static int SIZE = 10000000;
    private final static int HALF_SIZE = 10000000/2;

    public static void main(String[] args) {

        methodNoThread();
        try {
            methodWithThread();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void methodNoThread(){
        float[] arr = new float[SIZE];
        long startTime, endTime;
        Arrays.fill(arr, 1);

        startTime = System.currentTimeMillis();
        calcArr(arr, 0);
        endTime = System.currentTimeMillis();
        System.out.println("Without thread :" + (endTime-startTime));

    }

    private static void methodWithThread() throws InterruptedException {
        float[] arr = new float[SIZE];
        float[] arr1 = new float[HALF_SIZE];
        float[] arr2 = new float[SIZE - HALF_SIZE];
        long startTime, endTime;
        Arrays.fill(arr, 1);

        startTime = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF_SIZE);
        System.arraycopy(arr, HALF_SIZE, arr2, 0, SIZE - HALF_SIZE);
        Thread thread1 = new Thread(()->calcArr(arr1,0));
        Thread thread2 = new Thread(()->calcArr(arr2, HALF_SIZE));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.arraycopy(arr1, 0, arr, 0, HALF_SIZE);
        System.arraycopy(arr2, 0, arr, HALF_SIZE, SIZE - HALF_SIZE);

        endTime = System.currentTimeMillis();
        System.out.println("With thread :" + (endTime-startTime));

    }

    private static void calcArr(float[] arr, int delta){
        for(int i =0; i < arr.length; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + (i+delta)/5) * Math.cos(0.2f + (i+delta)/5) * Math.cos(0.4f + (i+delta)/2));
        }
    }




}
