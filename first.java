import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Name {

    // Variables for first and last names
    private String firstName;
    private String lastName;

    // Treating combination of first and last as a singular object
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Returns name object as first, last
    public String fullname() {
        return lastName + ", " + firstName;
    }

    // Checking to see if name object matches with another
    public boolean match(Name other) {
        return (
            this.firstName.toLowerCase()
                .equals(other.firstName.toLowerCase()) &&
            this.lastName.toLowerCase().equals(other.lastName.toLowerCase())
        );
    }

    // Sorts name object lexically
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

    // Unit tests
    public static void doUnitTests() {
        int testCount = 0, failCount = 0;
        System.out.println("Running unit tests for Name");

        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("john", "doe");
        Name name3 = new Name("Jane", "Doe");

        // Checking to see if sorting is case insensitive
        if (!name1.match(name2)) {
            System.out.println("Fail: Case-insensitive match failed");
            failCount++;
        }
        testCount++;

        if (name1.isLessThan(name3)) {
            System.out.println("Fail: isLessThan failed");
            failCount++;
        }
        testCount++;

        System.out.printf("%d tests run, %d failed\n", testCount, failCount);
    }
}

// PatientIdentity Class
class PatientIdentity {

    private Name name;
    private Date dateOfBirth;

    // class for whole patientID
    public PatientIdentity(Name name, Date dateOfBirth) {
        // classifying name and DOB as current one being checked for later operations :)
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    // Gets name
    public Name getName() {
        return name;
    }

    // Gets DOB
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    // Checks if patientID is the same as another
    public boolean match(PatientIdentity other) {
        return (
            this.name.match(other.name) &&
            this.dateOfBirth.equals(other.dateOfBirth)
        );
    }

    // Sorts whole patientID lexically first, then numerically if needed
    public boolean isLessThan(PatientIdentity other) {
        if (this.name.isLessThan(other.name)) {
            return true;
        } else if (this.name.match(other.name)) { // Names are equal
            return this.dateOfBirth.compareTo(other.dateOfBirth) < 0;
        } else {
            return false;
        }
    }

    // Converts date to a usable consistent format
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "name: " + name.toString() + " dob: " + sdf.format(dateOfBirth);
    }

    // Tests for sorting patientIDs
    public static void doUnitTests() throws ParseException {
        int testCount = 0, failCount = 0;
        System.out.println("Running unit tests for PatientIdentity");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Name name1 = new Name("John", "Doe");
        Date dob1 = sdf.parse("1990-01-15");
        PatientIdentity pi1 = new PatientIdentity(name1, dob1);

        Name name2 = new Name("john", "doe");
        Date dob2 = sdf.parse("1990-01-15");
        PatientIdentity pi2 = new PatientIdentity(name2, dob2);

        if (!pi1.match(pi2)) {
            System.out.println("FAIL: PatientIdentity match failed");
            failCount++;
        }
        testCount++;

        System.out.printf("%d tests run, %d failed\n", testCount, failCount);
    }
}

// Associates patientID with the Patient as a whole to be handled later
class Patient {

    private PatientIdentity patientIdentity;

    public Patient(PatientIdentity patientIdentity) {
        this.patientIdentity = patientIdentity;
    }

    public PatientIdentity getPatientIdentity() {
        return patientIdentity;
    }

    public String toString() {
        return "identity: " + patientIdentity.toString();
    }

    // Tests patientID fetching
    public static void doUnitTests() throws ParseException {
        int testCount = 0, failCount = 0;
        System.out.println("Running unit tests for Patient");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Name name = new Name("John", "Doe");
        Date dob = sdf.parse("1990-01-15");
        PatientIdentity pi = new PatientIdentity(name, dob);
        Patient patient = new Patient(pi);

        if (!patient.getPatientIdentity().match(pi)) {
            System.out.println("FAIL: Patient getPatientIdentity failed");
            failCount++;
        }
        testCount++;

        System.out.printf("%d tests run, %d failed\n", testCount, failCount);
    }
}

public class first {

    public static void main(String[] args) throws ParseException {
        System.out.println("Running all unit tests");
        Name.doUnitTests();
        PatientIdentity.doUnitTests();
        Patient.doUnitTests();
    }
}
