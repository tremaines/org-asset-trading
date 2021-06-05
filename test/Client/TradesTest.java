package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void setIdPositiveCheck() throws TradesException {
        // Check boundary
        trade1.setId(0);
        assertEquals(0, trade1.getId());

        // Check good value (< 0)
        trade1.setId(5);
        assertEquals(5, trade1.getId());
    }

    // Checks that an exception is thrown if the setId() method is set to a negative number
    @Test
    public void setIdNegativeCheck() throws TradesException {
        // Check boundary
        trade1.setId(0);
        assertEquals(0, trade1.getId());

        // Check negative
        assertThrows(TradesException.class, () -> {
            trade1.setId(-5);
        });

        // Check that tradeID is unchanged
        assertEquals(0, trade1.getId());
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
    public void setAssetIdCheck() throws TradesException {
        trade1.setAssetId(5);
        assertEquals(5, trade1.getAssetId());
    }

    // Checks if an exception is thrown when setAssetId() sets an assetID to a negative number
    @Test
    public void setAssetIdNegativeCheck() throws TradesException {
        assertThrows(TradesException.class, () -> {
            trade1.setAssetId(-5);
        });
    }

    // Checks the getQuantity() method for a trade
    @Test
    public void getQuantityCheck(){
        assertEquals(10, trade1.getQuantity());
    }

    // Checks the setQuantity() method for a trade
    @Test
    public void setQuantityCheck() throws TradesException {
        trade1.setQuantity(20);
        assertEquals(20, trade1.getQuantity());
    }

    // Checks if an exception is thrown when setQuantity() sets the quantity to a negative number
    @Test
    public void setQuantityNegativeCheck() throws TradesException {
        // Boundary case
        trade1.setQuantity(0);
        assertEquals(0, trade1.getQuantity());

        // Check negative
        assertThrows(TradesException.class, () -> {
            trade1.setQuantity(-1);
        });
    }

    // Checks setQuantity() when set to a positive number
    @Test
    public void setQuantityPositiveCheck() throws TradesException {
        // Boundary case
        trade1.setQuantity(0);
        assertEquals(0, trade1.getQuantity());

        // Check positive
        trade1.setQuantity(12);
        assertEquals(12, trade1.getQuantity());
    }

    // Checks the getPrice() method for a trade
    @Test
    public void getPriceCheck(){
        assertEquals(1, trade1.getPrice());
    }

    // Checks the setPrice() method for a trade
    @Test
    public void setPriceCheck() throws TradesException {
        trade1.setPrice(5);
        assertEquals(5, trade1.getPrice());
    }

    // Checks if an exception is thrown when setPrice() sets the quantity to a negative number
    @Test
    public void setPriceNegativeCheck() throws TradesException {
        // Boundary case
        trade1.setPrice(0);
        assertEquals(0, trade1.getPrice());

        // Check negative
        assertThrows(TradesException.class, () -> {
            trade1.setPrice(-1);
        });
    }

    // Checks setPrice() when the the quantity is set to a positive number
    @Test
    public void setPricePositiveCheck() throws TradesException {
        // Boundary case
        trade1.setPrice(0);
        assertEquals(0, trade1.getPrice());

        // Check positive
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
}
