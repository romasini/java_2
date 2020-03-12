package lesson2.homeWork_2;

public class ReadMatrix {

    private static final int SIZE_MATRIX = 4;

    public static void main(String[] args) {

        ReadMatrix exp = new ReadMatrix();

        //1. нормальная матрица
        System.out.println("Проверка матрицы 1:");
        String[][] matrix = {{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"},{"1","2","3","4"}};
        try {
            exp.printMatrix(matrix);
            System.out.printf("Сумма элементов %d", exp.sumMatrix(matrix));
        }catch (MyArrayDataException|MyArraySizeException e){
            System.out.println(e.getMessage());
        }
        System.out.println();

        //2. некорректные размеры матрицы
        System.out.println("Проверка матрицы 2:");
        String[][] matrix1 = {{"1","2","3","4"},{"1","2","3","4"},{"1","2","3"}};
        try {
            exp.printMatrix(matrix1);
            System.out.printf("Сумма элементов %d", exp.sumMatrix(matrix1));
        }catch (MyArrayDataException|MyArraySizeException e){
            System.out.println(e.getMessage());
        }

        //3. некорректные данные матрицы
        System.out.println("Проверка матрицы 3:");
        String[][] matrix2 = {{"1","2","3","4"},{"1","о","3","4"},{"1","2","3","4"},{"1","2","3","4"}};
        try {
            exp.printMatrix(matrix2);
            System.out.printf("Сумма элементов %d", exp.sumMatrix(matrix2));
        }catch (MyArrayDataException|MyArraySizeException e){
            System.out.println(e.getMessage());
        }


    }

    public int sumMatrix(String[][] matrix) throws MyArraySizeException, MyArrayDataException{
        int sum = 0;
        int current = 0;

        if(matrix.length != SIZE_MATRIX ){
            throw new MyArraySizeException();
        }
        for(int i =0; i<matrix.length; i++){
            if(matrix[i].length != SIZE_MATRIX){
                throw new MyArraySizeException();
            }
            for(int j = 0; j<matrix[i].length; j++){
                try{
                    current = Integer.parseInt(matrix[i][j]);
                } catch (NumberFormatException e){
                    throw new MyArrayDataException(String.format("Некорректные данные в ячейке %d - %d", i, j));
                }
                sum = sum + current;
            }

        }

        return sum;
    }

    public void printMatrix(String[][] matrix){
        for (int i =0; i<matrix.length; i++){
            for (int j =0; j<matrix[i].length; j++){
                System.out.printf("%s ",matrix[i][j]);
            }
            System.out.println();
        }
    }
}
