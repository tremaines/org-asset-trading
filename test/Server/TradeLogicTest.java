package Server;

import Client.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the TradeLogic class using a mock database
 */
public class TradeLogicTest {

    private UserDBMock userDB;
    private UnitDBMock unitDB;
    private PurchasesDBMock purchasesDB;
    private TradeDBMock tradeDB;
    private HistoryDBMock historyDB;

    private User userA, userB, userC, userD;;
    private Units unitA, unitB, unitC;
    private Assets assetA1, assetA2, assetB2, assetB3;
    private Trades trade1, trade2;

    private ArrayList<User> users;
    private ArrayList<Units> units;
    private ArrayList<Assets> owned;
    private ArrayList<Trades> trades;
    private TradeLogic tradeLogic;

    /**
     * Reset mock DBs before each test to maintain test independence
     */
    @BeforeEach
    public void setUpMockDB() {
        // Instantiate some users
        userA = new User("userA", false, 1, "123");
        userB = new User("userB", false, 2, "123");
        userC = new User("userC", false, 2, "123");
        userD = new User("userD", false, 3, "123");
        users = new ArrayList<>() {
            {
                add(userA);
                add(userB);
                add(userC);
                add(userD);
            }
        };
        // Instantiate some units
        unitA = new Units(1, "Unit A", 5000);
        unitB = new Units(2, "Unit B", 7500);
        unitC = new Units(3, "Unit C", 3000);
        units = new ArrayList<>() {
            {
                add(unitA);
                add(unitB);
                add(unitC);
            }
        };
        // Instantiate some assets owned by the units
        assetA1 = new Assets(1, "Asset A", 400, 1);
        assetA2 = new Assets(1, "Asset A", 100, 2);
        assetB2 = new Assets(2, "Asset B", 20, 2);
        assetB3 = new Assets(3, "Asset C", 75, 3);
        owned = new ArrayList<>() {
            {
                add(assetA1);
                add(assetA2);
                add(assetB2);
                add(assetB3);
            }
        };
        //Instantiate some pre-existing trades
        trade1 = new Trades(1, Trades.TradeType.sell, "userA", 1, 50, 12);
        trade2 = new Trades(2, Trades.TradeType.buy, "userD", 2, 10, 20);
        trades = new ArrayList<>() {
            {
                add(trade1);
                add(trade2);
            }
        };

        userDB = new UserDBMock(users);
        unitDB = new UnitDBMock(units);
        purchasesDB = new PurchasesDBMock(owned);
        tradeDB = new TradeDBMock(trades);
        historyDB = new HistoryDBMock();
        tradeLogic = new TradeLogic(unitDB, userDB, tradeDB, purchasesDB, historyDB);
    }

    /**
     * Add a BUY that does not match with any other trades, unit has enough credits
     */
    @Test
    public void addUnmatchedBuy() throws TradesException {
        Trades trade = new Trades(Trades.TradeType.buy, "userB", 3, 30, 100);
        tradeLogic.setTrade(trade);
        assertNotNull(tradeDB.getTrade(3));
    }

    /**
     * Add a SELL that does not match with any other trades, unit has enough asset
     */
    @Test
    public void addUnmatchedSell() throws TradesException {
        Trades trade = new Trades(Trades.TradeType.sell, "userB", 2, 10, 55);
        tradeLogic.setTrade(trade);
        assertNotNull(tradeDB.getTrade(3));
    }

    /**
     * Add a BUY that can be filled by a pre-existing SELL and confirm it is removed
     */
    @Test
    public void newBuyRemovedAfterMatch() throws TradesException {
        Trades trade = new Trades(Trades.TradeType.buy, "userB", 1, 50, 12);
        tradeLogic.setTrade(trade);
        assertNull(tradeDB.getTrade(3));
    }

    /**
     * Add a SELL that can be filled by a pre-existing BUY and confirm it is removed
     */
    @Test
    public void newSellRemovedAfterMatch() throws TradesException {
        Trades trade = new Trades(Trades.TradeType.sell, "userB", 2, 10, 20);
        tradeLogic.setTrade(trade);
        assertNull(tradeDB.getTrade(3));
    }

