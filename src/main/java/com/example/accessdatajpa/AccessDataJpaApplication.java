package com.example.accessdatajpa;

import com.example.accessdatajpa.Repository.CustomerRepository;
import com.example.accessdatajpa.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class AccessDataJpaApplication {

	private static final Logger log = LoggerFactory.getLogger(AccessDataJpaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccessDataJpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			Optional<Customer> customer = repository.findById(1L);
			log.info("Customer found with findById(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			repository.findAll().forEach(bauer -> log.info(bauer.toString()));

			log.info("");
		};
	}
}
