package com.monocept.ruleexecutor.containers;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

public class RuleExecutorPostgresContainer extends PostgreSQLContainer<RuleExecutorPostgresContainer> {

    private static final String IMAGE_VERSION = "postgres:12.7";
    private static RuleExecutorPostgresContainer container;

    public RuleExecutorPostgresContainer() {
        super(IMAGE_VERSION);
    }

    public static RuleExecutorPostgresContainer getInstance() {
        if (container == null) {
            container = new RuleExecutorPostgresContainer();
            container.start();
        }
        return container;
    }

    @Override
    public void start(){
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("POSTGRES_USER", container.getUsername());
        System.setProperty("POSTGRES_PASSWORD", container.getPassword());
        Location migrationLocation = new Location("filesystem:src/main/resources/db/migration/postgresql");
        ClassicConfiguration config = new ClassicConfiguration();
        config.setCleanDisabled(false);
        config.setDataSource(container.getJdbcUrl(), container.getUsername(), container.getPassword());
        config.setLocations(migrationLocation);
        Flyway newFlyway = new Flyway(config);
        newFlyway.clean();
        newFlyway.migrate();
    }

    @Override
    public void stop() {

    }
}
