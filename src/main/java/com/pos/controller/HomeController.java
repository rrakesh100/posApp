package com.pos.controller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * Created by rrampall on 19/12/17.
 */
@Controller
public class HomeController {

    @RequestMapping("/hello")
    public @ResponseBody  String sayHello(){
        return "Hello world !!! @@@@";
    }




//    //uncomment the below lines and run mvn spring-boot:run to do a cleanup of the database
//
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


//    public static void main(String[] args) {
//        String s = "  rokefsh/";
//        for(Character c : s.toCharArray()){
//            System.out.printf("\\u%04x \n", (int) c);
//        }
//
//
//        String p1= s.replaceAll("\\s","\\\\u0020").replaceAll("/","\\\\u002f");
//
//        System.out.println(p1);
//
//        String q= StringEscapeUtils.unescapeJava(p1);
//        String q2= StringEscapeUtils.unescapeJava(q);
//
//
//        System.out.println(q);
//        System.out.println(q2);
//
//
//
//
//    }

}
