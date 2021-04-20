package Client;

public class AssetsException extends Exception{
    
    public AssetsException(){
        super();
    }

    public AssetsException(String message){
        super(message);
    }

    public AssetsException(String message, Throwable cause){
        super(message, cause);
    }

    public AssetsException(Throwable cause) {
        super(cause);
    }
    
}
