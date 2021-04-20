package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    Organisation org;
    User user;
    List<String> assets;
    List<Integer> assetAmount;


    @BeforeEach
    @Test
    public void setUpUser() {
        // Parameters for Organisation object
        assets = new ArrayList<>();
        assetAmount = new ArrayList<>();

        assets.add("Hardware Resources");
        assetAmount.add(15);

        org = new Organisation();
        org.createOrganisation("Microsoft", 200, assets, assetAmount);
        user = new User();
        user.createUser("Tom", "abc123", false, "Microsoft");

    }

    // Checks the getUsername() method
    @Test
    public void getUsernameCheck(){
        assertEquals("Tom", user.getUser("Tom").getUsername());
    }


    // Checks the user's organisation name matches with that of the organisation name
    @Test
    public void getOrganisationCheck(){
        assertEquals("Microsoft",
                org.getOrganisation(user.getOrganisationName("Tom")).getOrganisationName());
    }

    // Checks the credits associated with the user's organisation
    @Test
    public void checkUserCredits(){
        assertEquals(200, org.getOrganisation(user.getOrganisationName("Tom")).getCredits());
    }

    // Checks whether passwords are hashed
    @Test
    public void hashPasswordCheck(){
        String pwd = "abc123";
        assertEquals(pwd.hashCode() * 2.334 + "", user.getUser("Tom").getHashedPassword());
    }

    // Checks admin status (true if admin, false otherwise)
    @Test
    public void checkAccountType(){
        assertFalse(user.getUser("Tom").getAdminStatus());
    }

    // Create Two users
    @Test
    public void createMultipleUsers() {
        User user1 = new User("Bob", "123test", true, "Microsoft");
        User user2 = new User("John", "qwerty", true, "Apple");
        assertTrue(user1.getUsername() != user2.getUsername());
    }


}
