package pek.hospital.rest.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class PatientAverageHeartRateDto {

    private String patientId;
    private String patientLastName;
    private String patientFirstName;
    private String bedId;
    private Integer averageHeartRate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime from;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime to;


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

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public Integer getAverageHeartRate() {
        return averageHeartRate;
    }

    public void setAverageHeartRate(Integer averageHeartRate) {
        this.averageHeartRate = averageHeartRate;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatientAverageHeartRateDto{");
        sb.append("patientId='").append(patientId).append('\'');
        sb.append(", patientLastName='").append(patientLastName).append('\'');
        sb.append(", patientFirstName='").append(patientFirstName).append('\'');
        sb.append(", bedId='").append(bedId).append('\'');
        sb.append(", averageHeartRate=").append(averageHeartRate);
        sb.append(", from=").append(from);
        sb.append(", to=").append(to);
        sb.append('}');
        return sb.toString();
    }

}
