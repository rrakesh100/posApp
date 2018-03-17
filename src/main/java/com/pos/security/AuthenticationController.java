package com.pos.security;

import com.pos.commons.PosApplicationException;
import com.pos.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by rrampall on 14/03/18.
 */
public class AuthenticationController {


    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<HttpStatus> registerUser(@Valid User accountDto, HttpServletRequest request) throws PosApplicationException {
        logger.debug("Registering user account with information: {}", accountDto);
        User registered = createUserAccount(accountDto);
        if (registered == null) {
            throw new PosApplicationException("User already exists");
        }
//        String appUrl = "http://" + request.getServerName() + ":" +
//                request.getServerPort() + request.getContextPath();
//
//        eventPublisher.publishEvent(
//                new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        return new Response<HttpStatus>().noContent().build();

    }

    private User createUserAccount(User accountDto) {
        User registered = null;
        try {
            registered = userService.registerNewUserAccount(accountDto);
        } catch (PosApplicationException e) {
            logger.error("Error occured while saving a new user", e);
        }
        return registered;

    }
}
