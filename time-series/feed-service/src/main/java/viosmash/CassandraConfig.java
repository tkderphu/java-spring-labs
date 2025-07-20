package viosmash;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import java.net.InetSocketAddress;

@Configuration
@EnableCassandraRepositories(basePackages = "com.example.repository") // Change this
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "new_feeds";  // Your keyspace
    }

    @Override
    protected String getLocalDataCenter() {
        return "datacenter1"; // Ensure this matches your Cassandra setup
    }

    @Override
    public String getContactPoints() {
        return "127.0.0.1"; // Cassandra host
    }

    @Override
    public int getPort() {
        return 9042; // Cassandra port
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Bean
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(getContactPoints());
        session.setPort(getPort());
        session.setLocalDatacenter(getLocalDataCenter());
        session.setKeyspaceName(getKeyspaceName());
        return session;
    }
}
