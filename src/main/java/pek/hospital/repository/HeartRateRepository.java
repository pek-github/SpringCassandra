package pek.hospital.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.Row;

import pek.hospital.domain.HeartRate;

@Repository
public interface HeartRateRepository extends CassandraRepository<HeartRate> {

    @Query("SELECT patient_id, patient_last_name, patient_first_name, bed_id, avg(heart_rate) AS avg_hr " +
           "FROM hospital.heart_rates_by_patient_and_time " +
           "WHERE patient_id = :patientId " +
           "AND when_date = :date " +
           "AND when_day_minutes >= :fromTime " +
           "AND when_day_minutes <= :toTime")
    Row getPatientsAverageHeartRate(@Param("patientId") String patientId,
                                    @Param("date") LocalDate date,
                                    @Param("fromTime") Integer fromTime,
                                    @Param("toTime") Integer toTime);

    @Query("SELECT patient_id, patient_last_name, patient_first_name, bed_id, avg(heart_rate) AS avg_hr " +
           "FROM hospital.heart_rates_by_patient_and_time " +
           "WHERE patient_id = :patientId " +
           "AND when_date >= :fromDate " +
           "AND when_date <= :toDate")
    Row getPatientsAverageHeartRate(@Param("patientId") String patientId,
                                    @Param("fromDate") LocalDate fromDate,
                                    @Param("toDate") LocalDate toDate);

}
