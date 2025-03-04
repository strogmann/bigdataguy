import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientIdentity {

    private Name name;
    private Date dateOfBirth;

    public PatientIdentity(Name name, Date dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Name getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean match(PatientIdentity other) {
        return (
            this.name.match(other.name) &&
            this.dateOfBirth.equals(other.dateOfBirth)
        );
    }

    public boolean isLessThan(PatientIdentity other) {
        if (this.name.isLessThan(other.name)) {
            return true;
        } else if (this.name.match(other.name)) {
            return this.dateOfBirth.compareTo(other.dateOfBirth) < 0;
        } else {
            return false;
        }
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "name: " + name.toString() + " dob: " + sdf.format(dateOfBirth);
    }

    public static void doUnitTests() throws ParseException {
        int testCount = 0, failCount = 0;
        System.out.println("Running unit tests for PatientIdentity");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Basic tests
        Name name1 = new Name("John", "Doe");
        Date dob1 = sdf.parse("1990-01-15");
        PatientIdentity pi1 = new PatientIdentity(name1, dob1);

        Name name2 = new Name("john", "doe");
        Date dob2 = sdf.parse("1990-01-15");
        PatientIdentity pi2 = new PatientIdentity(name2, dob2);

        Name name3 = new Name("Jane", "Doe");
        Date dob3 = sdf.parse("1995-03-20");
        PatientIdentity pi3 = new PatientIdentity(name3, dob3);

        // Match tests
        if (!pi1.match(pi2)) {
            System.out.println("FAIL: PatientIdentity match failed (basic)");
            failCount++;
        }
        testCount++;

        if (pi1.match(pi3)) {
            System.out.println("Fail: PatientIdentity match should be false");
            failCount++;
        }
        testCount++;

        // isLessThan tests
        if (!pi3.isLessThan(pi1)) {
            System.out.println("Fail: isLessThan failed (basic)");
            failCount++;
        }
        testCount++;

        Date dob4 = sdf.parse("1990-01-16");
        PatientIdentity pi4 = new PatientIdentity(name1, dob4);

        if (!pi1.isLessThan(pi4)) {
            System.out.println(
                "Fail: isLessThan failed (same name, later date)"
            );
            failCount++;
        }
        testCount++;

        Date dob5 = sdf.parse("1990-01-14");
        PatientIdentity pi5 = new PatientIdentity(name1, dob5);

        if (pi1.isLessThan(pi5)) {
            System.out.println(
                "Fail: isLessThan failed (same name, earlier date)"
            );
            failCount++;
        }
        testCount++;

        System.out.printf("%d tests run, %d failed\n", testCount, failCount);
    }
}
