package Client;

public class TradesException extends Exception{

    public TradesException(){
        super();
    }

    public TradesException(String message){
        super(message);
    }

    public TradesException(String message, Throwable cause){
        super(message, cause);
    }

    public TradesException(Throwable cause) {
        super(cause);
    }
}
