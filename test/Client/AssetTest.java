package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssetTest {

    User user;
    User adminUser;
    Assets assets;
    List<String> assets2;

    Organisation org;
    List<Integer> assetAmount;

    @BeforeEach
    @Test
    public void setUpAssets() throws UserException {
        assets = new Assets();

        // Parameters for Organisation object
        assets2 = new ArrayList<>();
        assetAmount = new ArrayList<>();

        org = new Organisation();
        org.createOrganisation("Google", 200, assets2, assetAmount);

        user = new User(org);
        user.createUser("James", "defg1234", false, "Google");
        adminUser = new User("Alice", "password1", true, "AI");
    }

    /**
     * User with admin rights should be able to add an asset
     *
     * @throws AssetsException
     * @throws UserException
     */
    @Test
    public void adminAddsAsset() throws AssetsException, UserException {
        assets.createAsset(adminUser, "GPU Hours");
        assertTrue(assets.checkAsset("GPU Hours"));
    }

    /**
     * UserException should be thrown when a non-admin user tries to add an asset
     *
     * @throws AssetsException
     * @throws UserException
     */
    @Test
    public void nonAdminAddsAsset() throws AssetsException, UserException {
        assertThrows(UserException.class, () -> {
            assets.createAsset(user, "RAID Setup");
        });
    }

    /**
     * Confirm the asset is definitely not added when a UserException is thrown for a non-admin user
     *
     * @throws AssetsException
     * @throws UserException
     */
    @Test
    public void assetNotAdded() throws AssetsException, UserException {
        try {
            assets.createAsset(user, "RAID Setup");
        } catch (UserException e) {
        }
        assertTrue(!assets.checkAsset("RAID Setup"));
    }

    /**
     * AssetException should be thrown when an asset already exists in the list
     * @throws AssetsException
     * @throws UserException
     */
    @Test
    public void noDuplicates() throws AssetsException, UserException {
        assertThrows(AssetsException.class, () -> {
            assets.createAsset(adminUser, "GPU Hours");
            assets.createAsset(adminUser, "GPU Hours");
        });
    }

//    @Test
//    public void addAssetCheck() throws AssetsException, UserException {
//        assets.createAsset(user, "Test");
//        assets2.add("Computational Resources");
//        assets2.add("Hardware Resources");
//        assets2.add("Software Licenses");
//        assets2.add("Test");
//        assertNotEquals(assets2, assets.getAllAssets());
//    }



}
