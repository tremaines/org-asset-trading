package Server;

import Client.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TradeLogicTest {

    UserDBMock userDB;
    UnitDBMock unitDB;
    PurchasesDBMock purchasesDB;
    TradeDBMock tradeDB;
    HistoryDBMock historyDB;

    User userA;
    User userB;
    User userC;
    User userD;
    Units unitA;
    Units unitB;
    Units unitC;
    Assets assetA1;
    Assets assetA2;
    Assets assetB2;
    Assets assetB3;
    Trades trade1;
    Trades trade2;

    ArrayList<User> users;
    ArrayList<Units> units;
    ArrayList<Assets> owned;
    ArrayList<Trades> trades;
    TradeLogic tradeLogic;

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
    public void addUnmatchedBuy() {
        Trades trade = new Trades(Trades.TradeType.buy, "userB", 3, 30, 100);
        tradeLogic.setTrade(trade);
        assertNotNull(tradeDB.getTrade(3));
    }

    /**
     * Add a SELL that does not match with any other trades, unit has enough asset
     */
    @Test
    public void addUnmatchedSell() {
        Trades trade = new Trades(Trades.TradeType.sell, "userB", 2, 10, 55);
        tradeLogic.setTrade(trade);
        assertNotNull(tradeDB.getTrade(3));
    }

    /**
     * Add a BUY that can be filled by a pre-existing SELL and confirm it is removed
     */
    @Test
    public void newBuyRemovedAfterMatch() {
        Trades trade = new Trades(Trades.TradeType.buy, "userB", 1, 50, 12);
        tradeLogic.setTrade(trade);
        assertNull(tradeDB.getTrade(3));
    }

    /**
     * Add a SELL that can be filled by a pre-existing BUY and confirm it is removed
     */
    @Test
    public void newSellRemovedAfterMatch() {
        Trades trade = new Trades(Trades.TradeType.sell, "userB", 2, 10, 20);
        tradeLogic.setTrade(trade);
        assertNull(tradeDB.getTrade(3));
    }

    /**
     * In the presence of multiple matching sells, a BUY should match the sell with the LOWEST price
     */
    @Test
    public void buyMatchesCheapestSell() {
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
    public void partialMatches() {
        Trades trade = new Trades(Trades.TradeType.sell, "userB", 2, 3, 15);
        tradeLogic.setTrade(trade);
        assertEquals(7, trade2.getQuantity());
    }
}
