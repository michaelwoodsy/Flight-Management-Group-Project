package project;
import org.junit.Test;
import project.model.Covid;

import static org.junit.Assert.assertEquals;

public class CovidTest {
    @Test
    public void riskTest(){
        Covid country1 = new Covid("BOL","South America","Bolivia","15/08/2020",96459,1388,3884,57,8263.408,332.733,11673029);
        Covid country2 = new Covid("BOL","South America","Bolivia","15/08/2020",2000,1388,3884,57,8263.408,332.733,100000);
        Covid country3 = new Covid("BOL","South America","Bolivia","15/08/2020",3500,1388,3884,57,8263.408,332.733,100000);
        Covid country4 = new Covid("BOL","South America","Bolivia","15/08/2020",4500,1388,3884,57,8263.408,332.733,100000);
        Covid country5 = new Covid("BOL","South America","Bolivia","15/08/2020",6000,1388,3884,57,8263.408,332.733,100000);

        assertEquals("Extremely Low", country1.get_risk());
        assertEquals("Low", country2.get_risk());
        assertEquals("Medium", country3.get_risk());
        assertEquals("High Risk", country4.get_risk());
        assertEquals("Extreme risk", country5.get_risk());

    }

    @Test
    public void getRiskIntTest() {
        Covid country1 = new Covid("BOL","South America","Bolivia","15/08/2020",96459,1388,3884,57,8263.408,332.733,11673029);
        Covid country2 = new Covid("BOL","South America","Bolivia","15/08/2020",96459,1388,3884,57,900000.12,332.733,11673029);
        Covid country3 = new Covid("BOL","South America","Bolivia","15/08/2020",96459,1388,3884,57,10.0,332.733,11673029);

        assertEquals(00.83, country1.getRiskDouble(), 0);
        assertEquals(90, country2.getRiskDouble(), 0);
        //Rounded to 2 d.p., so expected risk will be 0
        assertEquals(00.00, country3.getRiskDouble(), 0);
    }
}
