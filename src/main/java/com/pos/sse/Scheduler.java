package com.pos.sse;

import com.pos.pojos.XItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by rrampall on 20/01/18.
 */

@Configuration
@EnableScheduling
public class Scheduler {

    @Autowired
    SseEngine sseEngine;


    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Scheduled(fixedRate = 5000)
    public void justPublish(){

        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);

        XItem x = new XItem();
        x.setName("test - " + new Date().toString() );
        x.setBarcode(123);
        x.setDescription("blabla");
        x.setPrice(234);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@  Emitters count =  " + sseEngine.getEmitters().size());
        if(sseEngine.getEmitters().size()>0)
        sseEngine.getEmitters().entrySet().forEach(emitter -> applicationEventPublisher.publishEvent(new ResultsEvent(this,emitter.getKey() ,
                Arrays.asList(x))));


    }
}
