package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TradesTest {

    Organisation org;
    User user1;
    Trades trade1;
    int tradeID;

    List<String> assets;
    List<Integer> assetAmount;

    static final int tradeType = 0;
    static final int assetType = 1;
    static final int price = 2;
    static final int userName = 3;
    static final int orgName = 4;
    static final int time = 5;

    // Creating Trades Object
    @BeforeEach @Test
    public void setUpTrades() {
        // Parameters for Organisation object
        assets = new ArrayList<>();
        assetAmount = new ArrayList<>();

        org = new Organisation("Microsoft", 200, assets, assetAmount);
//        user1 = new User("Tom", "abc23", false, org.getOrganisationName(), org.getCredits(),
//                org.getAssets(), org.getAmounts());
        trade1 = new Trades();
    }
//
//    @Test
//    public void createListingCheck(){
//        trade1.createListing(user1, "Buy", "Software Licenses", 10, 5);
//        // The TradeID of the most recent listing will always be equal to the size of the map
//        tradeID = trade1.getListing().size();
//
//        assertEquals(1, trade1.getListing().size(),
//                "Creating a new listing failed");
//        assertNotNull(trade1.getListing().get(tradeID));
//    }
//
//    @Test
//    public void getListingCheck(){
//        trade1.createListing(user1, "Buy", "Software Licenses", 10, 5);
//        trade1.createListing(user1, "Sell", "Hardware Resources", 30, 15);
//        trade1.createListing(user1, "Buy", "Computational Resources", 40, 10);
//        // The TradeID of the most recent listing will always be equal to the size of the map
//        tradeID = trade1.getListing().size();
//
//        assertEquals("Buy", trade1.getListing().get(tradeID).get(tradeType),
//                "Creating a new listing failed");
//    }
//
//    @Test
//    public void matchListingCheck(){
//        trade1.createListing(user1, "Buy", "Software Licenses", 10, 5);
//        trade1.createListing(user1, "Sell", "Hardware Resources", 30, 15);
//        trade1.createListing(user1, "Buy", "Computational Resources", 40, 10);
//        trade1.createListing(user1, "Sell", "Software Licenses", 10, 5);
//        // The TradeID of the most recent listing will always be equal to the size of the map
//        tradeID = trade1.getListing().size();
//
//        assertEquals("Buy", trade1.matchListing());
//    }
//
}
