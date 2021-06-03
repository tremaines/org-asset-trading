package Client;

/**
 * Thrown due to an exception with an Assets object
 */
public class AssetsException extends Exception{

    public AssetsException(){
        super();
    }

    public AssetsException(String message){
        super(message);
    }
}
