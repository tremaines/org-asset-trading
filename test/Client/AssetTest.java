package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssetTest {

    Assets asset1;

    // Creating Asset object
    @BeforeEach
    @Test
    public void setUpUnit() {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
    }

    // Checks the getAssetID() method for an asset
    @Test
    public void getAssetIDCheck(){
        assertEquals(1, asset1.getAssetID());
    }

    // Checks the getAssetName() method for an asset
    @Test
    public void getAssetNameCheck() {
        assertEquals("CPU Hours", asset1.getAssetName());
    }

    // Checks the getQuantity() method for an asset
    @Test
    public void getAssetQuantityCheck() {
        assertEquals(300, asset1.getQuantity());
    }

    // Checks the setQuantity() method for an asset
    @Test
    public void setAssetQuantityCheck() throws AssetsException {
        asset1.setQuantity(111);
        assertEquals(111, asset1.getQuantity());
    }

    // Checks the setQuantity() method for a negative integer
    @Test
    public void setAssetQuantityNegativeCheck() throws AssetsException {
        // Boundary case
        asset1.setQuantity(0);
        assertEquals(0, asset1.getQuantity());

        // Check negative
        assertThrows(AssetsException.class, () -> {
            asset1.setQuantity(-5);
        });
    }

    // Checks the setQuantity() method for a positive integer
    @Test
    public void setAssetQuantityPositiveCheck() throws AssetsException {
        // Boundary case
        asset1.setQuantity(0);
        assertEquals(0, asset1.getQuantity());

        // Check positive
        asset1.setQuantity(55);
        assertEquals(55, asset1.getQuantity());
    }


    // Checks the getUnitID() method for an asset
    @Test
    public void getUnitIDAssetCheck() {
        assertEquals(1, asset1.getUnitID());
    }

    // Checks the setUnitID() method for an asset
    @Test
    public void setUnitIDAssetCheck() throws AssetsException {
        asset1.setUnitID(23);
        assertEquals(23, asset1.getUnitID());
    }

    // Checks the setUnitID() when a negative integer is set
    @Test
    public void setUnitIDNegativeAssetCheck() throws AssetsException {
        // Boundary case
        asset1.setUnitID(0);
        assertEquals(0, asset1.getUnitID());

        // Check negative
        assertThrows(AssetsException.class, () -> {
            asset1.setUnitID(-27);
        });
    }

    // Checks the setUnitID() when a positive integer is set
    @Test
    public void setUnitIDPositiveAssetCheck() throws AssetsException {
        // Boundary case
        asset1.setUnitID(0);
        assertEquals(0, asset1.getUnitID());

        // Check positive
        asset1.setUnitID(123);
        assertEquals(123, asset1.getUnitID());
    }

    // Checks the setAssetID() method for an asset
    @Test
    public void setAssetIDCheck() throws AssetsException {
        asset1.setAssetID(2);
        assertEquals(2, asset1.getAssetID());
    }

    // Checks the setAssetID() method for a positive integer
    @Test
    public void setAssetIDPositiveCheck() throws AssetsException {
        // Boundary case
        asset1.setAssetID(0);
        assertEquals(0, asset1.getAssetID());

        // Check positive
        asset1.setAssetID(23);
        assertEquals(23, asset1.getAssetID());
    }

    // Checks the setAssetID() method for a negative integer
    @Test
    public void setAssetIDNegativeCheck() throws AssetsException {
        // Boundary case
        asset1.setAssetID(0);
        assertEquals(0, asset1.getAssetID());

        // Check negative
        assertThrows(AssetsException.class, () -> {
            asset1.setAssetID(-5);
        });
    }

    // Checks the setAssetName() method for an asset
    @Test
    public void setAssetNameCheck() {
        asset1.setAssetName("CPU Time");
        assertEquals("CPU Time", asset1.getAssetName());
    }
}
