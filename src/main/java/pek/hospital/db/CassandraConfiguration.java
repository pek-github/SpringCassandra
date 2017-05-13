package pek.hospital.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


@Configuration
@PropertySource({ "classpath:cassandra.properties" })
@EnableCassandraRepositories(basePackages = "org.pek.hospital.repository")
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    private static final String KEYSPACE = "keyspace.name";
    private static final String CONTACT_POINTS = "db.contact.points";
    private static final String PORT = "db.port";

    @Autowired
    private Environment environment;


    @Override
    protected String getKeyspaceName() {
        return environment.getProperty(KEYSPACE);
    }

    @Override
    protected String getContactPoints() {
        return environment.getProperty(CONTACT_POINTS);
    }

    @Override
    protected int getPort() {
        return environment.getProperty(PORT, Integer.class);
    }

}
