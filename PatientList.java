import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PatientList implements Iterable<Patient> {
    private final Patient[] patients;
    private int size;

    public PatientList() {
        this.patients = new Patient[1000];
        this.size = 0;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean add(Patient patient) {
        if (size >= patients.length) {
            return false;
        }
        // Insert in sorted order
        int index = findInsertIndex(patient.patientIdentity());
        for (int i = size; i > index; i--) {
            patients[i] = patients[i - 1]; // Shift elements to the right
        }
        patients[index] = patient;
        size++;
        return true;
    }

    public Patient find(PatientIdentity identity) {
        Patient foundPatient = binarySearch(identity);
        if (foundPatient == null) {
            // Fallback to linear search if binary search fails
            foundPatient = linearSearch(identity);
        }
        return foundPatient;
    }

    private int findInsertIndex(PatientIdentity identity) {
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int comparison = patients[mid].patientIdentity().compareTo(identity);
            if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    private Patient binarySearch(PatientIdentity identity) {
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int comparison = patients[mid].patientIdentity().compareTo(identity);
            if (comparison == 0) {
                return patients[mid];
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    private Patient linearSearch(PatientIdentity identity) {
        for (int i = 0; i < size; i++) {
            if (patients[i].patientIdentity().match(identity)) {
                return patients[i];
            }
        }
        return null;
    }

    @Override
    public Iterator<Patient> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public Patient next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return patients[currentIndex++];
            }
        };
    }

    public static void doUnitTests() {
        try {
            int testCount = 0, failCount = 0;
            System.out.println("Running unit tests for PatientList");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Name name1 = new Name("John", "Doe");
            Date dob1 = sdf.parse("1990-01-15");
            PatientIdentity pi1 = new PatientIdentity(name1, dob1);
            Patient patient1 = new Patient(pi1);

            Name name2 = new Name("Jane", "Smith");
            Date dob2 = sdf.parse("1985-05-20");
            PatientIdentity pi2 = new PatientIdentity(name2, dob2);
            Patient patient2 = new Patient(pi2);

            PatientList patientList = new PatientList();
            if (!patientList.add(patient1)) {
                System.out.println("FAIL: Adding patient1 failed");
                failCount++;
            }
            testCount++;

            if (!patientList.add(patient2)) {
                System.out.println("FAIL: Adding patient2 failed");
                failCount++;
            }
            testCount++;

            Patient foundPatient = patientList.find(pi1);
            if (foundPatient == null || !foundPatient.patientIdentity().match(pi1)) {
                System.out.println("FAIL: Finding patient1 failed");
                failCount++;
            }
            testCount++;

            foundPatient = patientList.find(pi2);
            if (foundPatient == null || !foundPatient.patientIdentity().match(pi2)) {
                System.out.println("FAIL: Finding patient2 failed");
                failCount++;
            }
            testCount++;

            StringBuilder iteratorResults = new StringBuilder();
            for (Patient patient : patientList) {
                iteratorResults.append(patient.toString()).append("\n");
            }
            String expectedOrder = patient1 + "\n" + patient2 + "\n";
            if (!iteratorResults.toString().equals(expectedOrder)) {
                System.out.println("FAIL: Iterator did not return patients in expected order");
                failCount++;
            }
            testCount++;

            System.out.printf("%d tests run, %d failed\n", testCount, failCount);
        } catch (ParseException e) {
            System.out.println("Date parsing failed: " + e.getMessage());
        }
    }
}