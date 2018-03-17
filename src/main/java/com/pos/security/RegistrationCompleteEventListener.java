package com.pos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.UUID;

/**
 * Created by rrampall on 15/03/18.
 */
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {


    @Autowired
    private UserService userService;


    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent registrationCompleteEvent) {

    }


    private void confirmRegistration( RegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    //

    private final SimpleMailMessage constructEmailMessage(final RegistrationCompleteEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final SimpleMailMessage email = new SimpleMailMessage();


        //APPURL below should be the endpoint which has to be hit by the user in the registration confirmation email

//        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
//        final String message = messages.getMessage("message.regSucc", null, event.getLocale());
//        email.setTo(recipientAddress);
//        email.setSubject(subject);
//        email.setText(message + " \r\n" + confirmationUrl);
//        email.setFrom(env.getProperty("support.email"));
        return email;
    }

}
