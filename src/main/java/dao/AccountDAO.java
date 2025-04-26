package dao;

import model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import service.AccountService;
import utils.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO extends GenericDAO<Account, String> implements AccountService {

    public AccountDAO(Class<Account> clazz) {
        super(clazz);
        this.em = JPAUtil.getEntityManager();
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
     * Lấy tên đăng nhập
     *
     * @param userName
     * @return
     */
    public String containUserName(String userName) {
        String jpql = "select ac.accountID " +
                "from Account ac " +
                "where ac.accountID=:username";

        return em.createQuery(jpql, String.class)
                .setParameter("username", userName)
                .getSingleResult();

    }

    /**
     * Đăng nhập
     *
     * @param userName
     * @param pass
     * @return
     */
    public List<String> login(String userName, String pass) {
        String jpql = "SELECT a.accountID, a.password FROM Account a WHERE a.accountID = :userName AND a.password = :pass";

        List<Object[]> results = em.createQuery(jpql, Object[].class)
                .setParameter("userName", userName)
                .setParameter("pass", pass)
                .getResultList();

        List<String> list = new ArrayList<>();
        if (!results.isEmpty()) {
            Object[] row = results.get(0);
            list.add((String) row[0]); // accountID
            list.add((String) row[1]); // password
        }

        return list;
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
        System.out.println(accountDAO.containUserName("EP0903"));
}
}
