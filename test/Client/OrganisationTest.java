package Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrganisationTest {

    Organisation org;

    @BeforeEach
    @Test
    public void setUpOrganisation() {
        org = new Organisation("Microsoft", 200, "Computational Resource", 30);
    }

    @Test
    public void organisationCheck(){
        assertEquals(200, org.getCredits(),
                "Adding organisation failed");
    }

    @Test
    public void setCreditsCheck(){
        org.setCredits(210);
        assertEquals(210, org.getCredits(),
                "Setting credits failed");
    }




}