    /**
     * In the presence of multiple matching sells, a BUY should match the sell with the LOWEST price
     */
    @Test
    public void buyMatchesCheapestSell() throws TradesException {
        Trades sellTrade = new Trades(Trades.TradeType.sell, "userC", 1, 30, 8);
        tradeLogic.setTrade(sellTrade);
        Trades buyTrade = new Trades(Trades.TradeType.buy, "userD", 1, 20, 15);
        tradeLogic.setTrade(buyTrade);
        TradeHistory record = historyDB.getRecord(1);
        assertEquals(8, record.getCredits());
    }

    /**
     * BUYS can partially match, with the remaining quantity of the BUY remaining up for trade
     */
    @Test
    public void partialMatchesBuy() throws TradesException {
        Trades trade = new Trades(Trades.TradeType.sell, "userB", 2, 3, 15);
        tradeLogic.setTrade(trade);
        assertEquals(7, trade2.getQuantity());
    }

    /**
     * SELLS can partially match, with the remaining quantity of the SELL remaining up for trade
     */
    @Test
    public void partialMatchesSell() throws TradesException {
        Trades trade = new Trades(Trades.TradeType.buy, "userC", 1, 30, 12);
        tradeLogic.setTrade(trade);
        assertEquals(20, trade1.getQuantity());
    }

    /**
     * Cancelling a SELL removes the trade from active trades
     */
    @Test
    public void cancelTradeSell() throws UnitsException, TradesException, AssetsException {
        tradeLogic.cancelTrade(trade1);
        assertNull(tradeDB.getTrade(1));
    }

    /**
     * Cancelling a BUY removes the trade from active trades
     */
    @Test
    public void cancelTradeBuy() throws UnitsException, TradesException, AssetsException {
        tradeLogic.cancelTrade(trade2);
        assertNull(tradeDB.getTrade(2));
    }

    /**
     * A perfectly matching BUY and SELL order removes both trades from active trades
     */
    @Test
    public void perfectMatchRemoval() throws TradesException {
        Trades trade = new Trades(Trades.TradeType.buy, "userC", 1, 50, 12);
        tradeLogic.setTrade(trade);
        assertTrue(tradeDB.getTrade(1) == null && tradeDB.getTrade(3) == null);
    }

    /**
     * Cancelling a trade adds the trade to trade history with a CANCELLED status
     */
    @Test
    public void tradeHistoryCancelled() throws UnitsException, TradesException, AssetsException {
        tradeLogic.cancelTrade(trade1);
        TradeHistory trade =  historyDB.getRecord(1);
        assertEquals(trade.getStatus(), Trades.TradeType.cancelled);
    }

    /**
     * Attempting to place a BUY with insufficient credits will throw an exception
     * internally and TradeLogic.setTrade() will return -1
     */
    @Test
    public void insufficientCredits() throws TradesException {
        Trades trade = new Trades(Trades.TradeType.buy, "userD", 1, 600, 10);
        int foo = tradeLogic.setTrade(trade);
        assertEquals(-1, foo);
    }

    /**
     * Attempting to place a SELL with insufficient assets will throw an exception
     * internally and TradeLogic.setTrade() will return -1
     */
    @Test
    public void insufficientAsset() throws TradesException {
        Trades trade = new Trades(Trades.TradeType.sell, "userB", 2, 155, 20);
        int foo = tradeLogic.setTrade(trade);
        assertEquals(-1, foo);
    }

    /**
     * A unit that has a BUY and SELL order for the same asset will not match with itself
     */
    @Test
    public void sameUnitBuySell() throws TradesException {
        Trades trade1 = new Trades(Trades.TradeType.buy, "userB", 2, 40, 30);
        tradeLogic.setTrade(trade1);
        Trades trade2 = new Trades(Trades.TradeType.sell, "userC", 2, 10, 25);
        tradeLogic.setTrade(trade2);
        assertEquals(10, tradeDB.getTrade(4).getQuantity());
    }
}
