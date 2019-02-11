package dojo.spring.boot.security.handler;

import dojo.spring.boot.security.model.JwtToken;
import dojo.spring.boot.security.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import dojo.spring.boot.util.MapperUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        LOGGER.info("[Authentication Succeeded] Succeed to authenticate user [{}]", user.getUsername());

        response.setStatus(HttpStatus.OK.value());

        response.setContentType("application/json;charset=UTF-8");
        JwtToken token = new JwtToken(TokenUtils.buildToken(user));
        MapperUtils.getMapper().writeValue(response.getWriter(), token);
    }
}
