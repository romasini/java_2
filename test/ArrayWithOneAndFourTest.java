
import lessonJava3_6.ArrayWithOneAndFour;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)//параметроризованные тесты
public class ArrayWithOneAndFourTest {
    private static ArrayWithOneAndFour arrayWithOneAndFour = null;
    private int[] sourceArray;
    private boolean result;

    public ArrayWithOneAndFourTest(int[] sourceArray, boolean result){//конструктор для параметроризованных тестов
        this.sourceArray = sourceArray;
        this.result = result;
    }

    @Parameters
    public static Collection abracadabra(){
        return Arrays.asList(new Object[][]{
                {new int[]{1,1,1,4,4,1,4,4}, true},
                {new int[]{1,1,1,1,1}, false},
                {new int[]{4,4,4}, false},
                {new int[]{1,1,6,4,1,9,1,1,4}, false},
        });
    }


    @Before
    public void init(){
        arrayWithOneAndFour = new ArrayWithOneAndFour();
    }

    @Test
    public void isHaveJustOneFourTest(){
        Assert.assertEquals(result, arrayWithOneAndFour.isHaveJustOneFour(sourceArray));
    }

    @After
    public void clearTest(){
        arrayWithOneAndFour = null;
    }

}
