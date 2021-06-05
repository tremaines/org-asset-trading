package Client;

import Client.Trades.TradeType;

import java.io.Serializable;

/**
 * Trade History gives the information about the asset and its trade
 * value along with the users that have traded it for users to know if they should
 * buy that asset.
 */
public class TradeHistory implements Serializable {
    private static final long serialVersionUID = -6492420611118026208L;

    int id;
    TradeType status;
    int asset;
    int qty;
    int credits;
    String buyer;
    String seller;

    /**
     * Constructor
     */
    public TradeHistory() {
    }

    /**
     * Constructor generates trade history object from trade passed in (useful for cancelled trades
     *
     * @param trade The trade to be added to the history
     */
    public TradeHistory(Trades trade) {
        status = TradeType.cancelled;
        this.asset = trade.getAssetId();
        this.qty = trade.getQuantity();
        this.credits = trade.getPrice();
        if (trade.getType() == TradeType.buy) {
            this.buyer = trade.getUserName();
        } else {
            this.seller = trade.getUserName();
        }

    }

    /**
     * Constructor for TradeHistory object
     *
     * @param status Cancelled or complete
     * @param asset The asset id
     * @param qty The quantity of the asset traded
     * @param credits Price per unit of asset
     * @param buyer The buyer username
     * @param seller The seller username
     */
    public TradeHistory(TradeType status, int asset, int qty, int credits, String buyer, String seller) {
        this.status = status;
        this.asset = asset;
        this.qty = qty;
        this.credits = credits;
        this.buyer = buyer;
        this.seller = seller;
    }

    /**
     * Getter for trade history ID
     * @return The trade history ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for trade history ID
     * @param id
     */
    public void setId(int id) throws TradesException {
        if(id >= 0) {
            this.id = id;
        } else {
            throw new TradesException("The ID of the trade cannot be a negative integer");
        }
    }

    /**
     * Getter for the trade status
     * @return The final status of the trade
     */
    public TradeType getStatus() {
        return status;
    }

    /**
     * Setter for trade status
     * @param status
     */
    public void setStatus(TradeType status) {
        this.status = status;
    }

    /**
     * Getter for the asset traded
     * @return The ID of the asset traded
     */
    public int getAsset() {
        return asset;
    }

    /**
     * Setter for the asset traded
     * @param asset
     */
    public void setAsset(int asset) throws TradesException {
        if(asset >= 0) {
            this.asset = asset;
        } else {
            throw new TradesException("The asset cannot be a negative number");
        }
    }

    /**
     * Getter for the quantity of the asset traded
     * @return The quantity
     */
    public int getQty() {
        return qty;
    }

    /**
     * Setter for the quantity traded
     * @param qty
     */
    public void setQty(int qty) throws TradesException {
        if(qty >= 0) {
            this.qty = qty;
        } else {
            throw new TradesException("The quantity cannot be a negative number");
        }
    }

    /**
     * Getter for the cost per unit of asset
     * @return The price
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Setter for the cost per unit
     * @param credits
     */
    public void setCredits(int credits) throws TradesException {
        if(credits >= 0) {
            this.credits = credits;
        } else {
            throw new TradesException("The credits cannot be a negative number");
        }
    }

    /**
     * The username of the buyer (if applicable)
     * @return The buyer if it was a completed trade. Might be null the trade was a cancelled sale.
     */
    public String getBuyer() {
        return buyer;
    }

    /**
     * The setter for the buyer's username
     * @param buyer
     */
    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    /**
     * The username of the seller (if applicable)
     * @return The seller if it was a completed trade. Might be null the trade was a cancelled buy.
     */
    public String getSeller() {
        return seller;
    }

    /**
     * The setter for the seller
     * @param seller
     */
    public void setSeller(String seller) {
        this.seller = seller;
    }
}