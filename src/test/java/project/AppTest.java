package project;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     * Testing JUNIT test
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void goodAtMaths() {
        assertEquals(10, 5+5);
        assertEquals(25, 5*5);
    }

    @Test
    public void shouldAnswerWithTrueTwo()
    {
        assertTrue( true );
    }

    @Test
    public void LouisTest()
    {
        String myName = "Louis";
        assertEquals( myName, "Louis" );
    }

    /* @Test
    public void failingTest()
    {
        assertTrue( false );
    } */
}
