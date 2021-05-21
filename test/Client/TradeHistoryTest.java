package Client;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TradeHistoryTest {

    Organisation org;
    User user;
    Trades trade;
    Trades trade1;
    TradeHistory history;
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
    // Sold amount for a sell listing
    private static final int soldAmount = 4;
    // Username
    private static final int userName = 5;
    // Name of the organisation
    private static final int orgName = 6;
    // Trade fulfilled status (Yes/No/Partial/Cancelled)
    private static final int tradeFulfilled = 7;
    // Time of the listing
    private static final int time = 8;

    // Creating TradeHistory object
    @BeforeEach @Test
    public void setUpTradeHistory() throws UserException {
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
        user = new User(org);
        user.createUser("Tom", "abc123", false, "Microsoft");
        user.createUser("Fred", "aaa123", false, "Google");

        trade = new Trades(org, user);

        // Separate Trade class to test other methods
        trade1 = new Trades(org, user);

        history = new TradeHistory(trade1);
    }

    @Test
    public void checkSummaryHistory() throws TradesException {
        trade1.createListing("Tom","Sell", "Hardware Resources", 10, 5);
        trade1.createListing("Fred","Buy", "Hardware Resources", 5, 5);

        int size = history.getSummaryTradeHistory().size();

        assertEquals(1, size);
    }

    @Test
    public void checkExtendedHistory() throws TradesException {
        trade1.createListing("Tom","Sell", "Hardware Resources", 10, 5);
        trade1.createListing("Fred","Buy", "Hardware Resources", 5, 5);

        // Adding both buy and sell listings at the SAME index
        int size = history.getExtendedTradeHistory().size();
        assertEquals(2, size);
    }
}
