package entity;

//import ui.main.HomePage;
//import ui.main.Login_GUI;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class StaticProcess {
    public static DecimalFormat df = new DecimalFormat("#,##0.00 VND");
    public static NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
    public static boolean loginSuccess = false;
//    public static Login_GUI login;
    public static String userlogin = "";
    public static Employee empLogin;
//    public static HomePage homePage;
}
