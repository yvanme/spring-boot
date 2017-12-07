package org.matrixstudio.boot.controller;

import org.matrixstudio.boot.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@ExposesResourceFor(UserDetails.class)
public class UserController {

    private final Assembler userAssembler = new Assembler();

    @Autowired
    @Qualifier("jdbcUserDetailsService")
    private UserDetailsService userDetailsService;

    @GetMapping("/{username}")
    public UserResource get(@PathVariable String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return userAssembler.toResource(userDetails);
    }

    class Assembler extends ResourceAssemblerSupport<UserDetails, UserResource> {
        public Assembler() {
            super(UserController.class, UserResource.class);
        }

        @Override
        public UserResource toResource(UserDetails user) {
            return user != null ? createResourceWithId(user.getUsername(), user) : null;
        }

        @Override
        protected UserResource instantiateResource(UserDetails entity) {
            return new UserResource(entity);
        }
    }
}
