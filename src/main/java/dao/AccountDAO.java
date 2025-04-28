package dao;

import model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import service.AccountService;
import utils.JPAUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class AccountDAO extends GenericDAO<Account, String> implements AccountService {
    public AccountDAO(Class<Account> clazz) {
        super(clazz);
        this.em = JPAUtil.getEntityManager();
    }

    public AccountDAO(EntityManager em, Class<Account> clazz) {
        super(em, clazz);

    }

    static final String from = "ntmd160208@gmail.com";
    static final String password = "vkjlcvfedyctokjv";

    /**
     * Gửi mã xác thực đến email
     *
     * @param to
     * @param title
     * @param content
     * @return
     */
    public boolean sendEmail(String to, String title, String content) {
        // Properties : khai báo các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(from, password);
            }
        };

        // Phiên làm việc
        Session session = Session.getInstance(props, auth);

        // Tạo một tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
            // Kiểu nội dung
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

            // Người gửi
            msg.setFrom(from);

            // Người nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            // Tiêu đề email
            msg.setSubject(title);

            // Quy đinh ngày gửi
            msg.setSentDate(new Date());

            // Quy định email nhận phản hồi
            // msg.setReplyTo(InternetAddress.parse(from, false))

            // Nội dung
            msg.setContent(content, "text/HTML; charset=UTF-8");

            // Gửi email
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
     * @param newPassword
     * @return
     */
    @Override
    public boolean updatePasswordByAccountID(String accountID, String newPassword) {
        try {
            em.getTransaction().begin(); // Mở transaction

            String jpql = "UPDATE Account a SET a.password = :newPassword WHERE a.employee.employeeID = :accountID";
            int updatedCount = em.createQuery(jpql)
                    .setParameter("newPassword", newPassword)
                    .setParameter("accountID", accountID)
                    .executeUpdate();

            em.getTransaction().commit(); // Commit transaction

            return updatedCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback nếu có lỗi
            }
            return false;
        }
    }

    /**
     * Kiểm tra tài khoản đã đăng nhập hay chưa
     *
     * @param accountId
     * @param password
     * @return
     */
    public boolean loginCheck(String accountId, String password) {
        Account account = findById(accountId);

        // Cho phép đăng nhập
        account.setLoggedIn(true);
        update(account);

        return true;
    }

    /**
     * Đăng xuất tài khoản
     *
     * @param accountId
     * @return
     */
    public boolean logout(String accountId) {
        Account account = findById(accountId);
        em.getTransaction().begin();
        account.setLoggedIn(false);
        update(account);
        em.getTransaction().commit();
        return true;
    }

    /** Kiểm tra tài khoản đã đăng nhập hay chưa
     *
     * @param accountId
     * @return
     */

    public boolean isAccountLoggedIn(String accountId) {
        Account account = findById(accountId);
        return account != null && account.isLoggedIn();


    }

    /**
     * Cập nhật trạng thái đăng nhập
     *
     * @param accountId
     * @param isLoggedIn
     * @return
     */
    public boolean updateLoggedInStatus(String accountId, boolean isLoggedIn){
        Account account = findById(accountId);
        if (account != null) {
            account.setLoggedIn(isLoggedIn);
            update(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean outAllAccount(){
        String jpql = "UPDATE Account a SET a.loggedIn = false";
        em.getTransaction().begin();
        int updatedCount = em.createQuery(jpql)
                .executeUpdate();
        em.getTransaction().commit();
        return updatedCount > 0;
    }


    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO(Account.class);
//    System.out.println(accountDAO.getEmailByAccountID("036-30"));
        System.out.println(accountDAO.containUserName("EP1501"));
    }
}
