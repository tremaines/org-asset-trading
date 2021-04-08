package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class OrganisationTest {

    Organisation org;
    List<String> assets;
    List<Integer> assetAmount;
    List<String> emptyList;


    // Creating Organisation object
    @BeforeEach @Test
    public void setUpOrganisation() {
        emptyList = new ArrayList<>();

        // Parameters for Organisation object
        assets = new ArrayList<>();
        assetAmount = new ArrayList<>();

        org = new Organisation("Microsoft", 200, assets, assetAmount);
    }

    // Checks if the lists are empty, as an invalid asset will prevent the list from
    // being populated
    @Test
    public void invalidAssetCheck() {
        assets.add("Invalid");
        assetAmount.add(50);
        Organisation org2 = new Organisation("Google", 250, assets, assetAmount);
        assertEquals(emptyList, org2.getAssets(), "Emptying asset list failed");
    }

    // Checks if the assetAmount array will be cleared if there is an unrecognised asset
    @Test
    public void invalidAmountCheck() {
        assets.add("Invalid2");
        assetAmount.add(55);
        Organisation org2 = new Organisation("Google", 250, assets, assetAmount);
        assertEquals(emptyList, org2.getAssets(), "Emptying asset amount list failed");
    }

    // Checks the getCredits() method
    @Test
    public void getCreditsCheck() {
        assertEquals(200, org.getCredits(), "Adding organisation failed");
    }

    // Checks the setCredits() method
    @Test
    public void setCreditsCheck() {
        org.setCredits(210);
        assertEquals(210, org.getCredits(), "Setting credits failed");
    }

    // Checks the getAssets() method
    @Test
    public void getAssets() {
        List<String> newAssets = new ArrayList<>();
        newAssets.add("Hardware Resources");
        assets.add("Hardware Resources");
        assetAmount.add(10);
        assertEquals(newAssets, org.getAssets(), "Getting assets failed");
    }

    // Checks the getAssetsOwned() method
    @Test
    public void getAssetsAmount() {
        assets.add("Software Licenses");
        assetAmount.add(125);
        assets.add("Computational Resources");
        assetAmount.add(20);
        assets.add("Hardware Resources");
        assetAmount.add(45);

        // 125 is at index 0 of the assetAmount list and org.getAssetsAndAmounts().get() gets the
        // value of the key which is "Software Licenses", which is also 125
        assertEquals(assetAmount.toArray()[0], org.getAssetsAndAmounts().get("Software Licenses"),
                "Getting all the assets failed");
    }
}
