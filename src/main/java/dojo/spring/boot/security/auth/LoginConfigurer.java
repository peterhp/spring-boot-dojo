package dojo.spring.boot.security.auth;

import dojo.spring.boot.security.handler.AuthFailureHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import dojo.spring.boot.security.handler.AuthSuccessHandler;

public final class LoginConfigurer<B extends HttpSecurityBuilder<B>> extends
        AbstractHttpConfigurer<HttpBasicConfigurer<B>, B> {

    private String authUri = "/token/auth";

    private AuthenticationSuccessHandler authSuccessHandler = new AuthSuccessHandler();
    private AuthenticationFailureHandler authFailureHandler = new AuthFailureHandler();

    @Override
    public void configure(B http) throws Exception {
        LoginAuthenticationFilter authFilter = new LoginAuthenticationFilter(authUri);

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        authFilter.setAuthenticationManager(authenticationManager);

        authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        authFilter.setAuthenticationFailureHandler(authFailureHandler);

        http.addFilterAfter(postProcess(authFilter), LogoutFilter.class);
    }

    public LoginConfigurer<B> loginUri(String uri) {
        this.authUri = uri;
        return this;
    }

    public LoginConfigurer<B> loginSuccessHandler(AuthenticationSuccessHandler authSuccessHandler) {
        this.authSuccessHandler = authSuccessHandler;
        return this;
    }

    public LoginConfigurer<B> loginFailureHandler(AuthenticationFailureHandler authFailureHandler) {
        this.authFailureHandler = authFailureHandler;
        return this;
    }
}
