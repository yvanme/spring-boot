package org.matrixstudio.boot.controller;

import org.matrixstudio.boot.model.entity.User;
import org.matrixstudio.boot.repository.jpa.UserRepository;
import org.matrixstudio.boot.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController {

    private final Assembler userAssembler = new Assembler();

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}")
    public UserResource get(@PathVariable String username) {
        User user = userRepository.findOne(username);
        return userAssembler.toResource(user);
    }

    class Assembler extends ResourceAssemblerSupport<User, UserResource> {
        public Assembler() {
            super(UserController.class, UserResource.class);
        }

        @Override
        public UserResource toResource(User user) {
            return user != null ? createResourceWithId(user.getUsername(), user) : null;
        }

        @Override
        protected UserResource instantiateResource(User entity) {
            return new UserResource(entity);
        }
    }
}
