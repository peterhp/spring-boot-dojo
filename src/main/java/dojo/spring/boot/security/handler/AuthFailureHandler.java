package dojo.spring.boot.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import dojo.spring.boot.security.model.AuthError;
import dojo.spring.boot.util.MapperUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static dojo.spring.boot.security.util.ErrorUtils.buildAuthError;

public class AuthFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFailureHandler.class);

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        LOGGER.warn("[Authentication Failed] Fail to authenticate user");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader("WWW-Authenticate", "Bearer realm=\"Token\"");

        response.setContentType("application/json;charset=UTF-8");
        AuthError error = buildAuthError(request.getRequestURI(), HttpStatus.UNAUTHORIZED, "Authentication Failed");
        MapperUtils.getMapper().writeValue(response.getWriter(), error);
    }
}
