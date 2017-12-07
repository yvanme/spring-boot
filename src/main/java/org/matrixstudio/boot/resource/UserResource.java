package org.matrixstudio.boot.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.security.core.userdetails.UserDetails;

public class UserResource extends ResourceSupport {

    private UserDetails user;

    public UserResource(UserDetails user) {
        this.user = user;
    }

    public UserDetails getUser() {
        return user;
    }
}
