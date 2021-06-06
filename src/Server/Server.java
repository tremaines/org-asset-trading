package Server;

import Client.*;
import common.ServerCommands;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class for server functionality
 */
public class Server {

    private static final int SOCKET_ACCEPT_TIMEOUT = 100;

    private static final int SOCKET_READ_TIMEOUT = 5000;

    private AtomicBoolean running = new AtomicBoolean(true);

    // Database wrappers
    AssetDBSource assets;
    HistoryDBSource tradeHx;
    PurchasesDBSource purchases;
    TradeDBSource trades;
    UnitDBSource units;
    UserDBSource users;
    TradeLogic tradeLogic;

    /**
     * Starts the server by reading the port number and waiting for a connection
     */
    public void start() {
        // Initialise the DB Wrapper classes
        assets = new AssetDBSource();
        tradeHx = new HistoryDBSource();
        purchases = new PurchasesDBSource();
        trades = new TradeDBSource();
        units = new UnitDBSource();
        users = new UserDBSource();
        tradeLogic = new TradeLogic(units, users, trades, purchases, tradeHx);

        // Read the port number from the .props file
        Properties props = new Properties();
        FileInputStream details = null;
        int port = 0;

        try {
            details = new FileInputStream("./src/Server/dbserver.props");
            props.load(details);
            details.close();
            port = Integer.parseInt(props.getProperty("server.port"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Invoke sever socket connection
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(SOCKET_ACCEPT_TIMEOUT);
            // While server is running, wait for a client to connect
            for(;;) {
                if (!running.get()) {
                    break;
                }
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("New connection");
                    socket.setSoTimeout(SOCKET_READ_TIMEOUT);

                    Thread newClient = new Thread(() -> clientConnection(socket));
                    newClient.start();
                } catch (SocketTimeoutException to) {
                    // ignore and continue
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }

        System.exit(0);
    }

    public void shutdown() {
        running.set(false);
    }

    /**
     * Reads in the client command
     * @param socket
     */
    private void clientConnection(Socket socket) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            while(true) {
                try {
                    final ServerCommands command = (ServerCommands) inputStream.readObject();
                    implementCommands(inputStream, outputStream, command);
                } catch (SocketTimeoutException ste) {
                    continue;
                } catch (TradesException e) {
                    e.printStackTrace();
                }
                catch (EOFException eof) {
                    System.err.println("Client has disconnected");
                    return;
                } catch (UnitsException e) {
                    e.printStackTrace();
                } catch (AssetsException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassCastException | ClassNotFoundException err) {
            err.printStackTrace();
        }
    }

    /**
     * Checks client command and performs appropriate action
     * @param inputStream The input stream to read from the client
     * @param outputStream The output stream to send back to the client
     * @param command The client's command
     * @throws IOException Issue with input/output of an object
     * @throws ClassNotFoundException Class not found
     */
    private void implementCommands(ObjectInputStream inputStream, ObjectOutputStream outputStream,
                                   ServerCommands command) throws IOException, ClassNotFoundException, TradesException, UnitsException, AssetsException {
        switch (command) {

            case GET_USER: {
                final String userName = (String) inputStream.readObject();
                synchronized (users) {
                    final User user = users.getUser(userName);
                    outputStream.writeObject(user);
                }
                outputStream.flush();
            }
            break;

            case ADD_USER: {
                final User user = (User) inputStream.readObject();
                synchronized (users) {
                    users.addUser(user);
                }
            }
            break;

            case UPDATE_USER: {
                final User user = (User) inputStream.readObject();
                synchronized (users) {
                    users.update(user);
                }
            }
            break;

            case CHECK_USER: {
                final String userName = (String) inputStream.readObject();
                synchronized (users) {
                    outputStream.writeObject(users.checkUsername(userName));
                }
                outputStream.flush();
            }
            break;

            case GET_UNIT_BY_ID: {
                final Integer id = (Integer) inputStream.readObject();
                synchronized (units) {
                    final Units unit = units.getUnit(id);

                    outputStream.writeObject(unit);
                }
                outputStream.flush();
            }
            break;

            case GET_UNIT_BY_NAME: {
                final String name = (String) inputStream.readObject();
                synchronized (units) {
                    final Units unit = units.getUnit(name);

                    outputStream.writeObject(unit);
                }
                outputStream.flush();
            }
            break;

            case ADD_UNIT: {
                final Units unit = (Units) inputStream.readObject();
                synchronized (units) {
                    units.add(unit);
                }
            }
            break;

            case UPDATE_UNIT: {
                final Units unit = (Units) inputStream.readObject();
                synchronized (units) {
                    units.update(unit);
                }
            }
            break;

            case GET_UNIT_NAMES: {
                synchronized (units) {
                    outputStream.writeObject(units.getUnitNames());
                }
                outputStream.flush();
            }
            break;

            case GET_ALL_ASSETS: {
                synchronized (assets) {
                    final Assets[] asset = assets.getAllAssets();

                    outputStream.writeObject(asset);
                }
                outputStream.flush();
            }
            break;

            case GET_OWNED_UNOWNED: {
                final Integer unitID = (Integer) inputStream.readObject();
                synchronized (purchases) {
                    outputStream.writeObject(purchases.getOwnedAndUnowned(unitID));
                }
                outputStream.flush();
            }
            break;

            case GET_ASSET_BY_NAME: {
                final String name = (String) inputStream.readObject();
                synchronized (assets) {
                    final Assets asset = assets.getAsset(name);

                    outputStream.writeObject(asset);
                }
                outputStream.flush();
            }
            break;

            case GET_ALL_ASSETS_BY_ID: {
                final Integer unitID = (Integer) inputStream.readObject();
                synchronized (assets) {
                    outputStream.writeObject(purchases.getAssetsByUnit(unitID));
                }
                outputStream.flush();
            }
            break;

            case ADD_ASSET: {
                final Assets asset = (Assets) inputStream.readObject();
                synchronized (assets) {
                    assets.add(asset);
                }
            }
            break;

            case UPDATE_ASSET: {
                final Assets asset = (Assets) inputStream.readObject();
                synchronized (assets) {
                    assets.update(asset);
                }
            }
            break;

            case ADD_ASSET_PURCHASED: {
                final Integer asset = (Integer) inputStream.readObject();
                final Integer unit = (Integer) inputStream.readObject();
                final Integer qty = (Integer) inputStream.readObject();
                final Boolean replace = (Boolean) inputStream.readObject();
                synchronized (purchases) {
                    purchases.addToPurchases(asset, unit, qty, replace);
                }
            }
            break;

            case GET_ALL_TRADES: {
                synchronized (trades) {
                    outputStream.writeObject(trades.getAllBuyDetails());
                }
                outputStream.flush();
            }
            break;

            case GET_TRADES_BY_UNIT: {
                Integer id = (Integer) inputStream.readObject();
                String type = (String) inputStream.readObject();
                synchronized (trades) {
                    outputStream.writeObject(trades.getTradesByUnit(id, type));
                }
                outputStream.flush();
            }
            break;

            case GET_TRADE_BY_ID: {
                Integer tradeID = (Integer) inputStream.readObject();
                synchronized (trades) {
                    outputStream.writeObject(trades.getTrade(tradeID));
                }
                outputStream.flush();
            }
            break;

            case GET_TYPE_OF_TRADE: {
                String typeOfTrade = (String) inputStream.readObject();
                synchronized (trades) {
                    outputStream.writeObject(trades.getTypeOfTrade(typeOfTrade));
                }
                outputStream.flush();
            }
            break;

            case GET_BY_TYPE_AND_ASSET: {
                String typeofTrade = (String) inputStream.readObject();
                String assetName = (String) inputStream.readObject();
                synchronized (trades) {
                    outputStream.writeObject(trades.getByAssetAndType(typeofTrade, assetName));
                }
                outputStream.flush();
            }
            break;

            case ADD_TRADE: {
                final Trades trade = (Trades) inputStream.readObject();
                outputStream.writeObject(tradeLogic.setTrade(trade));
                outputStream.flush();
            }
            break;

            case DELETE_TRADE: {
                final Trades trade = (Trades) inputStream.readObject();
                tradeLogic.cancelTrade(trade);
            }
            break;

            case GET_HISTORY: {
                String assetName = (String) inputStream.readObject();
                synchronized (tradeHx) {
                    outputStream.writeObject(tradeHx.getHistoryOfAsset(assetName));
                }
                outputStream.flush();
            }
            break;
        }
    }
}
