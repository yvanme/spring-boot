package org.matrixstudio.boot.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EnableJpaRepositories("org.matrixstudio.boot.repository.jpa")
public class JpaConfiguration {
}
