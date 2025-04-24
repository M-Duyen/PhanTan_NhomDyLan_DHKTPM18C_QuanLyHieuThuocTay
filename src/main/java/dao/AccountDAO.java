package dao;

import entity.Account;
import jakarta.persistence.EntityManager;

public class AccountDAO extends GenericDAO<Account, String> {
    public AccountDAO(Class<Account> clazz) {
        super(clazz);
    }

    public AccountDAO(EntityManager em, Class<Account> clazz) {
        super(em, clazz);
    }
}
