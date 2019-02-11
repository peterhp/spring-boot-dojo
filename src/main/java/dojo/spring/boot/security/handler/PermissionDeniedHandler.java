package dojo.spring.boot.security.handler;

import dojo.spring.boot.security.model.AuthError;
import dojo.spring.boot.security.util.ErrorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import dojo.spring.boot.util.MapperUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PermissionDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionDeniedHandler.class);

    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        LOGGER.warn("[Permission Denied] User has no permission to access resource [{}]", request.getRequestURI());

        response.setStatus(HttpStatus.FORBIDDEN.value());

        response.setContentType("application/json;charset=UTF-8");
        AuthError error = ErrorUtils.buildAuthError(request.getRequestURI(), HttpStatus.FORBIDDEN, "Permission Denied");
        MapperUtils.getMapper().writeValue(response.getWriter(), error);
    }
}
