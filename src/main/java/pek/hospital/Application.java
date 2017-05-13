package pek.hospital;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pek.hospital.db.Initializer;


@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final String BLANK_LINE = " ";
    private static final String DASH_LINE = createDashLine();
    private static final String KEYWORD_INIT = "init";
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired
    private Initializer initializer;


    private static String createDashLine() {
        return Collections.nCopies(80, "-").stream().collect(Collectors.joining());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        welcome();

        if (mustInitialize(args)) {
            LOG.info("Initialization starts");
            initializer.initialize();
            LOG.info("Initialization finished");
        }

        conclude();
    }


    private void welcome() {
        LOG.info(BLANK_LINE);
        LOG.info(DASH_LINE);
        LOG.info("Welcome to a toy 'hospital' application with Spring Boot and Spring Data Cassandra");
    }

    private boolean mustInitialize(String... args) {
        return Stream.of(args).filter(KEYWORD_INIT::equalsIgnoreCase).findFirst().isPresent();
    }

    private void conclude() {
        LOG.info(BLANK_LINE);
        LOG.info(DASH_LINE);
        LOG.info(BLANK_LINE);
    }

}
