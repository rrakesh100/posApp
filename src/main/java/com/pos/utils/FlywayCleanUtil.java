package com.pos.utils;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Created by rrampall on 26/01/18.
 */
@Service
public class FlywayCleanUtil {


   //  uncomment the below lines and run mvn spring-boot:run to do a cleanup of the database

//    @Bean
//    public Flyway flyway(DataSource theDataSource) {
//        Flyway flyway = new Flyway();
//        System.out.println("@@@@@@@@@@ datasource = " + theDataSource);
//        flyway.setDataSource(theDataSource);
//        flyway.setLocations("classpath:db/migration");
//        flyway.clean();
//        flyway.migrate();
//
//        return flyway;
//    }


}
