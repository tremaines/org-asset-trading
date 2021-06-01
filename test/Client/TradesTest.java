package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TradesTest {
    Units unit1;
    User user1;
    Assets asset1;
    Trades trade1;

    // Creating Trade object
    @BeforeEach
    @Test
    public void setUpTrade() {
        unit1 = new Units(1, "Human Resources", 1000);
        user1 = new User("Tom", "Smith", "tom@gmail.com", "tom123", "password", true, 1);
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        trade1 = new Trades(1, Trades.TradeType.buy, "tom123", 1, 10, 1);
        java.util.Date javaUtilDate = new java.util.Date();
        java.sql.Date javaSQLDate = new java.sql.Date(javaUtilDate.getTime());
        trade1.setDate(javaSQLDate);
    }

    // Checks the getID() method for a trade
    @Test
    public void getIdCheck(){
        assertEquals(1, trade1.getId());
    }

    // Checks the setId() method for a trade
    @Test
    public void setIdCheck(){
        trade1.setId(2);
        assertEquals(2, trade1.getId());
    }

    // Checks the getType() method for a trade
    @Test
    public void getTypeCheck(){
        assertEquals(Trades.TradeType.buy, trade1.getType());
    }

    // Checks the setType() method for a trade
    @Test
    public void setTypeCheck(){
        trade1.setType(Trades.TradeType.sell);
        assertEquals(Trades.TradeType.sell, trade1.getType());
    }

    // Checks the getUserName() method for a trade
    @Test
    public void getUserNameCheck(){
        assertEquals("tom123", trade1.getUserName());
    }

    // Checks the setUserName() method for a trade
    @Test
    public void setUserNameCheck(){
        trade1.setUserName("bob543");
        assertEquals("bob543", trade1.getUserName());
    }

    // Checks the getAssetId() method for a trade
    @Test
    public void getAssetIdCheck(){
        assertEquals(1, trade1.getAssetId());
    }

    // Checks the setAssetId() method for a trade
    @Test
    public void setAssetIdCheck(){
        trade1.setAssetId(5);
        assertEquals(5, trade1.getAssetId());
    }

    // Checks the getQuantity() method for a trade
    @Test
    public void getQuantityCheck(){
        assertEquals(10, trade1.getQuantity());
    }

    // Checks the setQuantity() method for a trade
    @Test
    public void setQuantityCheck(){
        trade1.setQuantity(20);
        assertEquals(20, trade1.getQuantity());
    }

    // Checks the getPrice() method for a trade
    @Test
    public void getPriceCheck(){
        assertEquals(1, trade1.getPrice());
    }

    // Checks the setPrice() method for a trade
    @Test
    public void setPriceCheck(){
        trade1.setPrice(5);
        assertEquals(5, trade1.getPrice());
    }

    // Checks the getDate() returns a variable of type Date
    @Test
    public void getDateCheck(){
        java.util.Date javaUtilDate = new java.util.Date();
        java.sql.Date javaSQLDate = new java.sql.Date(javaUtilDate.getTime());
        assertEquals(javaSQLDate.getClass().getName(), trade1.getDate().getClass().getName());
    }

    // Checks the setDate() returns a variable of type Date
    @Test
    public void setDateCheck(){
        java.util.Date javaUtilDate = new java.util.Date();
        java.sql.Date javaSQLDate = new java.sql.Date(javaUtilDate.getTime());
        trade1.setDate(javaSQLDate);
        assertEquals(javaSQLDate, trade1.getDate());
    }

//    Organisation org;
//    User user;
//    Trades trade;
//    Trades trade1;
//    int tradeID;
//
//    List<String> assets;
//    List<Integer> amount;
//    List<String> assets2;
//    List<Integer> amount2;
//
//    // Trade type (Buy/Sell)
//    private static final int tradeType = 0;
//    // Type of asset
//    private static final int assetType = 1;
//    // Quantity of the asset
//    private static final int assetAmount = 2;
//    // Cost per asset unit
//    private static final int price = 3;
//    // Sold amount for a sell listing
//    private static final int soldAmount = 4;
//    // Username
//    private static final int userName = 5;
//    // Name of the organisation
//    private static final int orgName = 6;
//    // Trade fulfilled status (Yes/No/Partial/Cancelled)
//    private static final int tradeFulfilled = 7;
//    // Time of the listing
//    private static final int time = 8;
//
//    // Creating Trades Object
//    @BeforeEach @Test
//    public void setUpTrades() throws UserException {
//        // Parameters for Organisation object
//        assets = new ArrayList<>();
//        amount = new ArrayList<>();
//        assets2 = new ArrayList<>();
//        amount2 = new ArrayList<>();
//
//        assets.add("Hardware Resources");
//        amount.add(30);
//
//        org = new Organisation();
//        org.createOrganisation("Microsoft", 200, assets, amount);
//        org.createOrganisation("Google", 300, assets2, amount2);
//        user = new User(org);
//        user.createUser("Tom", "abc123", false, "Microsoft");
//        user.createUser("Fred", "aaa123", false, "Google");
//
//        trade = new Trades(org, user);
//
//        // Separate Trade class to test other methods
//        trade1 = new Trades(org, user);
//    }
//
//
//    // Checks that a listing can be created
//    @Test
//    public void createListingCheck1() throws TradesException {
//        trade.createListing("Tom","Buy", "Software Licenses", 10, 5);
//        // The TradeID of the most recent listing will always be equal to the size of the map
//        tradeID = trade.getListing().size();
//
//        assertEquals(1, trade.getListing().size());
//        assertNotNull(trade.getListing().get(tradeID));
//    }
//
//    // Checks that an exception will be thrown if user doesn't have enough credits to make the buy
//    // listing
//    @Test
//    public void createListingCheck2() throws TradesException {
//        assertThrows(TradesException.class, () -> {
//            trade.createListing("Tom","Buy", "Software Licenses", 50, 10);
//        });
//    }
//
//    // Checks that an exception will be thrown if user doesn't have enough assets to make the sell
//    // listing
//    @Test
//    public void createListingCheck3() throws TradesException {
//        assertThrows(TradesException.class, () -> {
//            trade.createListing("Tom","Sell", "Hardware Resources", 50, 10);
//        });
//    }
//
//    // Checking values of the trade listing
//    @Test
//    public void getListingCheck() throws TradesException {
//        trade.createListing("Tom","Buy", "Software Licenses", 10, 5);
//        // The TradeID of the most recent listing will always be equal to the size of the map
//        tradeID = trade.getListing().size();
//
//        assertEquals("Tom", trade.getListing().get(tradeID).get(userName));
//    }
//
//    // Checks that the credits will be removed corresponding to the total credit cost from the
//    // after creating a buy listing
//    @Test
//    public void getListingCheck2() throws TradesException {
//        trade.createListing("Tom","Buy", "Software Licenses", 10, 5);
//        // The TradeID of the most recent listing will always be equal to the size of the map
//        tradeID = trade.getListing().size();
//
//        assertEquals(150, org.getOrganisation("Microsoft").getCredits());
//    }
//
//    // Checks that the asset and asset amount will be removed from the organisation after
//    // creating a sell listing
//    @Test
//    public void getListingCheck3() throws TradesException {
//        trade.createListing("Tom","Sell", "Hardware Resources", 30, 5);
//        tradeID = trade.getListing().size();
//
//        List<String> emptyList = new ArrayList<>();
//
//        assertEquals(emptyList, org.getOrganisation("Microsoft").getAssets());
//    }
//
//    // matchListing() method is automatically called when a new listing is created!
//    // Basic matchListing() check that verifies credits returned are correct
//    @Test
//    public void matchListingCheck1() throws TradesException {
//        trade1.createListing("Tom","Sell", "Hardware Resources", 10, 5);
//        trade1.createListing("Fred","Buy", "Hardware Resources", 5, 5);
//
//        assertEquals(275,  org.getOrganisation("Google").getCredits());
//    }
//
//    // Basic matchListing() check2 that verifies no credits are returned
//    @Test
//    public void matchListingCheck2() throws TradesException {
//        trade1.createListing("Tom","Sell", "Hardware Resources", 10, 5);
//        trade1.createListing("Fred","Buy", "Hardware Resources", 10, 5);
//
//        assertEquals(250,  org.getOrganisation("Google").getCredits());
//    }
//
//    // Basic matchListing() check3 that verifies credits returned are correct
//    @Test
//    public void matchListingCheck3() throws TradesException {
//        assets2.add("Software Licenses");
//        amount2.add(20);
//        trade1.createListing("Fred","Sell", "Software Licenses", 10, 5);
//        trade1.createListing("Tom","Buy", "Software Licenses", 20, 10);
//
//        // The buy amount for Tom is for 200 credits, hence 50 credits are returned. As the trade
//        // listing is still active, the user will now possess 50 credits.
//        assertEquals(50,  org.getOrganisation("Microsoft").getCredits());
//    }
//
//    // More advanced matchListing() check that verifies that an organisation gets the expected
//    // amount of credits and assets when a trade is completed
//    @Test
//    public void matchListingCheck4() throws TradesException {
//        assets2.add("Software Licenses");
//        amount2.add(20);
//        trade1.createListing("Fred","Sell", "Software Licenses", 10, 5);
//        trade1.createListing("Tom","Buy", "Software Licenses", 20, 10);
//        trade1.createListing("Tom","Sell", "Software Licenses", 10, 5);
//        trade1.createListing("Fred","Buy", "Software Licenses", 20, 10);
//
//        Map<String, Integer> expectedResult = new HashMap<>();
//        expectedResult.put("Software Licenses", 20);
//
//        assertEquals(200,  org.getOrganisation("Google").getCredits());
//        assertEquals(expectedResult,  org.getOrganisation("Google").getAssetsAndAmounts());
//    }
//
//    // Checks that credits will be refunded on a partially completed trade
//    @Test
//    public void matchListingCheck5() throws TradesException {
//        assets2.add("Software Licenses");
//        amount2.add(20);
//        trade1.createListing("Fred","Sell", "Software Licenses", 10, 5);
//        trade1.createListing("Tom","Buy", "Software Licenses", 20, 10);
//        trade1.cancelListing(2);
//
//        // The buy amount for Tom is for 200 credits, hence 50 credits are returned. After the
//        // trade listing is cancelled, the remaining 100 credits are refunded back, therefore the
//        // organisation now has 150 credits.
//        assertEquals(150,  org.getOrganisation("Microsoft").getCredits());
//    }
//
//    // Checks that assets will be refunded on a partially completed trade
//    @Test
//    public void matchListingCheck6() throws TradesException {
//        assets2.add("Software Licenses");
//        amount2.add(20);
//        trade1.createListing("Fred","Sell", "Software Licenses", 20, 5);
//        trade1.createListing("Tom","Buy", "Software Licenses", 10, 10);
//        trade1.cancelListing(1);
//
//        Map<String, Integer> expectedResult = new HashMap<>();
//        expectedResult.put("Software Licenses", 10);
//
//        assertEquals(expectedResult,  org.getOrganisation("Google").getAssetsAndAmounts());
//    }
//
//    // Checks whether cancelListing() method returns assets on a sell listing
//    @Test
//    public void cancelListingCheck() throws TradesException {
//        assets2.add("Software Licenses");
//        amount2.add(20);
//        trade1.createListing("Fred","Sell", "Software Licenses", 20, 5);
//        trade1.cancelListing( 1);
//        assertEquals(20,  org.getOrganisation("Google").getAmounts().get(0));
//    }
//
//    // Checks whether cancelListing() method returns assets on a buy listing
//    @Test
//    public void cancelListingCheck2() throws TradesException {
//        trade1.createListing("Fred","Buy", "Software Licenses", 20, 5);
//        trade1.cancelListing(1);
//        assertEquals(300,  org.getOrganisation("Google").getCredits());
//    }
//
//    // Checks getAssetQuantity() method
//    @Test
//    public void checkGetAssetQuantity() throws TradesException {
//        assets2.add("Software Licenses");
//        amount2.add(20);
//        assets.add("Software Licenses");
//        amount.add(20);
//        trade1.createListing("Fred","Sell", "Software Licenses", 20, 5);
//        trade1.createListing("Tom","Sell", "Software Licenses", 20, 5);
//        int quantity = trade1.getAssetQuantity("Software Licenses");
//        assertEquals(40,  quantity);
//    }
//
//    // Checks getLowestPrice() method
//    @Test
//    public void checkGetLowestPrice() throws TradesException {
//        assets2.add("Software Licenses");
//        amount2.add(20);
//        assets.add("Software Licenses");
//        amount.add(20);
//        trade1.createListing("Fred","Sell", "Software Licenses", 20, 5);
//        trade1.createListing("Tom","Sell", "Software Licenses", 20, 4);
//        int quantity = trade1.getLowestPrice("Software Licenses");
//        assertEquals(4,  quantity);
//    }
}
