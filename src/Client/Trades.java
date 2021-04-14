package Client;

import java.time.LocalDate;
import java.util.*;

/**
 * Trades are how the different types of assets are distributed and shared between different
 * organisations, with the use of electronic credits. Organisations are given budgets for a
 * certain number of credits and they are able to use these credits to purchase additional assets.
 * Similarly, assets may also be assigned to an organisation when they are created and these can
 * be sold for credits.
 */
public class Trades {

    // Type of trade listing (buy/sell)
    private String type;
    // Name of the organisation the user account belongs to
    private String organisationName;
    // Name of the user's account
    private String username;
    // Name of the asset
    private String assetName;
    // Quantity of the asset
    private int AssetAmount;
    // Price per unit of the asset
    private int assetPrice;
    // Date of the trade listing
    private LocalDate date;

    // Trade type (Buy/Sell)
    private static final int tradeType = 0;
    // Type of asset
    private static final int assetType = 1;
    // Quantity of the asset
    private static final int assetAmount = 2;
    // Cost per asset unit
    private static final int price = 3;
    // Username
    private static final int userName = 4;
    // Name of the organisation
    private static final int orgName = 5;
    // Trade fulfilled status (Yes/No/Partially/Cancelled)
    private static final int tradeFulfilled = 6;
    // Time of the listing
    private static final int time = 7;
    // Unique ID number of the trade
    private int tradeID;

