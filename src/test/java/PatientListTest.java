package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PatientListTest {

    @Test
    void testAddPatient() {
        PatientList patientList = new PatientList();
        Patient patient = new Patient(new PatientIdentity(new Name("John", "Doe"), new Date()));
        patientList.add(patient);

        assertTrue(
            patientList.contains(patient),
            "Patient should be in the list"
        );
    }

    @Test
    void testRemovePatient() {
        PatientList patientList = new PatientList();
        Patient patient = new Patient("Jane", "Smith", 25);
        patientList.add(patient);
        patientList.remove(patient);

        assertFalse(
            patientList.contains(patient),
            "Patient should be removed from the list"
        );
    }

    @Test
    void testFindPatientByName() {
        PatientList patientList = new PatientList();
        Patient patient = new Patient("Alice", "Johnson", 40);
        patientList.add(patient);

        assertEquals(
            patient,
            patientList.findByName("Alice", "Johnson"),
            "Should find the correct patient"
        );
    }
}
