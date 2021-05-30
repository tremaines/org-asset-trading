package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitTest {

    Units unit1;

    // Creating Organisation object
    @BeforeEach
    @Test
    public void setUpOrganisation() {
        unit1 = new Units(1, "CPU Cluster Division", 1000);
    }


    // Checks the getUnitName() method
    @Test
    public void getUnitNameCheck() {
        assertEquals("CPU Cluster Division", unit1.getUnitName());
    }

    // Checks the getUnitID() method
    @Test
    public void getUnitIDCheck() {
        assertEquals(1, unit1.getUnitID());
    }

    // Checks the getCredits() method
    @Test
    public void getCreditsCheck() {
        assertEquals(1000, unit1.getCredits());
    }

    // Checks the setCredits() method
    @Test
    public void setCreditsCheck() throws UnitsException {
        unit1.setCredits(1500);
        assertEquals(1500, unit1.getCredits());
    }

    // Checks the setCredits() method after multiple changes
    @Test
    public void setCreditsMultipleCheck() throws UnitsException {
        unit1.setCredits(1500);
        unit1.setCredits(1600);
        unit1.setCredits(1400);
        unit1.setCredits(1350);
        assertEquals(1350, unit1.getCredits());
    }

    // Checks the setCredits() method with negative value
    @Test
    public void setCreditsNegativeCheck() throws UnitsException {
        assertThrows(UnitsException.class, () -> {
            unit1.setCredits(-100);
        });
    }

    // Checks the setUnitName() method
    @Test
    public void setUnitNameCheck() {
        unit1.setUnitName("IT");
        assertEquals("IT", unit1.getUnitName());
    }

    // Checks the setUnitID() method
    @Test
    public void setUnitID() {
        unit1.setUnitID(2);
        assertEquals(2, unit1.getUnitID());
    }

}

