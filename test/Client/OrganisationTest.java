package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrganisationTest {

    Organisation org;
    User user1;
    List<String> assets;
    List<Integer> assetAmount;

    @BeforeEach
    @Test
    public void setUpOrganisation() {
        assets = new ArrayList<>();
        assetAmount = new ArrayList<>();
        assets.add("Test");
        assetAmount.add(1);
        user1 = new User("Tom", "ab23", "non-admin", "Microsoft");
        org = new Organisation("Microsoft", 200, assets, assetAmount);
    }

    @Test
    public void organisationCheck(){
        assertEquals(200, org.getCredits(),
                "Adding organisation failed");
    }

    @Test
    public void setCreditsCheck(){
        org.setCredits(210);
        assertEquals(210, org.getCredits(),
                "Setting credits failed");
    }
}
