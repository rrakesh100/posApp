package com.pos.sse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

/**
 * Created by rrampall on 20/01/18.
 */
@Controller
@CrossOrigin
public class CustomersController  {


    @Autowired
    SseEngine sseEngine;

    @Autowired
    CustomersService sseService;

    @GetMapping("/customers")
    public SseEmitter getResults() {
        String eventId = UUID.randomUUID().toString();
        SseEmitter emitter = new SseEmitter(sseEngine.getTimeout());
        emitter.onCompletion(()->sseEngine.getEmitters().remove(eventId));
        sseEngine.getEmitters().put(eventId, emitter);

        // push notifications
        sseService.push(eventId, null);

        return emitter;
    }
}
