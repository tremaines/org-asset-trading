package Client;

/**
 * Thrown when a trade is being placed but the unit does not have enough credits (buy) or assets (sell)
 */
public class TradesException extends Exception{

    public TradesException(){
        super();
    }

    public TradesException(String message){
        super(message);
    }
}

