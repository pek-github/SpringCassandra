package pek.hospital.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pek.hospital.domain.DoctorByPatient;
import pek.hospital.error.AppError;
import pek.hospital.error.AppException;
import pek.hospital.rest.dto.PatientAverageHeartRateDto;
import pek.hospital.service.PatientService;

@RestController
public class PatientController {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;
    private static final Logger LOG = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;


    @RequestMapping(value = "patients/{patientId}/doctors/",
                    method = RequestMethod.GET,
                    produces = "application/json; charset=UTF-8")
    public List<DoctorByPatient> findDoctorsTreatingPatient(@PathVariable String patientId) throws AppException {
        return patientService.findDoctorsTreatingPatient(patientId);
    }

    @RequestMapping(value = "patients/{patientId}/heartrates/date/{date}",
                    method = RequestMethod.GET,
                    produces = "application/json; charset=UTF-8")
    public PatientAverageHeartRateDto getPatientsAverageHeartRate(
            @PathVariable String patientId,
            @PathVariable String date,
            @RequestParam(name = "fromTime", required = false, defaultValue = "00:00") String fromTime,
            @RequestParam(name = "toTime", required = false, defaultValue = "23:59") String toTime) throws AppException {

        validateInputDates(date);
        validateInputTimes(fromTime, toTime);
        validateTimeOrder(fromTime, toTime);

        return patientService.getPatientsAverageHeartRate(patientId,
                                                          date,
                                                          LocalTime.parse(fromTime, TIME_FORMATTER),
                                                          LocalTime.parse(toTime, TIME_FORMATTER));
    }

    @RequestMapping(value = "patients/{patientId}/heartrates/dates/{from}/{to}",
                    method = RequestMethod.GET,
                    produces = "application/json; charset=UTF-8")
    public PatientAverageHeartRateDto getPatientsAverageHeartRate(@PathVariable String patientId,
                                                                  @PathVariable String from,
                                                                  @PathVariable String to) throws AppException {

        validateInputDates(from, to);
        validateDateOrder(from, to);

        return patientService.getPatientsAverageHeartRate(patientId, from, to);
    }


    private void validateInputDates(String... dates) throws AppException {
        for (String date : dates) {
            try {
                LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);
                LOG.debug("Parsed date parameter: {}", localDate);
            } catch (DateTimeParseException e) {
                String message = "Invalid 'date' parameter: " + date;
                LOG.error(message);
                throw new AppException(message, AppError.INVALID_PARAMETER);
            }
        }
    }

    private void validateInputTimes(String... times) throws AppException {
        for (String time : times) {
            try {
                LocalTime localTime = LocalTime.parse(time, TIME_FORMATTER);
                LOG.debug("Parsed date parameter: {}", localTime);
            } catch (DateTimeParseException e) {
                String message = "Invalid 'time' parameter: " + time;
                LOG.error(message);
                throw new AppException(message, AppError.INVALID_PARAMETER);
            }
        }
    }

    private void validateDateOrder(String first, String second) throws AppException {
        LocalDate firstDate = LocalDate.parse(first, DATE_FORMATTER);
        LocalDate secondDate = LocalDate.parse(second, DATE_FORMATTER);
        boolean ok = !firstDate.isAfter(secondDate);

        if (!ok) {
            String message = String.format("Second date [%s] should not be before first date [%s]", second, first);
            LOG.error(message);
            throw new AppException(message, AppError.INVALID_PARAMETER_COMBINATION);
        }
    }

    private void validateTimeOrder(String first, String second) throws AppException {
        LocalTime firstTime = LocalTime.parse(first, TIME_FORMATTER);
        LocalTime secondTime = LocalTime.parse(second, TIME_FORMATTER);
        boolean ok = !firstTime.isAfter(secondTime);

        if (!ok) {
            String message = String.format("Second time [%s] should not be before first time [%s]", second, first);
            LOG.error(message);
            throw new AppException(message, AppError.INVALID_PARAMETER_COMBINATION);
        }
    }

}
