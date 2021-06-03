package Client;

/**
 * Thrown due to an issue with a User object
 */
public class UserException extends Exception {

    public UserException(){
        super();
    }

    public UserException(String message){
        super(message);
    }
}
