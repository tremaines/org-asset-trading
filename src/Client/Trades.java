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

    // Instance of the Organisation class
    private Organisation org;
    // Instance of the Organisation class
    private User users;
    // Total quantity of a listed asset an organisation owns
    int orgAssetQuantity;

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
    // Unique ID number of the trade
    private int tradeID;

    // Multi valued map of a key (tradeID) and its values
    Map<Integer, ArrayList<String>> multiValueMap = new HashMap<>();

    // Multi valued map containing summary data of a completed or partially completed sell listing
    Map<Integer, ArrayList<String>> tradeHistory = new HashMap<>();


    /**
     * Trade constructor that takes instances of the organisational and user classes
     *
     * @param organisation Instance of the Organisation class
     * @param user Instance of the User class
     */
    public Trades(Organisation organisation, User user) {
        this.org = organisation;
        this.users = user;
    }


    /**
     * User creates a new buy or sell listing for a specified amount of an asset at a set price
     *
     * @param username Username of the account creating the listing
     * @param type Type of listing (Buy/Sell)
     * @param assetName Name of the asset being traded
     * @param assetAmount Amount of the asset to be traded
     * @param assetPrice Price of the asset per unit
     */
    public void createListing(String username,
                              String type,
                              String assetName, int assetAmount, int assetPrice) {
        // Gets the instance of the Organisation associated with the user from the list of all
        // Organisations
        Organisation listingOrganisation = new Organisation();
        listingOrganisation = org.getOrganisation(org.getOrganisationList(),
                users.getOrganisationName(username));

        // Index of the asset in the list of assets owned by the user's organisation
        int index;
        if(type == "Sell") {
            index = listingOrganisation.getAssets().indexOf(assetName);

            // Current quantity of the asset that the organisation is listing owns
            orgAssetQuantity = listingOrganisation.getAmounts().get(index);
        }

        // Gets current credits owned by an organisation (For buy listing)
        int currentCredits = listingOrganisation.getCredits();

        // Total credit cost of purchasing all the asset units of the buy listing
        int totalCredits = assetPrice * assetAmount;

        // Prevents a sell listing in which a user lists more asset units than they own AND
        // prevents a buy listing where a user does not have enough credits to purchase all units
        if((type == "Sell") && (orgAssetQuantity < assetAmount)) {
            // Throw Exception here
            System.out.println("You are attempting to list more assets than you currently own.");
        } else if(type == "Buy" && (totalCredits > currentCredits)) {
            // Throw Exception here
            System.out.println("You do not have enough credits to create this listing.");
        } else {

            // The TradeID of a new listing to be added will always be one greater than the size of
            // the map
            tradeID = multiValueMap.size() + 1;

            // Values to be added into the multi value map, index values of each value in the map
            // can be found above declared as static variables. Additionally, all integers have
            // to be stored as strings as the multi value map stores the entire array of values
            // for each key as a string.
            multiValueMap.put(tradeID, new ArrayList<String>());
            multiValueMap.get(tradeID).add(type);
            multiValueMap.get(tradeID).add(assetName);
            multiValueMap.get(tradeID).add(assetAmount + "");
            multiValueMap.get(tradeID).add(assetPrice + "");
            // Stores a copy of the original listing amount (other assetAmount will get modified)
            multiValueMap.get(tradeID).add(assetAmount + "");
            multiValueMap.get(tradeID).add(username);
            multiValueMap.get(tradeID).add(users.getOrganisationName(username));
            multiValueMap.get(tradeID).add("No");
            multiValueMap.get(tradeID).add(LocalDate.now().toString());

            switch(type) {
                case "Buy":
                    // Removes total credits cost for the buy listing purchase
                    listingOrganisation.setCredits(currentCredits - totalCredits);
                    break;
                case "Sell":
                    // Remove asset quantity by listing amount if listing amount is less than
                    // the total asset amount, otherwise removes asset and asset quantity from the
                    // organisation's asset and asset amount lists, respectively
                    listingOrganisation.removeAssets(assetName, assetAmount);
                    break;
            }

            // If there are at least two listings, checks if there any potential matches
            if(multiValueMap.size() > 1) {
                matchListing();
            }
        }
    }

    /**
     * Gets the map of all trade listings
     *
     * @return All trade listings
     */
    public Map<Integer, ArrayList<String>> getListing() {
        return multiValueMap;
    }

    /**
     * Takes the tradeID of a listing and returns the listing type of the order
     *
     * @param tradeID The trade ID of the listing
     * @return String value of the listing type, i.e. Buy
     */
    public String getTradeType(int tradeID) {
        return multiValueMap.get(tradeID).get(tradeType);
    }

    /**
     * Gets the most recent listing created and then iterates through the list of all currently
     * available listings to check if the two listings can be fulfilled
     */
    public void matchListing() {

        // The tradeID for the listed trade will the size of the multi value map
        int tradeID1 = multiValueMap.size();
        String typeTrade1 = multiValueMap.get(tradeID1).get(tradeType);
        String assetTrade1 = multiValueMap.get(tradeID1).get(assetType);

        // Iterates through multi value map to compare each key's values against that of the most
        // recent listing created (tradeID1)
        for (Map.Entry<Integer, ArrayList<String>> mvMap : multiValueMap.entrySet()) {
            for (Map.Entry<Integer, ArrayList<String>> map : multiValueMap.entrySet()) {

                // The key of the map is the trade ID
                Integer tradeID2 = map.getKey();
                String typeTrade2 = multiValueMap.get(tradeID2).get(tradeType);
                String assetTrade2 = multiValueMap.get(tradeID2).get(assetType);

                // Checks if trade types are both different (Buy & Sell) and the listings contain
                // the asset type
                if((typeTrade1 != typeTrade2) && (assetTrade1 == assetTrade2)) {
                    tradeCompatibility(tradeID1, tradeID2);
                }
            }
        }
    }

    /**
     * Helper method for matchListing() that checks the compatibility of listings to ensure that
     * listings can appropriately trade with each other
     *
     * @param tradeID1 Trade ID of a listing
     * @param tradeID2 Trade ID of a listing
     */
    private void tradeCompatibility(int tradeID1, int tradeID2) {

        // Variable used to swap tradeIDs if tradeID1 is not the Buy listing
        int tradeIDSpare;

        // Checks if tradeID1 is the Buy listing, if not, swaps with tradeID2 so it will become the
        // buy listing. This ensures tradeID1 will always be the buy listing and tradeID2 the
        // sell listing.
        if(!(multiValueMap.get(tradeID1).get(tradeType) == "Buy")) {
            tradeIDSpare = tradeID2;
            tradeID1 = tradeID2;
            tradeID2 = tradeIDSpare;
        }

        // Price per unit of an asset for the buy and sell listings
        int buyValue = Integer.parseInt(multiValueMap.get(tradeID1).get(price));
        int sellValue = Integer.parseInt(multiValueMap.get(tradeID2).get(price));

        // If buy value per unit greater than or equal to the sell value per unit
        if(buyValue >= sellValue) {
            // Buy listing
            ArrayList<String> tradeBuy = multiValueMap.get(tradeID1);

            // Sell listing
            ArrayList<String> tradeSell = multiValueMap.get(tradeID2);

            // Gets boolean value of the trade status of the sell order
            boolean tradeBuyFulfilled =
                    tradeBuy.get(tradeFulfilled) == "Yes" || tradeBuy.get(tradeFulfilled) ==
                            "Cancelled";

            // Gets boolean value of the trade status of the sell order
            boolean tradeSellFulfilled =
                    tradeSell.get(tradeFulfilled) == "Yes" || tradeBuy.get(tradeFulfilled) ==
                            "Cancelled";

            // Checks if listing orders are from different organisations and that the orders have
            // not have been fulfilled
            if((tradeBuy.get(orgName) != tradeSell.get(orgName)) &&
                    (!tradeBuyFulfilled && !tradeSellFulfilled)) {
                // Calls completeTrade() method, see documentation
                completeTrade(tradeID1, tradeID2);
            }
        }
    }

    /**
     * Helper method for tradeCompatibility() method that completes the transfer of assets and
     * credits according to each corresponding listing
     *
     * @param tradeID1 Trade ID of the buy listing
     * @param tradeID2 Trade ID of the sell listing
     */
    private void completeTrade(int tradeID1, int tradeID2) {

        // Buy listing
        ArrayList<String> tradeBuy = multiValueMap.get(tradeID1);

        // Sell listing
        ArrayList<String> tradeSell = multiValueMap.get(tradeID2);

        // Gets Organisation object associated with the user who created the Buy listing
        Organisation orgBuyer = org.getOrganisation(org.getOrganisationList(),
                tradeBuy.get(orgName));

        // Gets Organisation object associated with the user who created the Sell listing
        Organisation orgSeller = org.getOrganisation(org.getOrganisationList(),
                tradeSell.get(orgName));

        // Buy price per unit of an asset
        int buyPrice = Integer.parseInt(tradeBuy.get(price));

        // Sell price per unit of an asset
        int sellPrice = Integer.parseInt(tradeSell.get(price));

        // Buy amount of an asset
        int buyAmount = Integer.parseInt(tradeBuy.get(assetAmount)) ;

        // Sell amount of an asset
        int sellAmount = Integer.parseInt(tradeSell.get(assetAmount));

        // Difference in price between the buy and sell amount
        int priceDifference;

        // Credits to be returned to the buyer if the buyer's order price is greater than the
        // seller's
        int returnCredits;
        int newCredit;
        int newAmount;

        if(sellAmount > buyAmount) {
            // Gets the amount of credits to be returned to the buyer (if any)
            priceDifference = buyPrice - sellPrice;
            returnCredits = priceDifference * buyAmount;

            // Give credits to seller's organisation
            newCredit = Integer.parseInt(tradeBuy.get(assetAmount)) *
                    Integer.parseInt(tradeSell.get(price));
            orgSeller.setCredits(orgSeller.getCredits() + newCredit);

            // Give assets and asset quantities to buyer's organisation
            orgBuyer.addAssets(tradeBuy.get(assetType), Integer.parseInt(tradeBuy.get(assetAmount)));

            // Returns outstanding credits to buyer (if any)
            orgBuyer.setCredits(orgBuyer.getCredits() + returnCredits);

            // Minus asset quantity of the sold amount from sell listing quantity
            newAmount = Integer.parseInt(tradeSell.get(assetAmount)) -
                            Integer.parseInt(tradeBuy.get(assetAmount));

            // Sets new quantities for both listings
            tradeSell.set(assetAmount, newAmount + "");
            tradeBuy.set(assetAmount, "0");

            // Set buy order to fulfilled (Yes), set sell order to partial
            tradeBuy.set(tradeFulfilled, "Yes");
            tradeSell.set(tradeFulfilled, "Partial");

            // Stores summary of sell listing data
            addTradeHistory(tradeID2);

        } else if (sellAmount == buyAmount) {
            // Gets the amount of credits to be returned to the buyer (if any)
            priceDifference = buyPrice - sellPrice;
            returnCredits = priceDifference * buyAmount;

            // Give credits to seller's organisation
            newCredit = Integer.parseInt(tradeBuy.get(assetAmount)) *
                    Integer.parseInt(tradeSell.get(price));
            orgSeller.setCredits(orgSeller.getCredits() + newCredit);

            // Give assets and asset quantities to buyer's organisation
            orgBuyer.addAssets(tradeBuy.get(assetType),
                    Integer.parseInt(tradeBuy.get(assetAmount)));

            // Returns outstanding credits to buyer (if any)
            orgBuyer.setCredits(orgBuyer.getCredits() + returnCredits);

            // Sets quantity of assets to be traded to be 0 for both buy and sell listings
            tradeSell.set(assetAmount, "0");
            tradeBuy.set(assetAmount, "0");

            // Set both orders status to "Yes", as they have been fulfilled
            tradeBuy.set(tradeFulfilled, "Yes");
            tradeSell.set(tradeFulfilled, "Yes");

            // Stores summary of sell listing data
            addTradeHistory(tradeID2);

        } else {
            // Gets the amount of credits to be returned to the buyer (if any)
            priceDifference = buyPrice - sellPrice;
            returnCredits = priceDifference * sellAmount;

            // Give credits to seller's organisation
            newCredit = Integer.parseInt(tradeSell.get(assetAmount)) *
                    Integer.parseInt(tradeSell.get(price));
            orgSeller.setCredits(orgSeller.getCredits() + newCredit);

            // Give assets and asset quantities to buyer's organisation
            orgBuyer.addAssets(tradeBuy.get(assetType),
                    Integer.parseInt(tradeSell.get(assetAmount)));

            // Returns outstanding credits to buyer (if any)
            orgBuyer.setCredits(orgBuyer.getCredits() + returnCredits);

            // Minus asset quantity of the sold amount from sell listing quantity
            newAmount = Integer.parseInt(tradeBuy.get(assetAmount)) -
                    Integer.parseInt(tradeSell.get(assetAmount));

            // Sets new quantities for both listings
            tradeBuy.set(assetAmount, newAmount + "");
            tradeSell.set(assetAmount, "0");

            // Set buy order to partial and set sell order to Yes (fulfilled)
            tradeBuy.set(tradeFulfilled, "Partial");
            tradeSell.set(tradeFulfilled, "Yes");

            // Stores summary of sell listing data
            addTradeHistory(tradeID2);
        }
    }

    /**
     * Allows a user to cancel their listing and have their assets or credits refunded back into
     * their account
     *
     * @param tradeID ID number of the trade, which is also the key in the multi value map
     */
    public void cancelListing(int tradeID) {
        // Gets the values associated with trade that the user wishes to cancel
        ArrayList<String> tradeToCancel = multiValueMap.get(tradeID);
        // Type of listing (Buy/Sell)
        String listingType = tradeToCancel.get(tradeType);

        // Quantity of an asset to purchase on a Buy listing
        int listingAmount;
        // Price per unit of an asset on a Buy listing
        int listingPrice;
        // Credits owned by the user
        int currentCredits;

        // Gets Organisation object associated with the user who is cancelling the trade
        Organisation orgCancel = org.getOrganisation(org.getOrganisationList(),
                tradeToCancel.get(orgName));

        switch (listingType) {
            case "Buy":
                // Set trade status to "Cancelled"
                tradeToCancel.set(tradeFulfilled, "Cancelled");

                // Return credits to user
                currentCredits = orgCancel.getCredits();
                listingAmount = Integer.parseInt(tradeToCancel.get(assetAmount));
                listingPrice = Integer.parseInt(tradeToCancel.get(price));

                orgCancel.setCredits(currentCredits + (listingAmount * listingPrice));

                // Set assetAmount to 0
                tradeToCancel.set(assetAmount, "0");
                break;

            case "Sell":
                // Set trade status to "Cancelled
                tradeToCancel.set(tradeFulfilled, "Cancelled");

                // Return assets to user (assetAmount)
                orgCancel.addAssets(tradeToCancel.get(assetType),
                        Integer.parseInt(tradeToCancel.get(assetAmount)));

                // Set assetAmount to 0
                tradeToCancel.set(assetAmount, "0");
                break;
        }
    }

    /**
     * Adds summary trade history data into a multi value map
     *
     * @param sellTradeID Trade ID of the sell listing that has been completed or partially
     *                    completed
     */
    public void addTradeHistory(int sellTradeID) {
        // The IDs of the history of completed trades will the size of the tradeHistory map
        int tradeHistoryID = tradeHistory.size() + 1;

        // Sell listing
        ArrayList<String> tradeSell = multiValueMap.get(sellTradeID);

        tradeHistory.put(tradeHistoryID, new ArrayList<String>());
        tradeHistory.get(tradeID).add(tradeSell.get(assetType));
        tradeHistory.get(tradeID).add(tradeSell.get(price) + "");
        tradeHistory.get(tradeID).add(LocalDate.now().toString());
    }

    /**
     * Gets the map of the trade history data required to form a graph of an asset's price vs the
     * data
     *
     * @return Summary data of all completed trades
     */
    public Map<Integer, ArrayList<String>> getTradeHistory() {
        return tradeHistory;
    }
}
