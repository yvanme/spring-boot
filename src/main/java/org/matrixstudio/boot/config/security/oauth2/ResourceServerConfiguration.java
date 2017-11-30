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

        resources.resourceId("spring-boot").stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().antMatchers("/audits/**", "/enterprises/**")
                .and()
                .authorizeRequests()
                .antMatchers("/audits/**").access("hasRole('USER')")
                .antMatchers("/enterprises/**").access("#oauth2.hasScope('app')");

    }
}
