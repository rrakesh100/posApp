package com.pos.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by rrampall on 15/03/18.
 */
public class RegistrationCompleteEvent extends ApplicationEvent {

    @Getter @Setter
    private User user;

    @Getter @Setter
    private Locale locale;

    public RegistrationCompleteEvent(User user, Locale locale) {
        super(user);
        this.user = user;
        this.locale = locale;
    }
}
