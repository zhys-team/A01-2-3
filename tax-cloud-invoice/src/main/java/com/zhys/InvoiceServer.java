package com.zhys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import com.alibaba.druid.pool.DruidDataSource;
//import com.lorne.tx.compensate.repository.CompensateDataSource;
//import com.lorne.tx.db.LCNDataSourceProxy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
 
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@EnableAutoConfiguration
@EnableScheduling
public class InvoiceServer {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceServer.class, args);
	}
	
	@Autowired
	private Environment env;


//	@Bean
//	public CompensateDataSource compensateDataSource() {
//
//		DruidDataSource dataSource = new DruidDataSource();
//		dataSource.setUrl(env.getProperty("spring.datasource.url"));
//		dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
//		dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
//		dataSource.setInitialSize(1);
//		dataSource.setMaxActive(5);
//		dataSource.setMinIdle(0);
//		dataSource.setMaxWait(60000);
//		dataSource.setValidationQuery("SELECT 1");
//		dataSource.setTestOnBorrow(false);
//		dataSource.setTestWhileIdle(true);
//		dataSource.setPoolPreparedStatements(false);
//
//		CompensateDataSource compensateDataSource = new CompensateDataSource();
//		compensateDataSource.setDataSource(dataSource);
//		return compensateDataSource;
//	}
//
//	@Bean
//	public DataSource dataSource() {
//		DruidDataSource dataSource = new DruidDataSource();
//		dataSource.setUrl(env.getProperty("spring.datasource.url"));
//		dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
//		dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
//		dataSource.setInitialSize(2);
//		dataSource.setMaxActive(20);
//		dataSource.setMinIdle(0);
//		dataSource.setMaxWait(60000);
//		dataSource.setValidationQuery("SELECT 1");
//		dataSource.setTestOnBorrow(false);
//		dataSource.setTestWhileIdle(true);
//		dataSource.setPoolPreparedStatements(false);
//
//		LCNDataSourceProxy dataSourceProxy = new LCNDataSourceProxy();
//		dataSourceProxy.setDataSource(dataSource);
//		dataSourceProxy.setMaxCount(10);
//		return dataSourceProxy;
//	}

}
