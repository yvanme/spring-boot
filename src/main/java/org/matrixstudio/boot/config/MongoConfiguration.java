package org.matrixstudio.boot.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootConfiguration
@EnableMongoRepositories("org.matrixstudio.boot.repository.mongodb")
public class MongoConfiguration {
}
