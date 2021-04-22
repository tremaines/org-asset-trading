package Client;

import java.time.LocalDate;

/**
 * Trade History gives the information about the asset and its trade
 * value along with the users that have traded it for users to know if they should
 * buy that asset.
 */
public class TradeHistory {

    // Asset type
    private String assetName;
    //
    private Double price;
    //
    private Integer quantity;
    //
    private LocalDate date;
    //
    private String username;
    //
    private String organisationNames;

    public User user;

    public Organisation organisation;

    private Trades trades;


    // For the graph, get Asset name, get Date (x-axis), get Asset buy price (y-axis)

    public TradeHistory(Trades trades)
    {
        this.trades = trades;
        organisation = new Organisation();
    }

    /***
     *
     */
    public void getTrade()
    {
        // When asset is being traded
        Trades  t = new Trades(organisation, user);
        t.getListing();

        // Get the information about it

        Assets a = new Assets();
        //
    }



    /***
     * This class adds the information of the trade
     *
     * @param assetName
     * @param price
     * @param quantity
     * @param date
     * @param organisationNames
     */
    public void addTrade(String assetName, Double price, Integer quantity, LocalDate date, String organisationNames)
    {

    }


}