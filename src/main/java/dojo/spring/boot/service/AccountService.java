package dojo.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dojo.spring.boot.dao.AccountDao;
import dojo.spring.boot.exception.DaoException;
import dojo.spring.boot.exception.DaoRuntimeException;
import dojo.spring.boot.mapper.AccountMapper;
import dojo.spring.boot.model.Account;

import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAccount(Account account) {
        try {
            account.setPassword(passwordEncoder.encode(account.getPassword()));

            accountDao.insert(account);
        } catch (DaoException e) {
            throw new DaoRuntimeException(e);
        }
    }

    public void modifyPassword(String username, String oldPassword, String newPassword) {
        try {
            Account account = accountDao.find(username);

            if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
                throw new RuntimeException("Password is not right.");
            }

            Account modifiedAccount = new Account(
                    account.getUsername(),
                    passwordEncoder.encode(newPassword),
                    account.getAuthorities()
            );

            accountDao.update(modifiedAccount);
        } catch (DaoException e) {
            throw new DaoRuntimeException(e);
        }
    }

    public void modifyAuthorities(String username, List<String> newAuthorities) {
        try {
            Account account = accountDao.find(username);

            Account modifiedAccount = new Account(
                    account.getUsername(),
                    account.getPassword(),
                    newAuthorities
            );

            accountDao.update(modifiedAccount);
        } catch (DaoException e) {
            throw new DaoRuntimeException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Account account = accountDao.find(username);

            return new AccountMapper().toUserDetails(account);
        } catch (DaoException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
