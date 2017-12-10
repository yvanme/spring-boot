package org.matrixstudio.boot.config.security.oauth2;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@SpringBootConfiguration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);

        resources.resourceId("spring-boot").stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/**").access("hasRole('USER') && #oauth2.hasScope('app')");

    }
}
