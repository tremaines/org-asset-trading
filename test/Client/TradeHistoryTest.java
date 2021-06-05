package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TradeHistoryTest {
    Units unit1;
    Units unit2;
    User user1;
    User user2;
    Assets asset1;
    Trades trade1;
    Trades trade2;
    TradeHistory tradeHistory1;
    TradeHistory tradeHistory2;

    // Creating TradeHistory object
    @BeforeEach
    @Test
    public void setUpTradeHistory() throws UserException {
        unit1 = new Units(1, "Human Resources", 1000);
        unit2 = new Units(2, "IT", 250);
        user1 = new User("Tom", "Smith", "tom@gmail.com", "tom123", "password", false, 1);
        user2 = new User("Joe", "Dobbs", "joe@gmail.com", "joe123", "password", false, 2);
        asset1 = new Assets(1, "CPU Hours", 300, 1);
        trade1 = new Trades(1, Trades.TradeType.buy, "tom123", 1, 10, 1);
        trade2 = new Trades(2, Trades.TradeType.sell, "joe123", 1, 10, 1);
        tradeHistory1 = new TradeHistory(Trades.TradeType.buy, 1, 1, 10, user1.getUsername(),
                user2.getUsername());
        tradeHistory2 = new TradeHistory(Trades.TradeType.sell, 1, 1, 10, user1.getUsername(),
                user2.getUsername());
        java.util.Date javaUtilDate = new java.util.Date();
        java.sql.Date javaSQLDate = new java.sql.Date(javaUtilDate.getTime());
    }

    // Checks the getID() method
    @Test
    public void getIdCheck(){
        assertEquals(0, tradeHistory1.getId());
    }

    // Checks the setId() method
    @Test
    public void setIdCheck() throws TradesException {
        tradeHistory1.setId(5);
        assertEquals(5, tradeHistory1.getId());
    }

    // Checks the setId() method for a negative integer
    @Test
    public void setIdNegativeCheck() throws TradesException {
        // Boundary case
        tradeHistory1.setId(0);
        assertEquals(0, tradeHistory1.getId());

        // Check negative
        assertThrows(TradesException.class, () -> {
            tradeHistory1.setId(-3);
        });
    }

    // Checks the setId() method for a positive integer
    @Test
    public void setIdPositiveCheck() throws TradesException {
        // Boundary case
        tradeHistory1.setId(0);
        assertEquals(0, tradeHistory1.getId());

        // Check positive
        tradeHistory1.setId(15);
        assertEquals(15, tradeHistory1.getId());
    }

    // Checks the getStatus() method
    @Test
    public void getStatusCheck(){
        assertEquals("buy", tradeHistory1.getStatus().toString());
    }

    // Checks the setStatus() method
    @Test
    public void setStatusCheck(){
        tradeHistory1.setStatus(Trades.TradeType.cancelled);
        assertEquals("cancelled", tradeHistory1.getStatus().toString());
    }

    // Checks the getAsset() method
    @Test
    public void getAssetCheck(){
        assertEquals(asset1.getAssetID(), tradeHistory1.getAsset());
    }

    // Checks the setAsset() method
    @Test
    public void setAssetCheck() throws TradesException {
        tradeHistory1.setAsset(8);
        assertEquals(8, tradeHistory1.getAsset());
    }

    // Checks the setAsset() method for a negative integer
    @Test
    public void setAssetNegativeCheck() throws TradesException {
        // Boundary case
        tradeHistory1.setAsset(0);
        assertEquals(0, tradeHistory1.getAsset());

        // Check negative
        assertThrows(TradesException.class, () -> {
            tradeHistory1.setAsset(-3);
        });
    }

    // Checks the setAsset() method for a positive integer
    @Test
    public void setAssetPositiveCheck() throws TradesException {
        // Boundary case
        tradeHistory1.setAsset(0);
        assertEquals(0, tradeHistory1.getAsset());

        // Check positive
        tradeHistory1.setAsset(5);
        assertEquals(5, tradeHistory1.getAsset());
    }

    // Checks the getQty() method
    @Test
    public void getQtyCheck(){
        assertEquals(1, tradeHistory1.getQty());
    }

    // Checks the stQty() method
    @Test
    public void setQtyCheck() throws TradesException {
        tradeHistory1.setQty(15);
        assertEquals(15, tradeHistory1.getQty());
    }

    // Checks the stQty() method for a negative integer
    @Test
    public void setQtyNegativeCheck() throws TradesException {
        // Boundary case
        tradeHistory1.setQty(0);
        assertEquals(0, tradeHistory1.getQty());

        // Check negative
        assertThrows(TradesException.class, () -> {
            tradeHistory1.setQty(-5);
        });
    }

    // Checks the stQty() method for a positive integer
    @Test
    public void setQtyPositiveCheck() throws TradesException {
        // Boundary case
        tradeHistory1.setQty(0);
        assertEquals(0, tradeHistory1.getQty());

        // Check positive
        tradeHistory1.setQty(5);
        assertEquals(5, tradeHistory1.getQty());
    }

    // Checks the getCredits() method
    @Test
    public void getCreditsCheck(){
        assertEquals(10, tradeHistory1.getCredits());
    }

    // Checks the setCredits() method
    @Test
    public void setCreditsCheck() throws TradesException {
        tradeHistory1.setCredits(12);
        assertEquals(12, tradeHistory1.getCredits());
    }

    // Checks the setCredits() method for a negative integer
    @Test
    public void setCreditsNegativeCheck() throws TradesException {
        // Boundary case
        tradeHistory1.setCredits(0);
        assertEquals(0, tradeHistory1.getCredits());

        // Check negative
        assertThrows(TradesException.class, () -> {
            tradeHistory1.setCredits(-2);
        });
    }

    // Checks the setCredits() method for a positive integer
    @Test
    public void setCreditsPositiveCheck() throws TradesException {
        // Boundary case
        tradeHistory1.setCredits(0);
        assertEquals(0, tradeHistory1.getCredits());

        // Check positive
        tradeHistory1.setCredits(10);
        assertEquals(10, tradeHistory1.getCredits());
    }

    // Checks the getBuyer() method
    @Test
    public void getBuyerCheck(){
        assertEquals(user1.getUsername(), tradeHistory1.getBuyer());
    }

    // Checks the setBuyer() method
    @Test
    public void setBuyerCheck(){
        tradeHistory1.setBuyer("abc");
        assertEquals("abc", tradeHistory1.getBuyer());
    }

    // Checks the getSeller() method
    @Test
    public void getSellerCheck(){
        assertEquals(user2.getUsername(), tradeHistory2.getSeller());
    }

    // Checks the setSeller() method
    @Test
    public void setSellerCheck(){
        tradeHistory2.setSeller("def");
        assertEquals("def", tradeHistory2.getSeller());
    }
}
