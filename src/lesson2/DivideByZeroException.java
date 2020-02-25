package lesson2;

public class DivideByZeroException extends ArithmeticException{
    public DivideByZeroException(){
        super("Ошибка деления на ноль !!!!");
    }
}
