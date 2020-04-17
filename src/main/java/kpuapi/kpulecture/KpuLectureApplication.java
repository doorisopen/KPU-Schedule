package kpuapi.kpulecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@SpringBootApplication
public class KpuLectureApplication {

	public static void main(String[] args) {
		SpringApplication.run(KpuLectureApplication.class, args);
	}

}
