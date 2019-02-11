package dojo.spring.boot.security.auth;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import dojo.spring.boot.security.handler.AnonymousDeniedHandler;

public final class JwtAuthConfigurer<B extends HttpSecurityBuilder<B>> extends
        AbstractHttpConfigurer<HttpBasicConfigurer<B>, B> {

    private AuthenticationEntryPoint jwtAuthEntryPoint = new AnonymousDeniedHandler();

    @Override
    public void configure(B http) throws Exception {
        JwtAuthenticationFilter authFilter = new JwtAuthenticationFilter();
        authFilter.setAuthenticationEntryPoint(jwtAuthEntryPoint);

        http.addFilterAfter(postProcess(authFilter), LogoutFilter.class);
    }

    public JwtAuthConfigurer<B> authenticationEntryPoint(AuthenticationEntryPoint authEntryPoint) {
        this.jwtAuthEntryPoint = authEntryPoint;
        return this;
    }
}
