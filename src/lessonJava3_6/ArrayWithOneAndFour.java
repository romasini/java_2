package lessonJava3_6;

/**
 * Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
 * то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 */

public class ArrayWithOneAndFour {
    public boolean isHaveJustOneFour(int[] sourceArray){
        boolean haveFour = false;
        boolean haveOne = false;
        for (int i = 0; i<sourceArray.length; i++){
            if(sourceArray[i]!=1 && sourceArray[i]!=4) return false;//нашли число не равное 1 и 4
            if(sourceArray[i]==4) haveFour = true;
            if(sourceArray[i]==1) haveOne = true;
        }
        return haveOne && haveFour;
    }
}
