package com.pos.security;

import com.pos.commons.PosApplicationException;
import com.pos.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by rrampall on 15/03/18.
 */
@Controller
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<HttpStatus> registerUser(@Valid UserDTO userDTO, HttpServletRequest request) throws PosApplicationException {
        logger.debug("Registering user account with information: {}", userDTO);
        User registeredUser = userService.registerNewUserAccount(userDTO);
        if (registeredUser == null) {
            throw new PosApplicationException("User already exists");
        }

        publisher.publishEvent(
                new RegistrationCompleteEvent(registeredUser, request.getLocale()));

        return new Response<HttpStatus>().noContent().build();

    }


}
