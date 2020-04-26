package lessonJava3_6;

import java.util.Arrays;

/**
 * Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
 * Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
 * идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе
 * необходимо выбросить RuntimeException. Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 * Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
*/

public class ReturnNewArray {
    public int[] returnArrayAfterFour(int[] sourceArray) throws RuntimeException{
        boolean notFour = true;
        int lastFour = 0;
        for (int i = 0; i<sourceArray.length; i++){
            if (sourceArray[i]==4) {
                notFour = false;
                lastFour = i;
            }
        }
        if (notFour) throw new RuntimeException();
        int[] result  = Arrays.copyOfRange(sourceArray, lastFour+1, sourceArray.length);
        return result;
    }
}
