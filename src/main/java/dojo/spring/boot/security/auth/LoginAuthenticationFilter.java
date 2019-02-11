package dojo.spring.boot.security.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Optional.ofNullable;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthenticationFilter.class);

    public LoginAuthenticationFilter(String authUri) {
        super(new AntPathRequestMatcher(authUri, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String username = ofNullable(request.getParameter("username")).orElse("").trim();
        String password = ofNullable(request.getParameter("password")).orElse("");

        LOGGER.info("Attempt to authenticate user [{}]", username);

        return this.getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    }
}
