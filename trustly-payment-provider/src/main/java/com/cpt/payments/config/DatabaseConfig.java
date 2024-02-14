package com.cpt.payments.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource(value = { "classpath:application-${spring.profiles.active}.properties" })
public class DatabaseConfig {
	@Autowired
	private Environment env;

	@Bean
	@Qualifier("TrustlyServiceJdbcTemplate")
	public DataSource processingServiceJdbcTemplate() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
		dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		dataSource.setMaximumPoolSize(Integer.parseInt(env.getProperty("spring.datasource.maxActive")));
		dataSource.setConnectionInitSql(env.getProperty("spring.datasource.validationQuery"));
		dataSource.setMinimumIdle(Integer.parseInt(env.getProperty("spring.datasource.minIdle")));
		return dataSource;
	}

	@Bean
	@Qualifier("namedJdbcTemplate")
	public NamedParameterJdbcTemplate namedJdbcTemplate() {
		return new NamedParameterJdbcTemplate(processingServiceJdbcTemplate());
	}
}
