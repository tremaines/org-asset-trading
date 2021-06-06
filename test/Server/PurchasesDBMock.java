package Server;

import Client.Assets;
import Client.AssetsException;

import java.util.ArrayList;

/**
 * A mock implementation of the assets_owned table database
 */
public class PurchasesDBMock implements PurchasesDB {

    private final ArrayList<Assets> owned;

    public PurchasesDBMock(ArrayList<Assets> owned) {
        this.owned = owned;
    }

    @Override
    public void addToPurchases(int asset, int unit, int qty, boolean replace) throws AssetsException {
        boolean relationshipExists = false;
        for (int i = 0; i < owned.size(); i++) {
            int assetID = owned.get(i).getAssetID();
            int unitID = owned.get(i).getUnitID();
            if (assetID == asset && unitID == unit) {
                relationshipExists = true;
                if (replace) {
                    owned.get(i).setQuantity(qty);
                } else {
                    int currentQty = owned.get(i).getQuantity();
                    owned.get(i).setQuantity(currentQty + qty);
                }
            }
        }
        if (!relationshipExists) {
            Assets newAsset = new Assets(asset, "name", qty, unit);
        }
    }

    @Override
    public Assets[] getAssetsByUnit(int unitID) {
        return new Assets[0];
    }

    @Override
    public Assets[] getOwnedAndUnowned(int unitID) {
        return new Assets[0];
    }

    @Override
    public Assets getAsset(int assetID, int unitID) {
        for(Assets asset : owned) {
            if (asset.getAssetID() == assetID && asset.getUnitID() == unitID) {
                return asset;
            }
        }
        return null;
    }
}
