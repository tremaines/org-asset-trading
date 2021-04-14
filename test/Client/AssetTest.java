package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AssetTest {

    User user;
    Assets assets;
    List<String> assets2;

    Organisation org;
    List<Integer> assetAmount;

    @BeforeEach
    @Test
    public void setUpAssets() {
        assets = new Assets();

        // Parameters for Organisation object
        assets2 = new ArrayList<>();
        assetAmount = new ArrayList<>();

        org = new Organisation();
        org.createOrganisation("Google", 200, assets2, assetAmount);

        user = new User();
        user.createUser("James", "defg1234", false, "Google");
    }

    @Test
    public void addAssetCheck(){
        assets.createAsset(user, "Test");
        assets2.add("Computational Resources");
        assets2.add("Hardware Resources");
        assets2.add("Software Licenses");
        assets2.add("Test");
        assertNotEquals(assets2, assets.getAllAssets());
    }

}
