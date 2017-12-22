package com.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@ImportResource("classpath:application-context.xml")
public class Main {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}

}


//Install postgres

//https://www.codementor.io/devops/tutorial/getting-started-postgresql-server-mac-osx

///Library/PostgreSQL/9.6/bin

//./psql -U postgres ca$hc0w

//CREATE DATABASE posdb;GRANT ALL PRIVILEGES ON DATABASE posdb TO rakesh;

	//ALTER DATABASE POSDB OWNER TO rakesh;
//./psql -U rakesh -d posdb



//https://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html



//http://www.thymeleaf.org/doc/articles/layouts.html

//https://github.com/rishi-anand/mrp/blob/master/database/2.3_to_2.3.1.sql

//spring-boot common application-properties.html


//flyway migration script names should start with capital V and start with 2 and not 1 because flyway baseline migration version is 1



//CORS support in the application
//https://spring.io/blog/2015/06/08/cors-support-in-spring-framework

