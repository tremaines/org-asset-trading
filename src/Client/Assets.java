package Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Assets are available to be purchased or sold on the electronic marketplace, with
 * users spending credits to obtain said assets.
 */
public class Assets {

    // List of asset types available to be bought or sold
    private List<String> assets;
    // Asset type
    private String assetName;

    /**
     * Constructor of the asset classes that initialises the list of assets and adds the default
     * assets available onto the marketplace.
     */
    public Assets() {
        assets = new ArrayList<>();

        // Default assets available on the marketplace
        assets.add("Computational Resources");
        assets.add("Hardware Resources");
        assets.add("Software Licenses");
    }

    /**
     * Allows admin users (IT Staff) to create a new asset on the marketplace
     *
     * @param user user currently logged in
     * @param assetName name of the asset to be created
     */
    public void createAsset(User user, String assetName) {
        if(user.getAccountType() == "admin" && !assets.contains(assetName)) {
            assets.add(assetName);
        }
        else if(user.getAccountType() != "admin") {
            System.out.println("Only admins can add assets!");
        }
    }

    /**
     * Gets a list of all assets available to be bought or sold
     *
     * @return list of assets
     */
    public List<String> getAllAssets() {
        return assets;
    }
}
