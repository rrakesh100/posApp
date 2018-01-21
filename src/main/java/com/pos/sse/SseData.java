package com.pos.sse;

import java.util.Date;

/**
 * Created by rrampall on 20/01/18.
 */
public class SseData {


    protected String eventId;
    protected Date started;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

}
