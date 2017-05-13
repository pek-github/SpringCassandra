package pek.hospital.domain;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;


@Table("doctors_by_patient")
public class DoctorByPatient {

    @PrimaryKeyColumn(name = "patient_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String patientId;

    @PrimaryKeyColumn(name = "doctor_last_name", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String lastName;

    @PrimaryKeyColumn(name = "doctor_first_name", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String firstName;

    @PrimaryKeyColumn(name = "doctor_id", ordinal = 3, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private Integer doctorId;


    public DoctorByPatient() {
    }

    public DoctorByPatient(String patientId, String lastName, String firstName, Integer doctorId) {
        this.patientId = patientId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DoctorByPatient{");
        sb.append("patientId='").append(patientId).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", doctorId=").append(doctorId);
        sb.append('}');
        return sb.toString();
    }

}
