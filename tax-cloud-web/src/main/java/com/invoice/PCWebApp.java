package com.invoice;

import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
//@EnableCircuitBreaker
@EnableHystrix
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages= {"com.zhys","com.invoice"})
public class PCWebApp {

	 public static void main(String[] args) {
		 SpringApplication.run(PCWebApp.class, args);
	}
	
//	 @Bean
//     public EmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() throws IOException {
//         TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//         tomcat.addAdditionalTomcatConnectors(httpConnector());
//         return tomcat;
//     }
//
//     public Connector httpConnector() throws IOException {
//         Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//         Http11NioProtocol http11NioProtocol = (Http11NioProtocol) connector.getProtocolHandler();
//         connector.setPort(8070);
//         //设置最大线程数
//         http11NioProtocol.setMaxThreads(100);
//         //设置初始线程数  最小空闲线程数
//         http11NioProtocol.setMinSpareThreads(20);
//         //设置超时
//         http11NioProtocol.setConnectionTimeout(5000);
//         return connector;
//     }
}
