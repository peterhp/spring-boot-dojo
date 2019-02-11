package dojo.spring.boot.mapper;

import dojo.spring.boot.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    public UserDetails toUserDetails(Account account) {
        return new User(
                account.getUsername(),
                account.getPassword(),
                toAuthorities(account.getAuthorities())
        );
    }

    public Account fromUserDetails(UserDetails user) {
        return new Account(
                user.getUsername(),
                user.getPassword(),
                fromAuthorities(new ArrayList<>(user.getAuthorities()))
        );
    }

    public List<GrantedAuthority> toAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public List<String> fromAuthorities(List<GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
