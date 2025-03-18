package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    void testMatch() {
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("john", "doe");
        Name name3 = new Name("Jane", "Doe");

        assertTrue(name1.match(name2), "Names should match (case-insensitive)");
        assertFalse(name1.match(name3), "Different names should not match");
    }

    @Test
    void testIsLessThan() {
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("Jane", "Doe");
        Name name3 = new Name("Adam", "Smith");
        Name name4 = new Name("John", "Smith");
        Name name5 = new Name("John", "Adams");

        assertFalse(
            name1.isLessThan(name2),
            "John Doe should not be less than Jane Doe"
        );
        assertTrue(
            name3.isLessThan(name1),
            "Adam Smith should be less than John Doe"
        );
        assertTrue(
            name1.isLessThan(name4),
            "John Doe should be less than John Smith"
        );
        assertFalse(
            name4.isLessThan(name5),
            "John Smith should not be less than John Adams"
        );
    }

    @Test
    void testFromString() {
        Name name = Name.fromString("John Doe");
        assertEquals(
            "Doe, John",
            name.fullName(),
            "Should create name correctly"
        );
    }

    @Test
    void testInvalidFromString() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> {
                Name.fromString("John");
            }
        );
        assertEquals(
            "Invalid name format. Expected format: 'First Last'",
            exception.getMessage()
        );
    }
}
