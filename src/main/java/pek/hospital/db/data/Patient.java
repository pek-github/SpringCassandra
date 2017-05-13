package pek.hospital.db.data;

public class Patient {

    private String patientId;
    private String patientLastName;
    private String patientFirstName;

    public Patient() {
    }

    public Patient(String patientId, String patientLastName, String patientFirstName) {
        this.patientId = patientId;
        this.patientLastName = patientLastName;
        this.patientFirstName = patientFirstName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Patient{");
        sb.append("patientId='").append(patientId).append('\'');
        sb.append(", patientLastName='").append(patientLastName).append('\'');
        sb.append(", patientFirstName='").append(patientFirstName).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
