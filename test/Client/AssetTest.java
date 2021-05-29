package Client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssetTest {

    Assets asset1;

    /**
     * Checks the getAssetID() method for an asset
     *
     * @throws AssetsException
     */
    @Test
    public void getAssetIDCheck() throws AssetsException {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        assertEquals(1, asset1.getAssetID());
    }

    /**
     * Checks the getAssetName() method for an asset
     *
     * @throws AssetsException
     */
    @Test
    public void getAssetNameCheck() throws AssetsException {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        assertEquals("CPU Hours", asset1.getAssetName());
    }

    /**
     * Checks the getQuantity() method for an asset
     *
     * @throws AssetsException
     */
    @Test
    public void getAssetQuantityCheck() throws AssetsException {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        assertEquals(300, asset1.getQuantity());
    }


    /**
     * Checks the getUnitID() method for an asset
     *
     * @throws AssetsException
     */
    @Test
    public void getUnitIDAssetCheck() throws AssetsException {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        assertEquals(1, asset1.getUnitID());
    }

    /**
     * Checks the setAssetID() method for an asset
     *
     * @throws AssetsException
     */
    @Test
    public void setAssetIDCheck() throws AssetsException {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        asset1.setAssetID(2);
        assertEquals(2, asset1.getAssetID());
    }

    /**
     * Checks the setAssetName() method for an asset
     *
     * @throws AssetsException
     */
    @Test
    public void setAssetNameCheck() throws AssetsException {
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        asset1.setAssetName("CPU Time");
        assertEquals("CPU Time", asset1.getAssetName());
    }
}
