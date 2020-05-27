package wei.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//@Configuration
@EnableWebSecurity
public class HttpSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                //.oauth2Client();
                .oauth2Login()
                .userInfoEndpoint().userService(userInfoUserService());
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> userInfoUserService() {
        return oAuth2UserRequest -> new OAuth2User() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            }

            @Override
            public Map<String, Object> getAttributes() {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("userName", "user");
                return attributes;
            }

            @Override
            public String getName() {
                return "user";
            }
        };
    }
}
