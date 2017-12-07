package org.matrixstudio.boot.config.security.oauth2;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@FrameworkEndpoint
public class UserInfoEndpoint {

    @RequestMapping(
            value = "/oauth/user_info",
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Principal getPrincipal(Principal principal) {
        return principal;
    }
}
