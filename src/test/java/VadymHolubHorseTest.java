import VadymHolubHorse.VadymHolubHorse;
import helpers.TestGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class VadymHolubHorseTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return TestGenerator.getTestBoards();
    }

    private int[][] fInput;

    private int fExpected;

    public VadymHolubHorseTest(int[][] input, int expected) {
        fInput = input;
        fExpected = expected;
    }


    @Test(timeout=5000)
    public void test() {
        assertEquals(fExpected, VadymHolubHorse.compute(fInput).length);
    }

}