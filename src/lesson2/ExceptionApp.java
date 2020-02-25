package lesson2;

public class ExceptionApp {

    public static void main(String[] args) {
        try {
           //justMethod();
            int[] c = {1,2,3};
            System.out.println(c[222]);
        }
//        catch (ArithmeticException e){
//            System.out.println("Ошибка деления на ноль");
//        }catch (ArrayIndexOutOfBoundsException e){
//            System.out.println("Ошибка в массиве");
//         catch (DivideByZeroException|ArrayIndexOutOfBoundsException e){
        catch (DivideByZeroException e){
             System.out.println("Ошибка");
             System.out.println(e.getMessage());
         }finally{
            System.out.println("Выполняется всегда");//даже если возникла ошибка, которой нет в кэтч
        }
         System.out.println("Finish");
    }

    private static void justMethod() {
        try {
            int a = 0;
            int b = divide(a);
            System.out.println("Это сообщение не будет выведено");
        }catch (DivideByZeroException e){
            System.out.println("Ошибка деления на ноль");
            //System.out.println(e.getMessage());
            throw e;
        }
    }

    private static int divide(int a) throws DivideByZeroException {
        if (a==0){
            throw new DivideByZeroException();
        }
        return 10/a;
    }
}
