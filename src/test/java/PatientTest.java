package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PatientTest {

    @Test
    void testPatientCreation() {
        Patient patient = new Patient(new PatientIdentity(new Name("John", "Doe"), new Date()));
        assertEquals("John", patient.getFirstName());
        assertEquals("Doe", patient.getLastName());
        assertEquals(30, patient.getAge());
    }

    @Test
    void testFullName() {
        Patient patient = new Patient("Jane", "Smith", 25);
        assertEquals("Smith, Jane", patient.fullName());
    }

    @Test
    void testAgeUpdate() {
        Patient patient = new Patient(new PatientIdentity(new Name("John", "Doe"), new Date()));
        patient.setAge(35);
        assertEquals(35, patient.getAge());
    }
}
