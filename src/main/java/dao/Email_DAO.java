package dao;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Email_DAO {

    public Email_DAO() {
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

    public static void main(String[] args) {
        Email_DAO email_dao = new Email_DAO();
        email_dao.sendEmail("nguyenmyduyen2702@gmail.com", "Test", "Đây là phần nội dung!");

    }

}