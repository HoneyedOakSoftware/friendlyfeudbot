package com.honeyedoaksoftware.friendlyfeudbot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.honeyedoaksoftware.friendlyfeudbot.repository.jpa")
//@EnableMongoRepositories(basePackages = "com.honeyedoaksoftware.friendlyfeudbot.repository.mongo")
public interface DatabaseConfig {
}
