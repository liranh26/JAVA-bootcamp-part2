package ajbc.learn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.dao.MongoProductDao;


@Configuration
@PropertySource("classpath:jdbc.properties")
public class AppConfing {
/*
 * This is the Configuration class for spring 
 * site (spring io docs) for spring framework have there the documantion
 */
	
	@Value("${server_address}")
	private String serverAddress;
	@Value("${port}")
	private String port;
	@Value("${db_name}")
	private String databaseName;
	@Value("${server_name}")
	private String serverName;
	@Value("${user}")
	private String userName;
	@Value("${password}")
	private String password;
	
	
	
	//Bean returns the product we want
	
	/*
	 * prototye is a lazy instatiated - it creates the instance only when we request it by getbean
	 */
	@Bean
	public MongoProductDao mongoDao() {
		return new MongoProductDao();
	}
	
	
	//Lazy avoids the bean to instatied if not calls - only when calls the 
//	@Lazy
//	@Scope("singleton")
	
	@Bean
	public JdbcProductDao jdbcDao() {
		JdbcProductDao dao = new JdbcProductDao();
		dao.setUserName(userName);
		dao.setPassword(password);
		dao.setDatabaseName(databaseName);
		dao.setPort(port);
		dao.setServerName(serverName);
		dao.setServerAddress(serverAddress);
		
		return dao;
	}
	
	
}
