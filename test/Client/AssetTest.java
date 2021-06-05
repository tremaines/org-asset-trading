package Client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssetTest {

    Assets asset1;

    // Checks the getAssetID() method for an asset
    @Test
    public void getAssetIDCheck(){
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        assertEquals(1, asset1.getAssetID());
    }

    // Checks the getAssetName() method for an asset
    @Test
    public void getAssetNameCheck() {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        assertEquals("CPU Hours", asset1.getAssetName());
    }

    // Checks the getQuantity() method for an asset
    @Test
    public void getAssetQuantityCheck() {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        assertEquals(300, asset1.getQuantity());
    }


    // Checks the getUnitID() method for an asset
    @Test
    public void getUnitIDAssetCheck() {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        assertEquals(1, asset1.getUnitID());
    }

    // Checks the setAssetID() method for an asset
    @Test
    public void setAssetIDCheck() throws AssetsException {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        asset1.setAssetID(2);
        assertEquals(2, asset1.getAssetID());
    }

    // Checks the setAssetID() method for a positive integer
    @Test
    public void setAssetIDPositiveCheck() throws AssetsException {
        asset1 = new Assets(1, "CPU Hours", 300, 1);

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
        asset1 = new Assets(1, "CPU Hours", 300, 1);

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
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        asset1.setAssetName("CPU Time");
        assertEquals("CPU Time", asset1.getAssetName());
    }
}
