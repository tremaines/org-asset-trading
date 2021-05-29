package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserTest {

    Units unit;
    User user;

    @BeforeEach
    @Test
    public void setUpUser()  {
        unit = new Units(1, "Human Resources", 1000);
        user = new User("Tom", "Smith", "tom@gmail.com", "tom123", "password", true, 1);
    }

    //  Checks the getFirstName() method
    @Test
    public void getFirstNameCheck(){
        assertEquals("Tom", user.getFirstName());
    }

    //  Checks the getLastName() method
    @Test
    public void getLastNameCheck(){
        assertEquals("Smith", user.getLastName());
    }

    //  Checks the getEmail() method
    @Test
    public void getEmailCheck(){
        assertEquals("tom@gmail.com", user.getEmail());
    }

    //  Checks the getUsername() method
    @Test
    public void getUsernameCheck(){
        assertEquals("tom123", user.getUsername());
    }

    //  Checks the getHashedPassword() method
    @Test
    public void getHashedPasswordCheck(){
        // TODO
    }

    //  Checks the getAdminStatus() method
    @Test
    public void getAdminStatusCheck(){
        assertEquals(true, user.getAdminStatus());
    }

    //  Checks the getUnit() method
    @Test
    public void getUserUnitCheck(){
        assertEquals(1, user.getUnit());
    }

//
//
//    // Checks the user's organisation name matches with that of the organisation name
//    @Test
//    public void getOrganisationCheck(){
//        assertEquals("Microsoft",
//                org.getOrganisation(user.getOrganisationName("Tom")).getOrganisationName());
//    }
//
//    // Checks the credits associated with the user's organisation
//    @Test
//    public void checkUserCredits(){
//        assertEquals(200, org.getOrganisation(user.getOrganisationName("Tom")).getCredits());
//    }
//
//    // Checks whether passwords are hashed
//    @Test
//    public void hashPasswordCheck(){
//        String pwd = "abc123";
//        assertEquals(pwd.hashCode() * 2.334 + "", user.getUser("Tom").getHashedPassword());
//    }
//
//    // Checks admin status (true if admin, false otherwise)
//    @Test
//    public void checkAccountType(){
//        assertFalse(user.getUser("Tom").getAdminStatus());
//    }
//
//    // Create Two users
//    @Test
//    public void createMultipleUsers() {
//        User user1 = new User("Bob", "123test", true, "Microsoft");
//        User user2 = new User("John", "qwerty", true, "Apple");
//        assertTrue(user1.getUsername() != user2.getUsername());
//    }
//
//    // Create Two users
//    @Test
//    public void getUserCheck() throws UserException {
//        user.createUser("Bob", "123test", true, "Microsoft");
//        String orgName = user.getUser("Bob").getOrganisationName();
//        assertEquals("Microsoft", orgName);
//    }
//
//    // Change password
//    @Test
//    public void changePassword() throws UserException {
//        user.createUser("Bob", "123test", true, "Microsoft");
//        String newPwd = "new123";
//        user.getUser("Bob").changePassword(newPwd);
//        assertEquals(newPwd.hashCode() * 2.334 + "", user.getUser("Bob").getHashedPassword());
//    }
//
//    // Change password multiple times
//    @Test
//    public void changePasswordManyTimes() throws UserException {
//        user.createUser("Bob", "123test", true, "Microsoft");
//        user.getUser("Bob").changePassword("pwdtest1");
//        user.getUser("Bob").changePassword("pwdtest2");
//        user.getUser("Bob").changePassword("pwdtest3");
//        user.getUser("Bob").changePassword("pwdtest4");
//        assertEquals("pwdtest4".hashCode() * 2.334 + "", user.getUser("Bob").getHashedPassword());
//    }

}
