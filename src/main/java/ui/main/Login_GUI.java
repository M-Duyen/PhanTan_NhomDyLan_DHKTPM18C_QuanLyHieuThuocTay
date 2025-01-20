package ui.main;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
//import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import database.ConnectDB;
import entity.StaticProcess;
import ui.login.Home;
import ui.login.Login;
import ui.scroll.win11.ScrollBarWin11UI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login_GUI extends JFrame implements ActionListener {
    public static Home home;

    public static Login loginPanel = new Login();

    public Login_GUI() {
        init();
        ConnectDB.getInstance().connect();
        getRootPane().setDefaultButton(loginPanel.btnLogin);
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(new Dimension(1920, 1080));
        setLocationRelativeTo(null);
        home = new Home();
        setContentPane(home);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                home.initOverlay(StaticProcess.login);
                home.play(0);
                home.getHomeOverlay().showLoginForm();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                home.stop();
            }
        });
    }

    public String getUserLogin(){
        return loginPanel.txtUsername.getText();
    }
    public static void main(Login_GUI login) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("ui.themes");
        FlatMacDarkLaf.setup();
        UIManager.put("deflauFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 20));
        EventQueue.invokeLater(() -> {
            login.setVisible(true);
        });

    }
    public void loginSuccessful() {
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
