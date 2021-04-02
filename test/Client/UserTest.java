package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    User user1;


    @BeforeEach
    @Test
    public void setUpUser() {
        user1 = new User("Tom", "ab23", "non-admin", "Microsoft");
    }

    @Test
    public void usernameCheck(){
        assertEquals("Tom", user1.getUsername(),
                "Adding user failed");
    }

    @Test
    public void hashPasswordTest(){
        String pwd = "ab23";
        assertEquals(pwd.hashCode() * 2.334 + "", user1.getHashedPassword(),
                "Hashing the password failed");
    }
}
