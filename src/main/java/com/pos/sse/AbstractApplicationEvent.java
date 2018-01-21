package com.pos.sse;

import org.springframework.context.ApplicationEvent;

/**
 * Created by rrampall on 20/01/18.
 */
public class AbstractApplicationEvent extends ApplicationEvent {


    private static final long serialVersionUID = 1L;

    protected String eventId;

    public AbstractApplicationEvent(Object source, String eventId) {
        super(source);
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
