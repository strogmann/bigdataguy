package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class PatientList {

    private Patient[] patients; // Made non-final to allow resizing during import
    private int size;
    private int indexOfIteration = -1; // -1 indicates no iteration in progress

    public PatientList() {
        this.patients = new Patient[1000];
        this.size = 0;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean add(Patient patient) {
        if (size >= patients.length) {
            // Resize the array if it's full
            patients = Arrays.copyOf(patients, patients.length * 2);
        }
        patients[size++] = patient;
        // We don't maintain sorted order on addition anymore
        return true;
    }

    public void sort() {
        Arrays.sort(
            patients,
            0,
            size,
            Comparator.comparing(p -> p.patientIdentity())
        );
    }

    public Patient find(PatientIdentity identity) {
        sort(); // Ensure the list is sorted before searching
        return binarySearch(identity);
    }

    private Patient binarySearch(PatientIdentity identity) {
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int comparison =
                patients[mid].patientIdentity().compareTo(identity);
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

    public void initIteration() {
        sort(); // Sort before iterating
        if (size > 0) {
            indexOfIteration = 0;
        } else {
            indexOfIteration = -1;
        }
    }

    public Patient next() {
        if (indexOfIteration == -1 || indexOfIteration >= size) {
            indexOfIteration = -1; // Ensure it's reset if it goes out of bounds
            return null;
        }

        Patient currentPatient = patients[indexOfIteration];
        indexOfIteration++;

        if (indexOfIteration >= size) {
            indexOfIteration = -1; // Reset after reaching the end
        }

        return currentPatient;
    }

    public boolean saveToFile(String filename) {
        try (
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename))
        ) {
            Patient[] sortedPatients = Arrays.copyOf(patients, size);
            mergeSort(sortedPatients, 0, sortedPatients.length - 1);

            for (Patient patient : sortedPatients) {
                writer.write(patient.toCSV());
                writer.newLine(); // Append newline after each patient
            }
            return true; // Success
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error occurred
        }
    }

    public boolean importFromFile(String filename) {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(filename))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Patient patient = Patient.fromCSV(line); // Create Patient from CSV
                    add(patient); // Add the patient to the list
                } catch (IllegalArgumentException e) {
                    // Skip invalid CSV lines
                    System.out.println("Skipping invalid line: " + line);
                }
            }
            return true; // Success
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error occurred
        } finally {
            sort(); // Sort the list after importing all patients
        }
    }

    private void mergeSort(Patient[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private void merge(Patient[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Patient[] L = new Patient[n1];
        Patient[] R = new Patient[n2];

        for (int i = 0; i < n1; ++i) L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[m + 1 + j];

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i].patientIdentity().compareTo(R[j].patientIdentity()) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    /*   public static void doUnitTests() {
        try {
            int testCount = 0, failCount = 0;
            System.out.println(
                "Running unit tests for PatientList with custom iterator and Merge Sort"
            );

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Name name1 = new Name("John", "Doe");
            Date dob1 = sdf.parse("1990-01-15");
            PatientIdentity pi1 = new PatientIdentity(name1, dob1);
            Patient patient1 = new Patient(pi1);

            Name name2 = new Name("Jane", "Smith");
            Date dob2 = sdf.parse("1985-05-20");
            PatientIdentity pi2 = new PatientIdentity(name2, dob2);
            Patient patient2 = new Patient(pi2);

            Name name3 = new Name("Alice", "Brown");
            Date dob3 = sdf.parse("1995-03-10");
            PatientIdentity pi3 = new PatientIdentity(name3, dob3);
            Patient patient3 = new Patient(pi3);

            PatientList patientList = new PatientList();
            patientList.add(patient2);
            patientList.add(patient1);
            patientList.add(patient3);
            testCount += 3;

            patientList.sort(); // Explicitly sort for testing purposes

            StringBuilder iteratorResults = new StringBuilder();
            patientList.initIteration();
            Patient current;
            while ((current = patientList.next()) != null) {
                iteratorResults.append(current.toString()).append("\n");
            }
            String expectedOrder =
                patient3 + "\n" + patient1 + "\n" + patient2 + "\n";
            if (!iteratorResults.toString().equals(expectedOrder)) {
                System.out.println(
                    "FAIL: Custom iterator did not return patients in expected sorted order"
                );
                failCount++;
            }
            testCount++;

            // Test find after sorting
            Patient foundPatient = patientList.find(pi1);
            if (
                foundPatient == null ||
                !foundPatient.patientIdentity().match(pi1)
            ) {
                System.out.println(
                    "FAIL: Finding patient1 failed after sorting"
                );
                failCount++;
            }
            testCount++;

            System.out.printf(
                "%d tests run, %d failed\n",
                testCount,
                failCount
            );
        } catch (ParseException e) {
            System.out.println("Date parsing failed: " + e.getMessage());
        }
        } */
}
