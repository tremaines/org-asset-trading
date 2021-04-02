package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssetTest {

    User user1;
    Assets assets = new Assets();
    List<String> assets2;

    @BeforeEach
    @Test
    public void setUpAssets() {
        user1 = new User("Tom", "ab23", "admin", "Microsoft");
        assets2 = new ArrayList<>();
    }

    @Test
    public void addAssetCheck(){
        assets.createAsset(user1, "Test");
        assets2.add("Computational Resources");
        assets2.add("Hardware Resources");
        assets2.add("Software Licenses");
        assets2.add("Test");
        assertEquals(assets2, assets.getAllAssets());
    }

}
