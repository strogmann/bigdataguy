package main;

public record Patient(PatientIdentity patientIdentity) {
    @Override
    public String toString() {
        return "identity: " + patientIdentity.toString();
    }

    public String toCSV() {
        return patientIdentity.toCSV(); // Assuming PatientIdentity has a toCSV method
    }

    public static Patient fromCSV(String csv) {
        PatientIdentity identity = PatientIdentity.fromCSV(csv); // Assuming PatientIdentity has a fromCSV method
        return new Patient(identity);
    }
}
