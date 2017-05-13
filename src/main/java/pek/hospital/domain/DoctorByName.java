package pek.hospital.domain;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;


@Table("doctors_by_name")
public class DoctorByName {

    @PrimaryKeyColumn(name = "last_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String lastName;

    @PrimaryKeyColumn(name = "first_name", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String firstName;

    @PrimaryKeyColumn(name = "doctor_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private Integer doctorId;

    @Column
    private String specialty;

    @Column
    private String title;


    public DoctorByName() {
    }

    public DoctorByName(String lastName, String firstName, Integer doctorId, String specialty, String title) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.doctorId = doctorId;
        this.specialty = specialty;
        this.title = title;
    }

    public DoctorByName(DoctorWithPhotoByName doctorWithPhotoByName) {
        this.lastName = doctorWithPhotoByName.getLastName();
        this.firstName = doctorWithPhotoByName.getFirstName();
        this.doctorId = doctorWithPhotoByName.getDoctorId();
        this.specialty = doctorWithPhotoByName.getSpecialty();
        this.title = doctorWithPhotoByName.getTitle();
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DoctorByName{");
        sb.append("lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", doctorId=").append(doctorId);
        sb.append(", specialty='").append(specialty).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
