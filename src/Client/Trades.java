package Client;

import java.io.Serializable;
import java.sql.Date;

/**
 * Trades are how the different types of assets are distributed and shared between different
 * organisations, with the use of electronic credits. Organisations are given budgets for a
 * certain number of credits and they are able to use these credits to purchase additional assets.
 * Similarly, assets may also be assigned to an organisation when they are created and these can
 * be sold for credits.
 */
public class Trades implements Serializable {
    private static final long serialVersionUID = -625445006411263343L;

    /**
     * Active trades can be either buy or sell
     * Archived trades (TradeHistory objects) can be completed (buyer/seller transaction) or cancelled
     */
    public enum TradeType {
        buy,
        sell,
        complete,
        cancelled
    }

    private int id;
    private TradeType type;
    private String userName;
    private int assetId;
    private int quantity;
    private int price; // price is price per unit
    private Date date;

    /**
     * A basic constructor
     */
    public Trades() { }

    /***
     * Trade constructor
     *
     * @param type Type of trade (Buy or sell)
     * @param userName The username of the user
     * @param assetId The ID of the asset being traded
     * @param quantity The quantity of the asset being traded
     * @param price The price per unit of asset
     */
    public Trades(TradeType type, String userName, int assetId, int quantity, int price) {
        this.type = type;
        this.userName = userName;
        this.assetId = assetId;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    /**
     * Trade constructor
     *
     * @param id The trade's ID
     * @param type Type of trade (Buy or sell)
     * @param userName The username of the user
     * @param assetId The ID of the asset being traded
     * @param quantity The quantity of the asset being traded
     * @param price The price per unit of asset
     */
    public Trades(int id, TradeType type, String userName, int assetId, int quantity, int price) {
        this.id = id;
        this.type = type;
        this.userName = userName;
        this.assetId = assetId;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Getter for the trade ID
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the trade ID
     * @param id
     */
    public void setId(int id) throws TradesException {
        if(id >= 0) {
            this.id = id;
        } else {
            throw new TradesException("The trade ID has to be a non-negative integer");
        }
    }

    /**
     * Getter for the trade type (buy or sell)
     * @return
     */
    public TradeType getType() {
        return type;
    }

    /**
     * Setter for the trade type (buy or sell)
     * @param type
     */
    public void setType(TradeType type) {
        this.type = type;
    }

    /**
     * Getter for the username of the buyer/seller
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the username of the buyer/seller
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for the ID of the asset being bought/sold
     * @return
     */
    public int getAssetId() {
        return assetId;
    }

    /**
     * Setter for the ID of the asset being bought/sold
     * @param assetId
     */
    public void setAssetId(int assetId) throws TradesException {
        if(assetId >= 0) {
            this.assetId = assetId;
        } else {
            throw new TradesException("The asset ID has to be a non-negative integer");
        }
    }

    /**
     * Getter for the quantity being bought/sold
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter for the quantity being bought/sold
     * @param quantity
     */
    public void setQuantity(int quantity) throws TradesException {
        if(quantity >= 0) {
            this.quantity = quantity;
        } else {
            this.quantity = 0;
            throw new TradesException("The quantity has to be a non-negative integer");
        }
    }

    /**
     * Getter for the price per unit of asset
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     * Setter for the price per unit of asset
     * @return
     */
    public void setPrice(int price) throws TradesException {
        if(price >= 0) {
            this.price = price;
        } else {
            throw new TradesException("The price has to be a non-negative integer");
        }
    }

    /**
     * Getter for the date/time the trade was posted
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for the date/time the trade was posted
     * @return
     */
    public void setDate(Date date) {
        this.date = date;
    }
}