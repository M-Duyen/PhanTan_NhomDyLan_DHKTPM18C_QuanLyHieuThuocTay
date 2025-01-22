package dao;

import entity.Account;
import jakarta.persistence.EntityManager;

public class Account_DAO extends AbstractCRUD<Account, String> {
    public Account_DAO(EntityManager em) {
        super(em, Account.class);
    }
}
