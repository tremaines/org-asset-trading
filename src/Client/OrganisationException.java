package Client;

public class OrganisationException extends Exception {

    public OrganisationException(){
        super();
    }

    public OrganisationException(String message){
        super(message);
    }

    public OrganisationException(String message, Throwable cause){
        super(message, cause);
    }

    public OrganisationException(Throwable cause) {
        super(cause);
    }
}
