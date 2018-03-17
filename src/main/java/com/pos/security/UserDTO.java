package com.pos.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * Created by rrampall on 15/03/18.
 */

@PasswordMatches
public class UserDTO {

        @NotNull
        @NotEmpty
        @Getter
        @Setter
        @Size(min = 1, message = "{Size.userDto.firstName}")
        private String firstName;

        @NotNull
        @NotEmpty
        @Getter @Setter
        @Size(min = 1, message = "{Size.userDto.lastName}")
        private String lastName;

        @NotNull
        @NotEmpty
        @Getter @Setter
        @Column(length = 60)
        private String password;

        @NotNull
        @NotEmpty
        @Getter @Setter
        private String matchingPassword;

        @NotNull
        @NotEmpty
        @Getter @Setter
        @ValidEmail
        @Size(min = 1, message = "{Size.userDto.email}")
        private String email;


        @Getter @Setter
        private Integer role;





}

}
