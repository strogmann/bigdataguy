import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {
        System.out.println("Running all unit tests");
        Name.doUnitTests();
        PatientIdentity.doUnitTests();
        Patient.doUnitTests();
    }
}
