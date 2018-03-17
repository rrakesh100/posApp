package com.pos.security;

import com.pos.commons.PosApplicationException;

/**
 * Created by rrampall on 15/03/18.
 */
public interface IUserService {

    public User registerNewUserAccount(UserDTO userDTO) throws PosApplicationException;

    public String validatePasswordResetToken(long id, String token) ;

    public void createVerificationTokenForUser(final User user, final String token) ;

}
