package com.financial.kafka.mariadb.integration.kafkamariadbintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaMariaDBIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaMariaDBIntegrationApplication.class, args);
    }

/*	@Bean
    public CommandLineRunner test(LoanRepository repository) {
		return (s) -> {
			// save a couple of loan records
			repository.save(new LoanDataRecord(23.00,23.00, "test1", "test2", "test3", "test4", 44.00, "IL", "AA"));
			repository.save(new LoanDataRecord(24.50,26.60, "test6", "test2", "test3", "test4", 44.20, "IL", "BB"));
			repository.findByTerm("test1").forEach(System.out::print);
		};
	}*/
}