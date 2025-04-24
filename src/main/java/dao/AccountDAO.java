package dao;

import model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import service.AccountService;

public class AccountDAO extends GenericDAO<Account, String> implements AccountService {
    public AccountDAO(Class<Account> clazz) {
        super(clazz);
    }

    public AccountDAO(EntityManager em, Class<Account> clazz) {
        super(em, clazz);
    }

    /**
     * Lấy email theo accountID
     *
     * @param accountID
     * @return
     */
    @Override
    public String getEmailByAccountID(String accountID) {
        String email = "";

        try {
            String jpql = "SELECT e.email FROM Account ac JOIN ac.employee e WHERE ac.accountID = :accountID";
            return em.createQuery(jpql, String.class)
                    .setParameter("accountID", accountID)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Cập nhật pass khi quên pass
     *
     * @param accountID
     * @param password
     * @return
     */
    @Override
    public boolean updatePasswordByAccountID(String accountID, String password) {
        try {
            String jpql = "UPDATE Account a SET a.password = :password WHERE a.employee.employeeID = :accountID";

            return em.createQuery(jpql)
                    .setParameter("password", password)
                    .setParameter("accountID", accountID)
                    .executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
    AccountDAO accountDAO = new AccountDAO(Account.class);
//    System.out.println(accountDAO.getEmailByAccountID("036-30"));
}
}
