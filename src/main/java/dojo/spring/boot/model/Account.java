package dojo.spring.boot.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Account {

    private String username;
    private String password;

    private List<String> authorities = new ArrayList<>();

    public Account() {

    }

    public Account(String username, String password, List<String> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
