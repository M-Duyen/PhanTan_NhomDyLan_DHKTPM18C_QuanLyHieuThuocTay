package ui.login;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import dao.Account_DAO;
import dao.Email_DAO;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class ForgotPassword extends JPanel implements ActionListener, KeyListener {
    Font f = new Font("Times New Romam", Font.PLAIN, 20);
    Font f1 = new Font("Times New Romam", Font.ITALIC, 15);
    public JButton btnSendCode;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JLabel lblErrorUser;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private Account_DAO account_dao = new Account_DAO();


    public ForgotPassword() {
        init();

        txtUsername.addKeyListener(this);
        txtEmail.addKeyListener(this);
        btnSendCode.addKeyListener(this);
        btnSendCode.addActionListener(this);

    }

    private void init() {
        setOpaque(false);
        addMouseListener(new MouseAdapter() {

        });

        setLayout(new MigLayout("wrap,fillx,insets 90 80 90 80", "[fill]"));

        JLabel title = new JLabel("Forgot password", SwingConstants.CENTER);

        lblErrorUser = new JLabel("", SwingConstants.LEFT);
        lblErrorUser.setFont(f1);
        lblErrorUser.setForeground(Color.red);
        txtUsername = new JTextField();
        txtEmail = new JTextField();

        btnSendCode = new JButton("Gửi mã");
        btnSendCode.setFont(f);
        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +30");
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "" +
                "margin:10,10,10,10;" +
                "focusWidth:1;" +
                "innerFocusWidth:0");
        txtUsername.setFont(new Font("Arial (Body)", Font.PLAIN, 18));

//        txtEmail.putClientProperty(FlatClientProperties.STYLE, "" +
//                "margin:10,10,10,10;");
//        txtEmail.setFont(new Font("Arial (Body)", Font.PLAIN, 18));

        btnSendCode.putClientProperty(FlatClientProperties.STYLE, "" +
                "margin:5,5,5,5;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        btnSendCode.setBackground(new Color(5, 149, 243));
        btnSendCode.setFont(new Font("Times New Romam", Font.PLAIN, 23));
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your email");


        add(title);
        lblUsername = new JLabel("Username");

        lblUsername.setFont(f);
        add(lblUsername, "gapy 20");
        add(txtUsername, "gapy 15");
        add(lblErrorUser, "gapy 5");

        lblPassword = new JLabel();
        lblPassword.setFont(f);
        add(lblPassword, "gapy 20");
        add(btnSendCode, "gapy 40");
        add(new JLabel(), "gapy 2");


    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int arc = UIScale.scale(50);
        g2.setColor(getBackground());
        g2.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));
        g2.dispose();
        super.paintComponent(g);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnSendCode)) {
            String username = txtUsername.getText().trim();

            if (username.isEmpty()) {
                txtUsername.requestFocus();
                lblErrorUser.setText("Vui lòng nhập tên người dùng");
            } else {
                String email = account_dao.getEmailByAccountID(username);
                if (!email.isEmpty()) {
                    lblErrorUser.setForeground(Color.white);
                    lblErrorUser.setText("Mật khẩu mới được gửi tới địa chỉ " + email);

                    if (account_dao.updatePasswordByAccountID(username, username)) {
                        Email_DAO email_dao = new Email_DAO();
                        email_dao.sendEmail(email, "Khôi phục mật khẩu", "Mật khẩu mới của bạn là " + username);
                    } else {
                        System.out.println("Cập nhật mật khẩu thất bại");
                    }
                } else {
                    lblErrorUser.setText("Tên người dùng không tồn tại");
                }

            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == txtUsername) {
                txtEmail.requestFocus();
            } else if (e.getSource() == txtEmail) {
                btnSendCode.doClick();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {


    }
}
