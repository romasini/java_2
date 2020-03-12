package lesson2.homeWork_2;

public class MyArraySizeException extends ArrayIndexOutOfBoundsException{

    public MyArraySizeException(){

        super("Неверный размер массива");
    }
}
