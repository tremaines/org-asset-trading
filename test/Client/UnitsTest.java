package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitsTest {

    Units unit1;

    // Creating Unit object
    @BeforeEach
    @Test
    public void setUpUnit() {
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
    public void setCreditsPositiveCheck() throws UnitsException {
        // Check boundary
        unit1.setCredits(0);
        assertEquals(0, unit1.getCredits());

        // Check good value (< 0)
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
        unit1.setCredits(1300);
        assertThrows(UnitsException.class, () -> {
            unit1.setCredits(-100);
        });

        // Check that credits are unchanged
        assertEquals(1300, unit1.getCredits());
    }

    // Checks the setUnitName() method
    @Test
    public void setUnitNameCheck() {
        unit1.setUnitName("IT");
        assertEquals("IT", unit1.getUnitName());
    }

    // Checks the setUnitID() method
    @Test
    public void setUnitIDPositiveCheck() throws UnitsException {
        // Check boundary
        unit1.setUnitID(0);
        assertEquals(0, unit1.getUnitID());

        // Check good value (< 0)
        unit1.setUnitID(8);
        assertEquals(8, unit1.getUnitID());

        // Good value check 2 (< 0)
        unit1.setUnitID(10);
        assertEquals(10, unit1.getUnitID());
    }

    // Checks the setUnitID() method with negative value
    @Test
    public void setUnitIDNegativeCheck() throws UnitsException {
        // Boundary case
        unit1.setUnitID(0);
        assertEquals(0, unit1.getUnitID());

        // Check set unitID Negative
        assertThrows(UnitsException.class, () -> {
            unit1.setUnitID(-5);
        });

        // Check that unitID is unchanged
        assertEquals(0, unit1.getUnitID());
    }
}

