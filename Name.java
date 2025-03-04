public class Name {

    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String fullname() {
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

    public String toString() {
        return fullname();
    }

    public static void doUnitTests() {
        int testCount = 0, failCount = 0;
        System.out.println("Running unit tests for Name");

        // Basic tests
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("john", "doe");
        Name name3 = new Name("Jane", "Doe");
        Name name4 = new Name("Adam", "Smith");
        Name name5 = new Name("John", "Smith");
        Name name6 = new Name("John", "Adams");

        // Match tests
        if (!name1.match(name2)) {
            System.out.println("Fail: Case-insensitive match failed (basic)");
            failCount++;
        }
        testCount++;

        if (name1.match(name3)) {
            System.out.println("Fail: Match should be false");
            failCount++;
        }
        testCount++;

        // isLessThan tests
        if (name1.isLessThan(name3)) {
            System.out.println("Fail: isLessThan failed (basic)");
            failCount++;
        }
        testCount++;

        if (name4.isLessThan(name1)) {
            System.out.println("Fail: isLessThan failed (Adam vs John)");
            failCount++;
        }
        testCount++;

        if (!name1.isLessThan(name5)) {
            System.out.println(
                "Fail: isLessThan failed (John Doe vs John Smith)"
            );
            failCount++;
        }
        testCount++;

        if (name5.isLessThan(name6)) {
            System.out.println(
                "Fail: isLessThan failed (John Smith vs John Adams)"
            );
            failCount++;
        }
        testCount++;

        // Boundary/Edge Cases
        Name emptyName = new Name("", "");
        Name spaceName = new Name(" ", " ");
        Name specialCharName = new Name("John!", "Doe-Smith");

        if (!emptyName.match(new Name("", ""))) {
            System.out.println("Fail: Empty name match failed");
            failCount++;
        }
        testCount++;

        if (!spaceName.match(new Name(" ", " "))) {
            System.out.println("Fail: space name match failed");
            failCount++;
        }
        testCount++;

        if (!specialCharName.match(new Name("john!", "doe-smith"))) {
            System.out.println("Fail: special character match failed");
            failCount++;
        }
        testCount++;

        System.out.printf("%d tests run, %d failed\n", testCount, failCount);
    }
}
