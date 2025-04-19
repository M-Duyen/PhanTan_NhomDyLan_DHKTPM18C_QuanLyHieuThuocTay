package dao;

import entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import utils.JPAUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Login_DAO{
    private Connection con; // Removed static modifier
    private String username;
    private String password;
    private String ten;
    private final EntityManager em;

    public Login_DAO() {
        this.em = JPAUtil.getEntityManager();
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


    public static void main(String[] args) {
        //ConnectDB.getInstance().connect();
        Login_DAO test = new Login_DAO(); // Create an instance of Test
//        System.out.println(test.dangNhapTen("MyDUYEN"));
        String tenDN = "EP1501";
        String MKDN = "EP1501@";
        List<String> kq =  test.login(tenDN, MKDN);
        System.out.println(kq);
//        System.out.println(test.containUserName(tenDN));

    }
}