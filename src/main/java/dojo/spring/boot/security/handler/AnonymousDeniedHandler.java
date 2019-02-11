package dojo.spring.boot.security.handler;

import dojo.spring.boot.security.model.AuthError;
import dojo.spring.boot.security.util.ErrorUtils;
import dojo.spring.boot.util.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnonymousDeniedHandler implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnonymousDeniedHandler.class);

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        LOGGER.warn("[Authentication Needed] Anonymous user can't access resource [{}]", request.getRequestURI());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader("WWW-Authenticate", "Bearer realm=\"Token\"");

        response.setContentType("application/json;charset=UTF-8");
        AuthError error = ErrorUtils.buildAuthError(request.getRequestURI(), HttpStatus.UNAUTHORIZED, "Authentication Needed");
        MapperUtils.getMapper().writeValue(response.getWriter(), error);
    }
}
