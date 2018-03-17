package com.pos.security;

import com.pos.commons.PosApplicationException;
import com.pos.model.VerificationToken;
import com.pos.repository.RolesRepository;
import com.pos.repository.UsersRepository;
import com.pos.repository.VerificationTokenRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Created by rrampall on 15/03/18.
 */
public class UserService implements IUserService {

    @Autowired
    private UsersRepository usersRepository;


    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private RolesRepository rolesRepository;



    @Autowired
    private Mapper mapper;


    @Autowired
    private PasswordEncoder passwordEncoder;



    @Transactional
    @Override
    public User registerNewUserAccount(UserDTO userDTO)
            throws PosApplicationException {

        if (emailExist(userDTO.getEmail())) {
            throw new PosApplicationException(
                    "There is an account with that email adress: "
                            +  userDTO.getEmail());
        }

        User user = mapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Arrays.asList(rolesRepository.findByName("ADMIN_ROLE")));
        return this.usersRepository.save(user);
    }


    private boolean emailExist(String email) {
        User user = this.usersRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }


    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }


    @Override
    public String validatePasswordResetToken(long id, String token) {
//        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
//        if ((passToken == null) || (passToken.getUser().getId() != id)) {
//            return "invalidToken";
//        }
//
//        final Calendar cal = Calendar.getInstance();
//        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//            return "expired";
//        }
//
//        final User user = passToken.getUser();
//        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
       return null;
    }
}
