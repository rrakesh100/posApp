package com.pos.sse;

import com.pos.pojos.XItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rrampall on 20/01/18.
 */
@Component
public class SseEngine {

    private static Logger logger = LoggerFactory.getLogger(SseEngine.class);

    private static long TIMEOUT = 120000L;

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();


    /**
     * Setup Sse emitter and keep publishing data until timeout completes
     *
     * @param sse
     * @param data
     */
    @Async
    public void runInLoop(Sse sseService, SseData data) {
        config(emitters.get(data.getEventId()), data.getEventId());

        if(emitters.get(data.getEventId()) != null){
            while ((Calendar.getInstance().getTimeInMillis() - data.getStarted().getTime()) < TIMEOUT) {
                sseService.handle(data);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
        }

    }

    /**
     * Setup Sse emitter and publish data
     *
     * @param sse
     * @param eventData
     */
    @Async
    public void run(Sse sseService, SseData eventData) {
        if(emitters.get(eventData.getEventId()) != null){
            config(emitters.get(eventData.getEventId()), eventData.getEventId());
            sseService.handle(eventData);
        }
    }

    private void config(SseEmitter emitter, String eventId) {
        if (emitter != null) {
            emitter.onCompletion(() -> {
                logger.debug("Emitter " + emitter.toString() + " COMPLETED!");
                emitters.remove(eventId);
            });
            emitter.onTimeout(() -> {
                logger.debug("Emitter " + emitter.toString() + " TIMEOUT!");
                emitters.remove(eventId);
            });
        }
    }

    public long getTimeout() {
        return TIMEOUT;
    }

    public Map<String, SseEmitter> getEmitters() {
        return emitters;
    }



}
