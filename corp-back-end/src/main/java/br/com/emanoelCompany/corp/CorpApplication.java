package br.com.emanoelCompany.corp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "br.com.emanoelCompany.model")
public class CorpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorpApplication.class, args);
	}

}
