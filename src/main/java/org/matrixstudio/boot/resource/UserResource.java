package org.matrixstudio.boot.resource;

import org.matrixstudio.boot.model.entity.User;
import org.springframework.hateoas.ResourceSupport;

public class UserResource extends ResourceSupport {

    private User user;

    public UserResource(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
