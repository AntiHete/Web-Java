package com.WebJava.cats.api.config;

import com.WebJava.cats.api.repository.impl.NaturalIdRepositoryImpl;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class to set up JPA repositories and auditing.
 * Enables JPA repositories for the specified base package and custom repository base class.
 */
@Configuration
@EnableJpaAuditing  // Enables JPA auditing features such as @CreatedDate, @LastModifiedDate, etc.
@EnableJpaRepositories(
    basePackages = "com.WebJava.cats.api.repository", // Base package to scan for JPA repositories
    repositoryBaseClass = NaturalIdRepositoryImpl.class // Custom base repository implementation
)
public class JpaRepositoryConfiguration {
    // No additional beans or logic required in this configuration class
}
