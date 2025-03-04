import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Patient {

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
