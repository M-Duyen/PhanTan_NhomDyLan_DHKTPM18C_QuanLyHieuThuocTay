package dao;

import entity.Account;
import jakarta.persistence.EntityManager;

public class Account_DAO extends GenericDAO<Account, String> {
    public Account_DAO(Class<Account> clazz) {
        super(clazz);
    }

    public Account_DAO(EntityManager em, Class<Account> clazz) {
        super(em, clazz);
    }
}
