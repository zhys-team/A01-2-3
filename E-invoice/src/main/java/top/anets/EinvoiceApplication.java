package top.anets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan(basePackages = "top.anets.listen")
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class EinvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EinvoiceApplication.class, args);
	}

}
