package pek.hospital.db;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import pek.hospital.domain.DoctorByName;
import pek.hospital.domain.DoctorByPatient;
import pek.hospital.domain.DoctorWithPhotoByName;
import pek.hospital.domain.HeartRate;
import pek.hospital.db.data.HeartRateExtremes;
import pek.hospital.db.data.Interval;
import pek.hospital.db.data.Patient;
import pek.hospital.repository.DoctorByNameRepository;
import pek.hospital.repository.DoctorByPatientRepository;
import pek.hospital.repository.DoctorWithPhotoByNameRepository;
import pek.hospital.repository.HeartRateRepository;
import pek.hospital.db.data.HeartRateGenerator;


@Component
public class DataInitializer {

    private static final String TABLE_DOCTORS = "doctors_by_name";
    private static final String TABLE_DOCTORS_WITH_PHOTOS = "doctors_with_photos_by_name";
    private static final String TABLE_DOCTORS_BY_PATIENT = "doctors_by_patient";
    private static final String TABLE_HEART_RATE = "heart_rates_by_patient_and_time";

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private CassandraOperations cassandraTemplate;

    @Autowired
    private DoctorByNameRepository doctorByNameRepository;

    @Autowired
    private DoctorWithPhotoByNameRepository doctorWithPhotoByNameRepository;

    @Autowired
    private DoctorByPatientRepository doctorByPatientRepository;

    @Autowired
    private HeartRateRepository heartRateRepository;

    @Autowired
    private HeartRateGenerator heartRateGenerator;


    public void populateTables() {
        populateDoctorsTables();
        populatePatientsTable();
        populateHeartRatesTable();
    }

    private void populateDoctorsTables() {
        LOG.debug("Populating tables: {}, {}", TABLE_DOCTORS, TABLE_DOCTORS_WITH_PHOTOS);

        doctorByNameRepository.deleteAll();
        doctorWithPhotoByNameRepository.deleteAll();

        Function<DoctorWithPhotoByName, Insert> insertToTableDoctorsWithPhotos
            = doc -> QueryBuilder.insertInto(TABLE_DOCTORS_WITH_PHOTOS)
                                 .value("last_name", doc.getLastName())
                                 .value("first_name", doc.getFirstName())
                                 .value("doctor_id", doc.getDoctorId())
                                 .value("specialty", doc.getSpecialty())
                                 .value("title", doc.getTitle())
                                 .value("photo", doc.getPhoto());

        Function<DoctorByName, Insert> insertToTableDoctors
            = doc -> QueryBuilder.insertInto(TABLE_DOCTORS)
                                 .value("last_name", doc.getLastName())
                                 .value("first_name", doc.getFirstName())
                                 .value("doctor_id", doc.getDoctorId())
                                 .value("specialty", doc.getSpecialty())
                                 .value("title", doc.getTitle());

        Function<DoctorWithPhotoByName, DoctorByName> docWithoutPhoto = DoctorByName::new;

        for (DoctorWithPhotoByName doctorWithPhotoByName : doctorsWithPhotoByName()) {
            Insert insert1 = insertToTableDoctorsWithPhotos.apply(doctorWithPhotoByName);
            Insert insert2 = docWithoutPhoto.andThen(insertToTableDoctors).apply(doctorWithPhotoByName);
            Batch batch = QueryBuilder.batch(insert1, insert2);
            cassandraTemplate.execute(batch);
        }

        LOG.debug("Populated tables: {}, {}", TABLE_DOCTORS, TABLE_DOCTORS_WITH_PHOTOS);
    }

    List<DoctorWithPhotoByName> doctorsWithPhotoByName() {

        DoctorWithPhotoByName[] doctorWithPhotoByName = {
            new DoctorWithPhotoByName("smith", "john", 1201, "microbiologist", "doc", ByteBuffer.wrap(new byte[]{ 0x0A })),
            new DoctorWithPhotoByName("smith", "john", 1101, "cardiologist", "trainee doc", ByteBuffer.wrap(new byte[]{ 0x01 })),
            new DoctorWithPhotoByName("house", "greg", 1011, "diagnostician", "head doc", ByteBuffer.wrap(new byte[]{ 0x25 })),
            new DoctorWithPhotoByName("hadley", "remy", 1013, "pathologist", "doc", ByteBuffer.wrap(new byte[]{ 0x13 })),
            new DoctorWithPhotoByName("johnson", "mary", 1185, "pediatrician", "senior doc", ByteBuffer.wrap(new byte[]{ 0x0D }))
        };

        return Arrays.asList(doctorWithPhotoByName);
    }

    private void populatePatientsTable() {
        LOG.debug("Populating table: {}", TABLE_DOCTORS_BY_PATIENT);
        doctorByPatientRepository.deleteAll();

        DoctorByPatient[] doctorsByPatients = {
            new DoctorByPatient("12111988K45", "smith", "john", 1101),
            new DoctorByPatient("12111988K45", "smith", "john", 1201),
            new DoctorByPatient("12111988K45", "house", "greg", 1011),
            new DoctorByPatient("25061984KP52", "hadley", "remy", 1013)
        };

        List<DoctorByPatient> docsByPatients = Arrays.asList(doctorsByPatients);
        doctorByPatientRepository.save(docsByPatients);
        LOG.debug("Populated table: {}", TABLE_DOCTORS_BY_PATIENT);
    }

    private void populateHeartRatesTable() {
        LOG.debug("Populating table: {}", TABLE_HEART_RATE);
        populateAlex();
        populateJane();
        populateJordan();
        LOG.debug("Populated table: {}", TABLE_HEART_RATE);
    }

    private void populateAlex() {
        Patient patient = new Patient("12111988K45", "summers", "alex");
        String bed = "E45";
        HeartRateExtremes heartRateExtremes = new HeartRateExtremes(60, 140);
        Interval interval
            = new Interval(Instant.parse("2016-04-25T00:00:02Z"), Instant.parse("2016-04-26T00:00:04Z"), 1);

        populate(patient, bed, heartRateExtremes, interval);
    }

    private void populateJane() {
        Patient patient = new Patient("17101991S49", "rain", "jane");
        String bed = "G45";
        HeartRateExtremes heartRateExtremes = new HeartRateExtremes(70, 120);
        Interval interval
            = new Interval(Instant.parse("2016-04-24T00:00:02Z"), Instant.parse("2016-04-26T00:00:04Z"), 5);

        populate(patient, bed, heartRateExtremes, interval);
    }

    private void populateJordan() {
        Patient patient = new Patient("02041985S49", "flow", "jordan");
        String bed = "D28";
        HeartRateExtremes heartRateExtremes = new HeartRateExtremes(30, 70);
        Interval interval
            = new Interval(Instant.parse("2016-04-24T00:00:02Z"), Instant.parse("2016-04-27T00:00:09Z"), 60);

        populate(patient, bed, heartRateExtremes, interval);
    }

    private void populate(final Patient patient, final String bed, final HeartRateExtremes heartRateExtremes, final Interval interval) {
        List<HeartRate> heartRates = heartRateGenerator.generate(patient, bed, heartRateExtremes, interval);
        heartRateRepository.save(heartRates);
    }

}
