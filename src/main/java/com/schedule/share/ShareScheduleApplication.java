package com.schedule.share;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShareScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareScheduleApplication.class, args);
	}

}
