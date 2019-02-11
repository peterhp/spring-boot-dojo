package dojo.spring.boot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal UserDetails user) {
        User userInfo = new User(user.getUsername(), "********", user.getAuthorities());

        return ResponseEntity.ok(userInfo);
    }
}
