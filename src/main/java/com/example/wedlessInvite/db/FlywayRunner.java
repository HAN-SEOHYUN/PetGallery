package com.example.wedlessInvite.db;

import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("entityManagerFactory")
public class FlywayRunner {
    private final Flyway flyway;

    @Autowired
    public FlywayRunner(Flyway flyway) {
        this.flyway = flyway;
    }

    @PostConstruct
    public void runMigrations() {
        System.out.println("FLYWAY 시작...");
        flyway.baseline();
        flyway.migrate();
    }
}