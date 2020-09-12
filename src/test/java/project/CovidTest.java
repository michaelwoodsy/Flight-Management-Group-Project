package project;
import org.junit.Test;
import project.model.Covid;

import static org.junit.Assert.assertEquals;

public class CovidTest {
    @Test
    public void riskTest(){
        Covid country1 = new Covid("Bolivia","15/08/2020",826.408,332.733,11673029);
        Covid country2 = new Covid("Bolivia","15/08/2020",8263.408,332.733,100000);
        Covid country3 = new Covid("Bolivia","15/08/2020",10123.412,332.733,100000);
        Covid country4 = new Covid("Bolivia","15/08/2020",25232.412,332.733,100000);
        Covid country5 = new Covid("Bolivia","15/08/2020",1000000,332.733,100000);

        assertEquals("Extremely Low", country1.getRiskString());
        assertEquals("Low", country2.getRiskString());
        assertEquals("Medium", country3.getRiskString());
        assertEquals("High Risk", country4.getRiskString());
        assertEquals("Extreme risk", country5.getRiskString());

    }

    @Test
    public void getRiskDoubleTest() {
        Covid country1 = new Covid("Bolivia","15/08/2020",8263.408,332.733,11673029);
        Covid country2 = new Covid("Bolivia","15/08/2020",900000.12,332.733,11673029);
        Covid country3 = new Covid("Bolivia","15/08/2020",10.0,332.733,11673029);

        assertEquals(00.83, country1.getRiskDouble(), 0);
        assertEquals(90, country2.getRiskDouble(), 0);
        //Rounded to 2 d.p., so expected risk will be 0
        assertEquals(00.00, country3.getRiskDouble(), 0);
    }
}
