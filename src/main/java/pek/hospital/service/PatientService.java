package pek.hospital.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.Row;

import pek.hospital.domain.DoctorByPatient;
import pek.hospital.error.AppError;
import pek.hospital.error.ResourceNotFoundException;
import pek.hospital.repository.DoctorByPatientRepository;
import pek.hospital.repository.HeartRateRepository;
import pek.hospital.rest.dto.PatientAverageHeartRateDto;


@Service
public class PatientService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final Logger LOG = LoggerFactory.getLogger(PatientService.class);

    private static final Function<LocalTime, Integer> MINUTES_OF_DAY_CONVERTER
        = time -> time.getHour() * 60 + time.getMinute();


    @Autowired
    private DoctorByPatientRepository doctorByPatientRepository;

    @Autowired
    private HeartRateRepository heartRateRepository;


    public List<DoctorByPatient> findDoctorsTreatingPatient(String patientId) throws ResourceNotFoundException {

        List<DoctorByPatient> doctors = doctorByPatientRepository.findByPatientId(patientId);

        if (doctors.isEmpty()) {
            String message = String.format("No doctors were found treating patient with id '%s'", patientId);
            LOG.debug(message);
            throw new ResourceNotFoundException(message, AppError.REQUESTED_RESOURCE_DOES_NOT_EXIST);
        }

        return doctors;
    }

    public PatientAverageHeartRateDto getPatientsAverageHeartRate(String patientId,
                                                                  String date,
                                                                  LocalTime fromTime,
                                                                  LocalTime toTime) throws ResourceNotFoundException {

        LocalDate when = parseDate(date);
        Row avgHeartRate = heartRateRepository.getPatientsAverageHeartRate(patientId,
                                                                           when,
                                                                           MINUTES_OF_DAY_CONVERTER.apply(fromTime),
                                                                           MINUTES_OF_DAY_CONVERTER.apply(toTime));

        LOG.debug("Retrieved response from database: {}", avgHeartRate);

        if (avgHeartRate.isNull(0)) {
            String msg = "No heart rate records were found for patient with id '%s' for date '%s' and time between '%s' and '%s'";
            String message = String.format(msg, patientId, date, fromTime, toTime);
            throw new ResourceNotFoundException(message, AppError.REQUESTED_RESOURCE_DOES_NOT_EXIST);
        }

        PatientAverageHeartRateDto patientAverageHeartRateDto = convertDbRow(avgHeartRate);
        return enrichWithDates(patientAverageHeartRateDto, date, fromTime, toTime);
    }

    public PatientAverageHeartRateDto getPatientsAverageHeartRate(String patientId,
                                                                  String fromDate,
                                                                  String toDate) throws ResourceNotFoundException {

        LocalDate from = parseDate(fromDate);
        LocalDate to = parseDate(toDate);
        Row avgHeartRate = heartRateRepository.getPatientsAverageHeartRate(patientId, from, to);

        LOG.debug("Retrieved response from database: {}", avgHeartRate);

        if (avgHeartRate.isNull(0)) {
            String msg = "No heart rate records were found for patient with id '%s' for dates between '%s' and '%s'";
            String message = String.format(msg, patientId, fromDate, toDate);
            throw new ResourceNotFoundException(message, AppError.REQUESTED_RESOURCE_DOES_NOT_EXIST);
        }

        PatientAverageHeartRateDto patientAverageHeartRateDto = convertDbRow(avgHeartRate);
        return enrichWithDates(patientAverageHeartRateDto, fromDate, toDate);
    }


    private LocalDate parseDate(String date) {
            java.time.LocalDate localDate = java.time.LocalDate.parse(date, DATE_FORMATTER);
            int year = localDate.getYear();
            int month = localDate.getMonthValue();
            int dayOfMonth = localDate.getDayOfMonth();
            return LocalDate.fromYearMonthDay(year, month, dayOfMonth);
    }

    private PatientAverageHeartRateDto convertDbRow(Row row) {
        PatientAverageHeartRateDto patientAverageHeartRateDto = new PatientAverageHeartRateDto();
        patientAverageHeartRateDto.setPatientId(row.getString("patient_id"));
        patientAverageHeartRateDto.setPatientLastName(row.getString("patient_last_name"));
        patientAverageHeartRateDto.setPatientFirstName(row.getString("patient_first_name"));
        patientAverageHeartRateDto.setBedId(row.getString("bed_id"));
        patientAverageHeartRateDto.setAverageHeartRate(row.getInt("avg_hr"));
        return patientAverageHeartRateDto;
    }

    private PatientAverageHeartRateDto enrichWithDates(PatientAverageHeartRateDto patientAverageHeartRateDto,
                                                       String date,
                                                       LocalTime fromTime,
                                                       LocalTime toTime) {

        java.time.LocalDate localDate = java.time.LocalDate.parse(date, DATE_FORMATTER);
        LocalDateTime from = LocalDateTime.of(localDate, fromTime);
        LocalDateTime to = LocalDateTime.of(localDate, toTime);

        return populateWithDates(patientAverageHeartRateDto, from, to);
    }

    private PatientAverageHeartRateDto enrichWithDates(PatientAverageHeartRateDto patientAverageHeartRateDto,
                                                       String fromDate,
                                                       String toDate) {

        LocalDateTime from = LocalDateTime.parse(fromDate + "T00:00:00", DATE_TIME_FORMATTER);
        LocalDateTime to = LocalDateTime.parse(toDate + "T23:59:00", DATE_TIME_FORMATTER);
        return populateWithDates(patientAverageHeartRateDto, from, to);
    }

    private PatientAverageHeartRateDto populateWithDates(PatientAverageHeartRateDto patientAverageHeartRateDto,
                                                         LocalDateTime from,
                                                         LocalDateTime to) {

        patientAverageHeartRateDto.setFrom(from);
        patientAverageHeartRateDto.setTo(to);
        LOG.debug("Computed PatientAverageHeartRateDto: {}", patientAverageHeartRateDto);
        return patientAverageHeartRateDto;
    }

}
