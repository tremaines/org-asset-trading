package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

    //  Checks the hashPassword() method
    @Test
    public void hashedPasswordCheck(){
        String originalPwd = "hello123";
        String hashedPwd = (originalPwd.hashCode() * 2.334) + "";
        assertEquals("" + -1.850199741504E9, hashedPwd);
    }

    //  Checks the getHashedPassword() method
    @Test
    public void getHashedPasswordCheck() throws UserException{
        String originalPwd = "hello123";
        String hashedPwd = (originalPwd.hashCode() * 2.334) + "";
        user.setPassword(hashedPwd);
        assertEquals(hashedPwd, user.getHashedPassword());
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

    //  Checks the setFirstName() method
    @Test
    public void setFirstNameCheck(){
        user.setFirstName("Bob");
        assertEquals("Bob", user.getFirstName());
    }

    //  Checks the setLastName() method
    @Test
    public void setLastNameCheck(){
        user.setLastName("lastname");
        assertEquals("lastname", user.getLastName());
    }

    //  Checks the setEmail() method
    @Test
    public void setEmailCheck() {
        user.setEmail("test@mail.com");
        assertEquals("test@mail.com", user.getEmail());
    }

    //  Checks the setUsername() method
    @Test
    public void setUsernameCheck() throws UserException{
        user.setUsername("tom456");
        assertEquals("tom456", user.getUsername());
    }

    // Checks the setUsername() method if input is empty
    @Test
    public void setUserNameEmptyCheck() throws UserException{
        assertThrows(UserException.class, () -> {
            user.setUsername("");
        });
    }

    //  Checks the setPassword() method
    @Test
    public void setPasswordCheck() throws UserException{
        user.setPassword("dog123");
        assertEquals("dog123", user.getHashedPassword());
    }

    //  Checks the setPassword() method if input is empty
    @Test
    public void setPasswordEmptyCheck() throws UserException{
        assertThrows(UserException.class, () -> {
            user.setPassword("");
        });
    }

    //  Checks the password after multiple changes in a row
    @Test
    public void setPasswordMultipleTimesCheck() throws UserException{
        user.setPassword("dog123");
        user.setPassword("hello1");
        user.setPassword("cab302");
        assertEquals("cab302", user.getHashedPassword());
    }

    //  Checks the setAdmin() method
    @Test
    public void setAdminCheck() {
        user.setAdmin(false);
        assertEquals(false, user.getAdminStatus());
    }

    //  Checks the setAdmin() method after multiple changes
    @Test
    public void setAdminMultipleTimesCheck() {
        user.setAdmin(false);
        user.setAdmin(true);
        user.setAdmin(false);
        user.setAdmin(true);
        assertEquals(true, user.getAdminStatus());
    }

    //  Checks the setUnit() method
    @Test
    public void setUnitCheck() throws UserException {
        user.setUnit(2);
        assertEquals(2, user.getUnit());
    }

    //  Checks the setUnit() method after multiple uses in a row
    @Test
    public void setUnitMultipleTimesCheck() throws UserException{
        user.setUnit(2);
        user.setUnit(4);
        user.setUnit(5);
        assertEquals(5, user.getUnit());
    }

    //  Checks that the setUnit() throws exception if set to 0
    @Test
    public void setUnitToZeroCheck(){
        assertThrows(UserException.class, () -> {
            user.setUnit(0);
        });
    }

    //  Checks that the setUnit() doesn't allow negatives
    @Test
    public void setUnitNegativeCheck(){
        // Check zero
        assertThrows(UserException.class, () -> {
            user.setUnit(0);
        });

        // Check negative
        assertThrows(UserException.class, () -> {
            user.setUnit(-23);
        });
    }

    //  Checks the getBuyNotificationStatus() method
    @Test
    public void getBuyNotificationStatusCheck(){
        assertEquals(false, user.getBuyNotificationStatus());
    }

    //  Checks the getSellNotificationStatus() method
    @Test
    public void getSellNotificationStatusCheck(){
        assertEquals(false, user.getSellNotificationStatus());
    }

    //  Checks the setNotificationStatus() method for the "Buy" option
    @Test
    public void setNotificationStatusBuyCheck(){
        user.setNotificationStatus("Buy", true);
        assertEquals(true, user.getBuyNotificationStatus());
    }

    //  Checks the setNotificationStatus() method for the "Sell" option
    @Test
    public void setNotificationStatusSellCheck(){
        user.setNotificationStatus("Sell", true);
        assertEquals(true, user.getSellNotificationStatus());
    }
}
