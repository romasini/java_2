
import lessonJava3_6.ReturnNewArray;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)//параметроризованные тесты
public class ReturnNewArrayTest {
    private static ReturnNewArray returnNewArray = null;
    private int[] sourceArray;
    private int[] resultArray;
    private boolean runtimeException;

    public ReturnNewArrayTest(int[] sourceArray, int[]resultArray, boolean runtimeException){//конструктор для параметроризованных тестов
        this.sourceArray = sourceArray;
        this.resultArray = resultArray;
        this.runtimeException = runtimeException;
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();// в junit 5 от правил избавляются

    @Parameters
    public static Collection abracadabra(){
        return Arrays.asList(new Object[][]{
                {new int[]{1,2,4,4,2,3,4,1,7}, new int[]{1,7}, false},
                {new int[]{1,1,6,1,1,9,1,1,1}, null, true},
                {new int[]{1,1,6,4,1,9,1,1,1}, new int[]{1,9,1,1,1}, false},
                {new int[]{1,1,6,4,1,9,1,1,4}, new int[0], false},
        });
    }


    @Before
    public void init(){
        returnNewArray = new ReturnNewArray();
    }

    @Test
    public void returnArrayAfterFourTest(){
        if (runtimeException) exception.expect(RuntimeException.class);
        Assert.assertArrayEquals(resultArray, returnNewArray.returnArrayAfterFour(sourceArray));
    }

    @After
    public void clearTest(){
        returnNewArray = null;
    }

}