    // Multi valued map of a key (tradeID) and its values
    Map<Integer, ArrayList<String>> multiValueMap = new HashMap<>();

//    /**
//     * User creates a new buy or sell listing for a specified amount of an asset at a set price
//     *
//     * @param user The user listing the order
//     * @param type Type of listing, i.e. Buy
//     * @param assetName Name of the asset
//     * @param assetAmount Quantity of the asset
//     * @param assetPrice Price per unit of the asset
//     */
//    public void createListing(User user, String type, String assetName, int assetAmount,
//                              int assetPrice) {
//        // The TradeID of a new listing to be added will always be one greater than the size of
//        // the map
//        tradeID = multiValueMap.size() + 1;
//
//        multiValueMap.put(tradeID, new ArrayList<String>());
//        multiValueMap.get(tradeID).add(type);
//        multiValueMap.get(tradeID).add(assetName);
//        // Converts asset amount to be stored as a string
//        multiValueMap.get(tradeID).add(assetAmount + "");
//        // Converts asset price to be stored as a string
//        multiValueMap.get(tradeID).add(assetPrice + "");
//        multiValueMap.get(tradeID).add(user.getUsername());
//        multiValueMap.get(tradeID).add(user.getOrganisationName());
//        // Trade fulfilled status
//        multiValueMap.get(tradeID).add("No");
//        multiValueMap.get(tradeID).add(LocalDate.now().toString());
//
//        // If buy listing, set new credits of Organisation to be current credits MINUS (no. of
//        // assets multiplied by price)
//
//        // If sell listing, remove asset quantity by listing amount if listing amount is less
//        // than total asset amount, otherwise remove asset and asset quantity from organisation
//    }
//
//    /**
//     * Gets the map of all trade listings
//     *
//     * @return All trade listings
//     */
//    public Map<Integer, ArrayList<String>> getListing() {
//        return multiValueMap;
//    }
//
//    /**
//     * Takes the tradeID of a listing and returns the listing type of the order
//     *
//     * @param tradeID The trade ID of the listing
//     * @return String value of the listing type, i.e. Buy
//     */
//    public String getTradeType(int tradeID) {
//        return multiValueMap.get(tradeID).get(tradeType);
//    }
//
//    public Boolean matchListing() {
//        // Checks trade type (Buy/sell)
//        type = this.getTradeType(tradeID);
//
//        // For loop to iterate through map to see if any asset type of the trade ID matches asset
//        // type of opposite trade type
//
//        // Iterates through multi value map to compare each key's values against that of the key
//        // entered (tradeID)
//        for (Map.Entry<Integer, ArrayList<String>> mvMap : multiValueMap.entrySet()) {
//            for (Map.Entry<Integer, ArrayList<String>> map : multiValueMap.entrySet()) {
//
//                // The key of the map is the trade ID
//                Integer trade_ID = map.getKey();
//                // Values within a list for the specified key
//                ArrayList<String> tradeDetails = map.getValue();
//
//                // Call differentTradeTypes() here
//
//
//            }
//        }
//
//        return null;
//    }
//
//    private boolean differentTradeTypes(int tradeID1, int tradeID2) {
//        int tradeIDSpare;
//
//        if(!(multiValueMap.get(tradeID1).get(tradeType) == "Buy")) {
//            tradeIDSpare = tradeID2;
//            tradeID1 = tradeID2;
//            tradeID2 = tradeIDSpare;
//        }
//
//        // Buy listing
//        ArrayList<String> tradeBuy = multiValueMap.get(tradeID1);
//        // Sell listing
//        ArrayList<String> tradeSell = multiValueMap.get(tradeID2);
//
//        boolean tradeBuyFulfilled =
//                tradeBuy.get(tradeFulfilled) != "Yes" && tradeBuy.get(tradeFulfilled) !=
//                        "Cancelled";
//        boolean tradeSellFulfilled =
//                tradeSell.get(tradeFulfilled) != "Yes" && tradeBuy.get(tradeFulfilled) !=
//                        "Cancelled";
//
//        if ((tradeBuy.get(tradeType) != tradeSell.get(tradeType)) && (tradeBuy.get(assetType) ==
//                tradeSell.get(assetType))) {
//
//            if((tradeBuy.get(orgName) != tradeSell.get(orgName)) && !(tradeBuyFulfilled && tradeSellFulfilled)) {
//
//                // Call tradeAmountsAndQuantities()
//
//
//
//                }
//            }
//        }
//    }
//
//    // Takes trade ID 1 and trade ID 2
//    private void tradeAmountsAndQuantities (int tradeID1, int tradeID2) {
//        // Buy listing
//        ArrayList<String> tradeBuy = multiValueMap.get(tradeID1);
//        // Sell listing
//        ArrayList<String> tradeSell = multiValueMap.get(tradeID2);
//
//        int buyPrice = Integer.parseInt(tradeBuy.get(price));
//        int sellPrice = Integer.parseInt(tradeSell.get(price));
//
//        int buyAmount = Integer.parseInt(tradeBuy.get(assetAmount)) ;
//        int sellAmount = Integer.parseInt(tradeSell.get(assetAmount));
//
//        // Difference in price between the buy and sell amount
//        int amountDifference = buyAmount - sellAmount;
//        int absDifference = Math.abs(amountDifference);
//
//        // Credits to be returned to the buyer if the buyer's order price is greater than the
//        // seller's
//        int returnCredits;
//
//        if(sellAmount > buyAmount) {
//            returnCredits = buyAmount * amountDifference;
//
//            // Minus asset quantity of the sold amount from sell listing quantity
//            int newSellAmount =
//                    Integer.parseInt(tradeSell.get(assetAmount)) -
//                            Integer.parseInt(tradeBuy.get(assetAmount));
//
//            // Sets new quantity for sell listing
//            tradeSell.set(assetAmount, newSellAmount + "");
//
//            // Give assets and asset quantities to buyer's organisation
//
//            // Give credits to seller's organisation
//
//            // Set buy order to fulfilled (Yes), set sell order to partial
//            tradeBuy.set(tradeFulfilled, "Yes");
//            tradeSell.set(tradeFulfilled, "Partial");
//
//            // Return credits to buyer
//
//        } else if (sellAmount == buyAmount) {
//            returnCredits = buyAmount * amountDifference;
//
//            // Give assets and asset quantities to buyer's organisation
//
//            // Minus asset quantity of the sold amount from sell listing
//
//            // Give credits to seller's organisation
//
//            // Set buy order to fulfilled, set sell order to partial
//
//            // Return credits to buyer
//
//
//        } else {
//            returnCredits = (buyAmount - sellAmount) * amountDifference;
//
//
//
//        }
//
//
//
//
//    }
}
