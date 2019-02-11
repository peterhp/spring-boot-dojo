package dojo.spring.boot.dao;

import dojo.spring.boot.exception.DaoException;
import org.springframework.stereotype.Repository;
import dojo.spring.boot.model.Account;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountDao {

    private Map<String, Account> accounts = new HashMap<>();

    public boolean exists(String username) {
        return accounts.containsKey(username);
    }

    public void insert(Account account) throws DaoException {
        if (accounts.containsKey(account.getUsername())) {
            throw new DaoException(String.format("User [%s] already exists.", account.getUsername()));
        }

        accounts.put(account.getUsername(), account);
    }

    public void delete(String username) throws DaoException {
        if (!accounts.containsKey(username)) {
            throw new DaoException(String.format("User [%s] doesn't exist.", username));
        }

        accounts.remove(username);
    }

    public void update(Account account) throws DaoException {
        if (!accounts.containsKey(account.getUsername())) {
            throw new DaoException(String.format("User [%s] doesn't exist.", account.getUsername()));
        }

        accounts.put(account.getUsername(), account);
    }

    public Account find(String username) throws DaoException {
        if (!accounts.containsKey(username)) {
            throw new DaoException(String.format("User [%s] doesn't exist.", username));
        }

        return accounts.get(username);
    }
}
