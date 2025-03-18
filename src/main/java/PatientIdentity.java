package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public record PatientIdentity(Name name, Date dateOfBirth)
    implements Comparable<PatientIdentity> {
    public boolean match(PatientIdentity other) {
        return (
            this.name.match(other.name) &&
            this.dateOfBirth.equals(other.dateOfBirth)
        );
    }

    @Override
    public int compareTo(PatientIdentity other) {
        int nameComparison = this.name.compareTo(other.name);
        if (nameComparison != 0) {
            return nameComparison;
        }
        return this.dateOfBirth.compareTo(other.dateOfBirth);
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

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return name.toString() + "," + sdf.format(dateOfBirth); // Adjust as necessary
    }

    public static PatientIdentity fromCSV(String csv) {
        String[] parts = csv.split(",");
        Name name = Name.fromString(parts[0]); // Assuming Name has a fromString method
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = null;
        try {
            dob = sdf.parse(parts[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new PatientIdentity(name, dob);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "name: " + name.toString() + " dob: " + sdf.format(dateOfBirth);
    }
}
