package pek.hospital.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.keyspace.CreateTableSpecification;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.DataType;


@Component
public class Initializer {

    private static final String TABLE_DOCTORS = "doctors_by_name";
    private static final String TABLE_DOCTORS_WITH_PHOTOS = "doctors_with_photos_by_name";
    private static final String TABLE_DOCTORS_BY_PATIENT = "doctors_by_patient";
    private static final String TABLE_HEART_RATE = "heart_rates_by_patient_and_time";

    private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

    @Autowired
    private CassandraOperations cassandraTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private DataInitializer dataInitializer;


    // prerequisite: Keyspace, due to bean: CassandraConfiguration
    public void initialize() {
        createTables();
        dataInitializer.populateTables();
    }

    private void createTables() {
        createDoctorsTables();
        createPatientsTable();
        createHeartRatesTable();
    }

    private void createDoctorsTables() {
        LOG.debug("Creating table: {}", TABLE_DOCTORS);
        CreateTableSpecification createTableSpec1
            = CreateTableSpecification.createTable()
                                      .ifNotExists()
                                      .name(TABLE_DOCTORS)
                                      .partitionKeyColumn("last_name", DataType.text())
                                      .partitionKeyColumn("first_name", DataType.text())
                                      .clusteredKeyColumn("doctor_id", DataType.cint(), Ordering.ASCENDING)
                                      .column("specialty", DataType.text())
                                      .column("title", DataType.text());

        cassandraTemplate.execute(createTableSpec1);
        LOG.debug("Created table: {}", TABLE_DOCTORS);

        LOG.debug("Creating table: {}", TABLE_DOCTORS_WITH_PHOTOS);
        CreateTableSpecification createTableSpec2
            = CreateTableSpecification.createTable()
                                      .ifNotExists()
                                      .name(TABLE_DOCTORS_WITH_PHOTOS)
                                      .partitionKeyColumn("last_name", DataType.text())
                                      .partitionKeyColumn("first_name", DataType.text())
                                      .clusteredKeyColumn("doctor_id", DataType.cint(), Ordering.ASCENDING)
                                      .column("specialty", DataType.text())
                                      .column("title", DataType.text())
                                      .column("photo", DataType.blob());

        cassandraTemplate.execute(createTableSpec2);
        LOG.debug("Created table: {}", TABLE_DOCTORS_WITH_PHOTOS);
    }

    private void createPatientsTable() {
        LOG.debug("Creating table: {}", TABLE_DOCTORS_BY_PATIENT);

        CreateTableSpecification createTableSpec
            = CreateTableSpecification.createTable()
                                      .ifNotExists()
                                      .name(TABLE_DOCTORS_BY_PATIENT)
                                      .partitionKeyColumn("patient_id", DataType.text())
                                      .clusteredKeyColumn("doctor_last_name", DataType.text(), Ordering.ASCENDING)
                                      .clusteredKeyColumn("doctor_first_name", DataType.text(), Ordering.ASCENDING)
                                      .clusteredKeyColumn("doctor_id", DataType.cint(), Ordering.ASCENDING);

        cassandraTemplate.execute(createTableSpec);
        LOG.debug("Created table: {}", TABLE_DOCTORS_BY_PATIENT);
    }

    private void createHeartRatesTable() {
        LOG.debug("Creating table: {}", TABLE_HEART_RATE);

        CreateTableSpecification createTableSpec
            = CreateTableSpecification.createTable()
                                      .ifNotExists()
                                      .name(TABLE_HEART_RATE)
                                      .partitionKeyColumn("patient_id", DataType.text())
                                      .clusteredKeyColumn("when_date", DataType.date(), Ordering.DESCENDING)
                                      .clusteredKeyColumn("when_day_minutes", DataType.cint(), Ordering.DESCENDING)
                                      .column("patient_last_name", DataType.text()) // static
                                      .column("patient_first_name", DataType.text()) // static
                                      .column("bed_id", DataType.text())
                                      .column("heart_rate", DataType.cint());

        cassandraTemplate.execute(createTableSpec);
        LOG.debug("Created table: {}", TABLE_HEART_RATE);
    }

}

