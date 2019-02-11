package dojo.spring.boot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GreetController {

    @RequestMapping(value = "/greet", method = RequestMethod.GET)
    public ResponseEntity<String> sayHello(@AuthenticationPrincipal UserDetails user) {
        String person = user != null ? user.getUsername() : "guys";

        return ResponseEntity.ok(String.format("Hello, %s!", person));
    }
}
