package pek.hospital.domain;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import com.datastax.driver.core.LocalDate;

@Table("heart_rates_by_patient_and_time")
public class HeartRate {

    @PrimaryKeyColumn(name = "patient_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String patientId;

    @PrimaryKeyColumn(name = "when_date", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDate whenDate;

    @PrimaryKeyColumn(name = "when_day_minutes", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Integer whenDayMinutes;

    @Column("patient_last_name")
    private String patientLastName;

    @Column("patient_first_name")
    private String patientFirstName;

    @Column("bed_id")
    private String bedId;

    @Column("heart_rate")
    private Integer heartRate;


    public HeartRate() {
    }

    public HeartRate(String patientId,
                     LocalDate whenDate,
                     Integer whenDayMinutes,
                     String patientLastName,
                     String patientFirstName,
                     String bedId,
                     Integer heartRate) {

        this.patientId = patientId;
        this.whenDate = whenDate;
        this.whenDayMinutes = whenDayMinutes;
        this.patientLastName = patientLastName;
        this.patientFirstName = patientFirstName;
        this.bedId = bedId;
        this.heartRate = heartRate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public LocalDate getWhenDate() {
        return whenDate;
    }

    public void setWhenDate(LocalDate whenDate) {
        this.whenDate = whenDate;
    }

    public Integer getWhenDayMinutes() {
        return whenDayMinutes;
    }

    public void setWhenDayMinutes(Integer whenDayMinutes) {
        this.whenDayMinutes = whenDayMinutes;
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

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HeartRate{");
        sb.append("patientId='").append(patientId).append('\'');
        sb.append(", whenDate=").append(whenDate);
        sb.append(", whenDayMinutes=").append(whenDayMinutes);
        sb.append(", patientLastName='").append(patientLastName).append('\'');
        sb.append(", patientFirstName='").append(patientFirstName).append('\'');
        sb.append(", bedId='").append(bedId).append('\'');
        sb.append(", heartRate=").append(heartRate);
        sb.append('}');
        return sb.toString();
    }

}
