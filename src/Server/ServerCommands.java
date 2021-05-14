package Server;

public enum ServerCommands {
    // User commands
    GET_USER,
    ADD_USER,
    UPDATE_USER,
    CHECK_USER,
    // Unit commands
    GET_UNIT_BY_ID,
    GET_UNIT_BY_NAME,
    ADD_UNIT,
    UPDATE_UNIT,
    GET_UNIT_NAMES,
    // Assets produced commands
    GET_ASSET_BY_ID,
    GET_ASSET_BY_NAME,
    ADD_ASSET,
    UPDATE_ASSET,
    GET_ASSET_NAMES,
    GET_ASSETS_BY_UNIT,
    GET_ASSET_AND_AMOUNTS,
    // Purchases commands
    ADD_ASSET_PURCHASED,
    // Trade commands
    GET_ALL_TRADES,
    GET_TRADES_BY_UNIT,
    ADD_TRADE,
    MATCH_TRADES,
    GET_TRADE_BY_ID,
    UPDATE_TRADE,
    DELETE_TRADE,
    // Trade Hx Commands
    ADD_TO_HISTORY
}
