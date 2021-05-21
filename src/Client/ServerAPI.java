package Client;

import Server.ServerCommands;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Properties;

/**
 * This class sends commands to the server and receives replies
 */
public class ServerAPI {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    /**
     * Constructor for the sever API class
     */
    public ServerAPI() {
        Properties props = new Properties();
        FileInputStream details = null;

        String ip = null;
        int port = 0;

        // Read in details for sever connection
        try {
            details = new FileInputStream("./src/Client/client.props");
            props.load(details);
            details.close();

            ip = props.getProperty("server.ip");
            port = Integer.parseInt(props.getProperty("server.port"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create socket connection
        try {
            socket = new Socket(ip, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve a user
     * @param username The username of the user to be retrieved
     * @return An instance of the user
     */
    public User getUser(String username) {
        try {
            outputStream.writeObject(ServerCommands.GET_USER);
            outputStream.writeObject(username);

            outputStream.flush();

            return (User) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check whether a user exists
     * @param username The username to be checked
     * @return True if they exist, false if not or null if there is a connection error
     */
    public Boolean checkUser(String username) {
        try {
            outputStream.writeObject(ServerCommands.CHECK_USER);
            outputStream.writeObject(username);
            outputStream.flush();

            return (Boolean) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Send a user to be added to the database
     * @param user The user to be added
     */
    public void addUser(User user) {
        try {
            outputStream.writeObject(ServerCommands.ADD_USER);
            outputStream.writeObject(user);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        try {
            outputStream.writeObject(ServerCommands.UPDATE_USER);
            outputStream.writeObject(user);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve a unit using its ID
     * @param id The unit ID
     * @return The unit associated with the ID or null if there is no unit with that ID
     */
    public Units getUnit(int id) {
        try {
            outputStream.writeObject(ServerCommands.GET_UNIT_BY_ID);
            outputStream.writeObject(id);
            outputStream.flush();

            return (Units) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieve a unit using its name
     * @param name The name of the unit
     * @return The unit associated with that name or null if there is no unit with that name
     */
    public Units getUnit(String name) {
        try {
            outputStream.writeObject(ServerCommands.GET_UNIT_BY_NAME);
            outputStream.writeObject(name);
            outputStream.flush();

            return (Units) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Send a new unit to the server to be added
     * @param unit The unit to be added
     */
    public void addUnit(Units unit) {
        try {
            outputStream.writeObject(ServerCommands.ADD_UNIT);
            outputStream.writeObject(unit);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send updated details of a unit to the server
     * @param unit The unit with its new details
     */
    public void updateUnit(Units unit) {
        try {
            outputStream.writeObject(ServerCommands.UPDATE_UNIT);
            outputStream.writeObject(unit);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve an array of all unit names stored in the database
     * @return A string array of all unit names
     */
    public String[] getUnitNames() {
        try {
            outputStream.writeObject(ServerCommands.GET_UNIT_NAMES);
            outputStream.flush();

            return (String[]) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieve an asset from the database
     * @param id The ID of the asset to be retrieved
     * @return The asset associated with that ID, null if there is none
     */
    public Assets getAsset(int id) {
        try {
            outputStream.writeObject(ServerCommands.GET_ASSET_BY_ID);
            outputStream.writeObject(id);
            outputStream.flush();

            return (Assets) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieve an asset from the database
     * @param name The name of the asset to be retrieved
     * @return The asset associated with that name, null if there is none
     */
    public Assets getAsset(String name) {
        try {
            outputStream.writeObject(ServerCommands.GET_ASSET_BY_NAME);
            outputStream.writeObject(name);
            outputStream.flush();

            return (Assets) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Send a new asset to the server to be added to the database
     * @param asset The asset to be added
     */
    public void addAsset(Assets asset) {
        try {
            outputStream.writeObject(ServerCommands.ADD_ASSET);
            outputStream.writeObject(asset);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the details of an existing asset
     * @param asset The asset object with the new details
     */
    public void updateAsset(Assets asset) {
        try {
            outputStream.writeObject(ServerCommands.UPDATE_ASSET);
            outputStream.writeObject(asset);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get an array of the names of all assets
     * @return A string array of asset names
     */
    public String[] getAssetName() {
        try {
            outputStream.writeObject(ServerCommands.GET_ASSET_NAMES);
            outputStream.flush();

            return (String[]) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a string array of assets produced by every unit except for the one provided
     * @param unitID The unit to be excluded
     * @return A string array of asset names
     */
    public String[] getAssetsExcluding(int unitID) {
        try {
            outputStream.writeObject(ServerCommands.GET_ASST_NAMES_EXCLUDING);
            outputStream.writeObject(unitID);
            outputStream.flush();

            return (String[]) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the names of all assets held by a unit
     * @param unitID The ID of the unit whose assets we want
     * @return A string array of the names of all assets held by that unit
     */
    public String[] getAssetsByUnit(int unitID) {
        try {
            outputStream.writeObject(ServerCommands.GET_ASSETS_BY_UNIT);
            outputStream.writeObject(unitID);
            outputStream.flush();

            return (String[]) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all the assets and the amount of that asset available
     * @param unitID The unit whose assets we want
     * @return A HashMap where the asset name is the key and the amount is the value
     */
    public HashMap<String, Integer> getAssetAndAmounts(int unitID) {
        try {
            outputStream.writeObject(ServerCommands.GET_ASSET_AND_AMOUNTS);
            outputStream.writeObject(unitID);
            outputStream.flush();

            return (HashMap<String, Integer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Send a new asset purchase to the database
     * @param assetID The ID of the asset
     * @param unitID The ID of the unit
     * @param assetQty The quantity of the asset purchased
     */
    public void addAssetPurchased(int assetID, int unitID, int assetQty) {
        try {
            outputStream.writeObject(ServerCommands.ADD_ASSET_PURCHASED);
            outputStream.writeObject(assetID);
            outputStream.writeObject(unitID);
            outputStream.writeObject(assetQty);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all current sell offers
     * @return A hashmap where the asset name is the key and the value is an integer array containing the
     * total quantity of the asset for sale and the lowest price
     */
    public HashMap<String, int[]> getAllSells() {
        try {
            outputStream.writeObject(ServerCommands.GET_ALL_TRADES);
            outputStream.flush();

            return (HashMap<String, int[]>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the trades of a given unit of a given type
     * @param unitID The unit ID
     * @param tradeType The trade type (buy or sell)
     * @return A HashMap where the trade ID is the key and the value is a string array containing the asset name,
     * price and quantity
     */
    public HashMap<Integer, String[]> getTradesByUnit(int unitID, String tradeType) {
        try {
            outputStream.writeObject(ServerCommands.GET_TRADES_BY_UNIT);
            outputStream.writeObject(unitID);
            outputStream.writeObject(tradeType);
            outputStream.flush();

            return (HashMap<Integer, String[]>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends a trade over to be handled by the server
     * @param newTrade The new trade
     * @throws TradesException
     */
    public void addTrade(Trades newTrade) throws TradesException {
        try {
            outputStream.writeObject(ServerCommands.ADD_TRADE);
            outputStream.writeObject(newTrade);
            outputStream.flush();

            if ((Integer)inputStream.readObject() != 0) {
                throw new TradesException();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Trades getTrade(int tradeID) {
        try {
            outputStream.writeObject(ServerCommands.GET_TRADE_BY_ID);
            outputStream.writeObject(tradeID);
            outputStream.flush();

            return (Trades)  inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cancelTrade(Trades trade) {
        try {
            outputStream.writeObject(ServerCommands.DELETE_TRADE);
            outputStream.writeObject(trade);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
