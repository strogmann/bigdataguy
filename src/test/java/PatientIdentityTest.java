package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import org.junit.jupiter.api.Test;

class PatientIdentityTest {

    @Test
    void testIdentityCreation() {
        Name name = new Name("John", "Doe");
        Date dob = new Date(95, 5, 10); // Year offset from 1900 in Date
        PatientIdentity identity = new PatientIdentity(name, dob);
        assertEquals(name, identity.getName());
        assertEquals(dob, identity.getDateOfBirth());
    }

    @Test
    void testIdentityComparison() {
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("Jane", "Doe");
        Date dob1 = new Date(95, 5, 10);
        Date dob2 = new Date(90, 2, 15);

        PatientIdentity id1 = new PatientIdentity(name1, dob1);
        PatientIdentity id2 = new PatientIdentity(name1, dob1);
        PatientIdentity id3 = new PatientIdentity(name2, dob2);

        assertTrue(
            id1.equals(id2),
            "Identical patient identities should be equal"
        );
        assertFalse(
            id1.equals(id3),
            "Different patient identities should not be equal"
        );
    }
}
