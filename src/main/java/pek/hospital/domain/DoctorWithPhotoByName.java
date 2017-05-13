package pek.hospital.domain;

import java.nio.ByteBuffer;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import com.datastax.driver.core.utils.Bytes;


@Table("doctors_with_photos_by_name")
public class DoctorWithPhotoByName {

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

    @Column
    private ByteBuffer photo;


    public DoctorWithPhotoByName() {
    }

    public DoctorWithPhotoByName(String lastName,
                                 String firstName,
                                 Integer doctorId,
                                 String specialty,
                                 String title,
                                 ByteBuffer photo) {

        this.lastName = lastName;
        this.firstName = firstName;
        this.doctorId = doctorId;
        this.specialty = specialty;
        this.title = title;
        this.photo = photo;
    }

    public DoctorWithPhotoByName(DoctorByName doctorByName) {
        this.lastName = doctorByName.getLastName();
        this.firstName = doctorByName.getFirstName();
        this.doctorId = doctorByName.getDoctorId();
        this.specialty = doctorByName.getSpecialty();
        this.title = doctorByName.getTitle();
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

    public ByteBuffer getPhoto() {
        return photo;
    }

    public void setPhoto(ByteBuffer photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder("DoctorWithPhotoByName{");
        sb.append("lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", doctorId=").append(doctorId);
        sb.append(", specialty='").append(specialty).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", photo=").append(Bytes.toHexString(photo));
        sb.append('}');
        return sb.toString();
    }

}
