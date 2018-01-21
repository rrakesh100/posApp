package com.pos.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.model.Item;
import com.pos.pojos.XItem;
import com.pos.service.ItemsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rrampall on 20/01/18.
 */
@Service
public class CustomersService  implements Sse {

    private static Logger logger = LoggerFactory.getLogger(CustomersService.class);

    @Autowired
    SseEngine sseEngine;

    @Autowired
    ItemsService itemsService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void push(String eventId, Object data) {
        sseEngine.run(this, new ResultsSseData(eventId));
    }

    @Override
    public void handle(SseData eventData) {
        ResultsSseData serviceData = (ResultsSseData) eventData;
        List<XItem> items = loadItems();
        int nextIndex = serviceData.getLastIndex() + 1;
//        if(items != null && nextIndex < items.size()){
//            XItem item = items.get(nextIndex);
//            serviceData.getProcessedItems().add(item);
//            serviceData.setLastIndex(nextIndex);
//            try {
//                // sleep 1 second for the first element, 2 seconds for the second,...
//                Thread.sleep((nextIndex + 1) * 1000);
//            } catch (InterruptedException e) {
//                logger.error(e.getMessage());
//            }

            applicationEventPublisher.publishEvent(new ResultsEvent(this, serviceData.getEventId(), items));
     //   }
    }

    @Override
    @EventListener(classes = ResultsEvent.class)
    public void onPublish(AbstractApplicationEvent event) {
        ResultsEvent resultsEvent = (ResultsEvent) event;
        SseEmitter emitter = sseEngine.getEmitters().get(event.getEventId());
        try {

            if (!resultsEvent.getItems().isEmpty()) {
                logger.debug("Sending message through emmitter " + emitter.toString());
                logger.debug("message = {} " , resultsEvent.getItems());

                //      //  SseEmitter.event().name()

                emitter.send(resultsEvent.getItems());
            }

        } catch (IOException e) {
            logger.error("Error in emitter " + emitter + " while sending message");
            sseEngine.getEmitters().remove(event.getEventId());
        }

    }

    private List<XItem> loadItems(){

        return itemsService.getAllItems();
    }




}
