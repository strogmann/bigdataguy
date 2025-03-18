package main;

public class Name implements Comparable<Name> {

    private final String firstName;
    private final String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String fullName() {
        return lastName + ", " + firstName;
    }

    public boolean match(Name other) {
        return (
            this.firstName.equalsIgnoreCase(other.firstName) &&
            this.lastName.equalsIgnoreCase(other.lastName)
        );
    }

    public boolean isLessThan(Name other) {
        int lastNameComparison =
            this.lastName.toLowerCase().compareTo(other.lastName.toLowerCase());
        if (lastNameComparison != 0) {
            return lastNameComparison < 0;
        } else {
            return (
                this.firstName.toLowerCase()
                    .compareTo(other.firstName.toLowerCase()) <
                0
            );
        }
    }

    @Override
    public int compareTo(Name other) {
        int lastNameComparison =
            this.lastName.compareToIgnoreCase(other.lastName);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }
        return this.firstName.compareToIgnoreCase(other.firstName);
    }

    @Override
    public String toString() {
        return fullName();
    }

    public static Name fromString(String nameString) {
        String[] parts = nameString.split(" ");
        if (parts.length < 2) {
            throw new IllegalArgumentException(
                "Invalid name format. Expected format: 'First Last'"
            );
        }
        String firstName = parts[0];
        String lastName = parts[1];
        return new Name(firstName, lastName);
    }
}
