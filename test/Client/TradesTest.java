package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TradesTest {

    Organisation org;
    User user;
    Trades trade;
    Trades trade1;
    int tradeID;

    List<String> assets;
    List<Integer> amount;
    List<String> assets2;
    List<Integer> amount2;

    // Trade type (Buy/Sell)
    private static final int tradeType = 0;
    // Type of asset
    private static final int assetType = 1;
    // Quantity of the asset
    private static final int assetAmount = 2;
    // Cost per asset unit
    private static final int price = 3;
    // Original asset amount
    private static final int originalAmount = 4;
    // Username
    private static final int userName = 5;
    // Name of the organisation
    private static final int orgName = 6;
    // Trade fulfilled status (Yes/No/Partially/Cancelled)
    private static final int tradeFulfilled = 7;
    // Time of the listing
    private static final int time = 8;

    // Creating Trades Object
    @BeforeEach @Test
    public void setUpTrades() {
        // Parameters for Organisation object
        assets = new ArrayList<>();
        amount = new ArrayList<>();
        assets2 = new ArrayList<>();
        amount2 = new ArrayList<>();

        assets.add("Hardware Resources");
        amount.add(30);

        org = new Organisation();
        org.createOrganisation("Microsoft", 200, assets, amount);
        org.createOrganisation("Google", 300, assets2, amount2);
        user = new User();
        user.createUser("Tom", "abc123", false, "Microsoft");
        user.createUser("Fred", "aaa123", false, "Google");

        trade = new Trades(org, user);

        // Separate Trade class to test other methods
        trade1 = new Trades(org, user);
    }

    // Checks that a listing can be created
    @Test
    public void createListingCheck(){
        trade.createListing("Tom","Buy", "Software Licenses", 10, 5);
        // The TradeID of the most recent listing will always be equal to the size of the map
        tradeID = trade.getListing().size();

        assertEquals(1, trade.getListing().size());
        assertNotNull(trade.getListing().get(tradeID));
    }

    // Checking values of the trade listing
    @Test
    public void getListingCheck(){
        trade.createListing("Tom","Buy", "Software Licenses", 10, 5);
        // The TradeID of the most recent listing will always be equal to the size of the map
        tradeID = trade.getListing().size();

        assertEquals("Tom", trade.getListing().get(tradeID).get(userName));
    }

    // Checks that the credits will be removed corresponding to the total credit cost from the
    // after creating a buy listing
    @Test
    public void getListingCheck2(){
        trade.createListing("Tom","Buy", "Software Licenses", 10, 5);
        // The TradeID of the most recent listing will always be equal to the size of the map
        tradeID = trade.getListing().size();

        assertEquals(150, org.getOrganisation("Microsoft").getCredits());
    }

    // Checks that the asset and asset amount will be removed from the organisation after
    // creating a sell listing
    @Test
    public void getListingCheck3(){
        trade.createListing("Tom","Sell", "Hardware Resources", 30, 5);
        tradeID = trade.getListing().size();

        List<String> emptyList = new ArrayList<>();

        assertEquals(emptyList, org.getOrganisation("Microsoft").getAssets());
    }

    // matchListing() method is automatically called when a new listing is created!
    @Test
    public void matchListingCheck(){
        trade1.createListing("Tom","Sell", "Hardware Resources", 10, 5);
        trade1.createListing("Fred","Buy", "Hardware Resources", 5, 5);

        assertEquals(275,  org.getOrganisation("Google").getCredits());
    }

    @Test
    public void matchListingCheck2(){
        trade1.createListing("Tom","Sell", "Hardware Resources", 10, 5);
        trade1.createListing("Fred","Buy", "Hardware Resources", 10, 5);

        assertEquals(250,  org.getOrganisation("Google").getCredits());
    }

    @Test
    public void matchListingCheck3(){
        assets2.add("Software Licenses");
        amount2.add(20);
        trade1.createListing("Fred","Sell", "Software Licenses", 10, 5);
        trade1.createListing("Tom","Buy", "Software Licenses", 20, 10);


        assertEquals(50,  org.getOrganisation("Microsoft").getCredits());
    }

    // Checks whether cancelListing() method returns assets on a sell listing
    @Test
    public void cancelListingCheck(){
        assets2.add("Software Licenses");
        amount2.add(20);
        trade1.createListing("Fred","Sell", "Software Licenses", 20, 5);
        trade1.cancelListing( 1);
        assertEquals(20,  org.getOrganisation("Google").getAmounts().get(0));
    }

    // Checks whether cancelListing() method returns assets on a buy listing
    @Test
    public void cancelListingCheck2(){
        trade1.createListing("Fred","Buy", "Software Licenses", 20, 5);
        trade1.cancelListing(1);
        assertEquals(300,  org.getOrganisation("Google").getCredits());
    }
}
