package project;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void whichIsBiggerTest() {
        assertEquals(10, whichIsBigger(10, 5));
        assertEquals(50, whichIsBigger(50, 50));
    }
}
