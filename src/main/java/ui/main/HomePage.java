package ui.main;

import dao.Employee_DAO;
import dao.Manager_DAO;
import database.ConnectDB;
import entity.Employee;
import entity.Manager;
import entity.StaticProcess;
import ui.dialog.Confirm;
import ui.forms.TempOrderForm;
import ui.glasspanepupup.CreateOrderWithPres;
import ui.glasspanepupup.GlassPanePopup;
import ui.glasspanepupup.Message;
import ui.glasspanepupup.Notification;
import ui.menu.MenuEvent;
import ui.scroll.win11.ScrollBarWin11UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static entity.StaticProcess.homePage;
import static entity.StaticProcess.userlogin;


public class HomePage extends javax.swing.JFrame implements ActionListener{
    private JPanel currentPanel;

    private final HomeSlide homeSlide = new HomeSlide();
    private final CreateOrder createOrder = new CreateOrder(this);
    //private final OrderHistory orderHistory = new OrderHistory(this);
    private final RevenueStatistic revenueStatistic = new RevenueStatistic();
    //private CategorySearch category = new CategorySearch();
    private final AddProduct addProduct = new AddProduct();
    private final UpdateProduct updateProduct = new UpdateProduct();
    private final ProductStatistics productStatistics = new ProductStatistics(this);
    private final AddCustomer addCustomer = new AddCustomer();
    private final CustomerSearch customerSearch = new CustomerSearch();
    private final VendorSearch vendorSearch = new VendorSearch();
    private final AddVendor addVendor = new AddVendor();
    private final EmployeeSearch employeeSearch = new EmployeeSearch();
    private final AddEmployee addEmployee = new AddEmployee();
    private final PromotionSearch promotionSearch = new PromotionSearch();
    private final AddPromotion addPromotion = new AddPromotion();
    private final TodayRevenueStatistic todayRevenueStatistic = new TodayRevenueStatistic();
    private final ProcessOrder processOrder = new ProcessOrder();
    private static String accLoginID;

    public HomePage() {
        initComponents();
        setFullScreen();
        updateDateLable();
        initHomeSlide();
        GlassPanePopup.install(this);
        ui.glasspanepupup.Notification notification;
        GlassPanePopup.install(this);
        menu.setEvent(new MenuEvent() {
            @Override
            public void selected(int index, int subIndex) {
                openFrame(index, subIndex);
            }
        });

        btnGuide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URI uri = new URI("https://pphiep.github.io/HTCNWeb/User_manual/HTML/User_manual.html");
                    Desktop.getDesktop().browse(uri);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        pCenter.requestFocusInWindow();
        btnAvatar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee_DAO employee_dao = new Employee_DAO();
                Manager_DAO manager_dao = new Manager_DAO();

                Message message = new Message();

                if(userlogin.startsWith("MN")){
                    Manager emp = Manager_DAO.getInstance().getManager_ByID(userlogin);
                    message.lblEmpID_show.setText(emp.getManagerID());
                    message.lblEmpName_show.setText(emp.getManagerName());
                    message.lblPhoneNumber_show.setText(emp.getPhoneNumber());
                    message.lblDOB_show.setText(emp.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    //TODO: Trong bảng manager thiếu mấy thông tin này
                    message.lblGender_show.setText((""));
                    message.lblDegree_show.setText("");
                    message.lblEmail_show.setText("");
                }else {
                    Employee emp = Employee_DAO.getInstance().getListEmployeeByAccountID(userlogin);
                    message.lblEmpID_show.setText(emp.getEmployeeName());
                    message.lblEmpName_show.setText(emp.getEmployeeID());
                    message.lblPhoneNumber_show.setText(emp.getPhoneNumber());
                    message.lblDOB_show.setText(emp.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    message.lblGender_show.setText((emp.isGender()) ? "Nữ" : "Nam");
                    message.lblDegree_show.setText(emp.getDegree());
                    message.lblEmail_show.setText(emp.getEmail());
                }
                GlassPanePopup.showPopup(message);
            }
        });
        btnNotification.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notification notification = new Notification();
                GlassPanePopup.showPopup(notification);
                notification.myList1.addItem("Đăng nhập thành công tài khoản " + userlogin);
            }
        });
    }

    public static void setRole(String s){
        accLoginID = s;
    }

    private void openFrame(int index,int subIndex){
        //Trang chủ
        if(index == 0 && subIndex == 0){
            replacePanel(homeSlide);
        }
        //Hóa đơn
        if(index == 1 && subIndex == 1){
            replacePanel(createOrder);
        } else if(index == 1 && subIndex == 2){
            replacePanel(new OrderHistory(this));
        } else if(index == 1 && subIndex == 3){
            replacePanel(processOrder.getPnlProcessPanel());
        } else if(index == 1 && subIndex == 4){
            replacePanel(revenueStatistic);
        } else if(index == 1 && subIndex == 5){
            replacePanel(new TodayRevenueStatistic());
        }
        //Sản phẩm
        else if(index == 2 && subIndex == 1){
            replacePanel(new CategorySearch(this));
        } else if(index == 2 && subIndex == 2){
            replacePanel(addProduct);
        } else if(index == 2 && subIndex == 3){
            replacePanel(productStatistics);
            productStatistics.startAnimation();
        }
        //Khách hàng
        else if(index == 3 && subIndex == 1){
            replacePanel(customerSearch);
        } else if(index == 3 && subIndex == 2){
            replacePanel(addCustomer);
        }
        //Nhà cung cấp
        else if(index == 4 && subIndex == 1){
            replacePanel(vendorSearch);
        } else if(index == 4 && subIndex == 2){
            replacePanel(addVendor);
        }
        //Nhân viên
        else if(index == 5 && subIndex == 1){
            replacePanel(employeeSearch);
        } else if(index == 5 && subIndex == 2){
            replacePanel(addEmployee);
        }
        //Khuyến mãi
        else if(index == 6 && subIndex == 1){
            replacePanel(promotionSearch);
        } else if(index == 6 && subIndex == 2){
            replacePanel(addPromotion);
        }
    }

    public void initHomeSlide(){
        currentPanel = homeSlide;
        replacePanel(homeSlide);
    }

    public void replacePanel(JPanel panel){
        if (currentPanel != null && currentPanel.getClass().equals(panel.getClass())) {
            return;
        }

        currentPanel = panel;
        pCenter.removeAll();

        pCenter.setLayout(new BorderLayout());
        pCenter.add(panel, BorderLayout.CENTER);

        pCenter.revalidate();
        pCenter.repaint();
    }

    private void setFullScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = ge.getMaximumWindowBounds();

        this.setBounds(bounds);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    private void updateDateLable(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        lbHeaderDate.setText(sdf.format(now));
    }

    public void updateCurretPanel(JPanel panel){
        currentPanel = panel;
    }

    public void showPres(TempOrderForm tempOrderForm){
        GlassPanePopup.showPopup(new CreateOrderWithPres(this, tempOrderForm));
    }

    public void closePres(){
        GlassPanePopup.closePopup(0);
    }

    public CreateOrder getCreateOrder(){
        return createOrder;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pNorth = new JPanel();
        lbHeaderDate = new javax.swing.JLabel();
        btnGuide = new ui.button.Button();
        btnNotification = new ui.button.Button();
        btnAvatar = new ui.button.Button();
        pWest = new JPanel();
        lbLogo = new javax.swing.JLabel();
        lblEmplName = new javax.swing.JLabel();
        scrollPaneWin = new ui.scroll.win11.ScrollPaneWin11();
        menu = new ui.menu.Menu();
        btnLogout = new ui.button.Button();
        pCenter = new JPanel();
        homeSlide1 = new HomeSlide();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pNorth.setBackground(new java.awt.Color(102, 204, 255));
        pNorth.setPreferredSize(new java.awt.Dimension(756, 75));

        lbHeaderDate.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbHeaderDate.setForeground(new java.awt.Color(255, 255, 255));

        btnGuide.setBackground(new java.awt.Color(102, 204, 255));
        btnGuide.setIcon(new javax.swing.ImageIcon("src/main/java/ui/button/information_32.png")); // NOI18N
        btnGuide.setToolTipText("");
        btnGuide.setRound(50);
        btnGuide.setShadowColor(new java.awt.Color(102, 204, 255));

        btnNotification.setBackground(new java.awt.Color(102, 204, 255));
        btnNotification.setIcon(new javax.swing.ImageIcon("src/main/java/ui/button/notification_32.png")); // NOI18N
        btnNotification.setRound(50);
        btnNotification.setShadowColor(new java.awt.Color(102, 204, 255));

        btnAvatar.setBackground(new java.awt.Color(102, 204, 255));
        btnAvatar.setIcon(new javax.swing.ImageIcon("src/main/java/ui/button/user_32_white.png")); // NOI18N
        btnAvatar.setRound(50);
        btnAvatar.setShadowColor(new java.awt.Color(102, 204, 255));

        lblEmplName.setBackground(new java.awt.Color(255, 255, 255));
        lblEmplName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmplName.setForeground(new java.awt.Color(255, 255, 255));
        lblEmplName.setHorizontalAlignment(SwingConstants.RIGHT);
        String userName = userlogin.startsWith("MN")?Manager_DAO.getInstance().getManager_ByID(userlogin).getManagerName():Employee_DAO.getInstance().getEmployee_ByID(userlogin).getEmployeeName();
        lblEmplName.setText(userName);

        javax.swing.GroupLayout pNorthLayout = new javax.swing.GroupLayout(pNorth);
        pNorth.setLayout(pNorthLayout);
        pNorthLayout.setHorizontalGroup(
                pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pNorthLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(lbHeaderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblEmplName, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNotification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnGuide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
        );
        pNorthLayout.setVerticalGroup(
                pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pNorthLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnNotification, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAvatar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnGuide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(pNorthLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lbHeaderDate, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                        .addComponent(lblEmplName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pWest.setBackground(new java.awt.Color(102, 204, 255));

        lbLogo.setIcon(new javax.swing.ImageIcon("src/main/java/ui/menu/logoleft.png")); // NOI18N
        lbLogo.setPreferredSize(new java.awt.Dimension(329, 200));

        scrollPaneWin.setBackground(new java.awt.Color(102, 204, 255));
        scrollPaneWin.setBorder(null);
        scrollPaneWin.setViewportView(menu);

        btnLogout.setBackground(new java.awt.Color(102, 204, 255));
        btnLogout.setIcon(new javax.swing.ImageIcon("src/main/java/ui/button/logout.png")); // NOI18N
        btnLogout.setShadowColor(new java.awt.Color(102, 204, 255));

        javax.swing.GroupLayout pWestLayout = new javax.swing.GroupLayout(pWest);
        pWest.setLayout(pWestLayout);
        pWestLayout.setHorizontalGroup(
            pWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneWin, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addComponent(lbLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pWestLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pWestLayout.setVerticalGroup(
            pWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pWestLayout.createSequentialGroup()
                .addComponent(lbLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollPaneWin, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pCenter.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pCenterLayout = new javax.swing.GroupLayout(pCenter);
        pCenter.setLayout(pCenterLayout);
        pCenterLayout.setHorizontalGroup(
            pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCenterLayout.createSequentialGroup()
                .addComponent(homeSlide1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        pCenterLayout.setVerticalGroup(
            pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCenterLayout.createSequentialGroup()
                .addComponent(homeSlide1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 225, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pWest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pNorth, javax.swing.GroupLayout.DEFAULT_SIZE, 1632, Short.MAX_VALUE)
                    .addComponent(pCenter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pWest, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pNorth, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pCenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
        btnLogout.addActionListener(this);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        UIDefaults ui = UIManager.getDefaults();
        ui.put("ScrollBarUI", ScrollBarWin11UI.class.getCanonicalName());

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StaticProcess.homePage = new HomePage();
                homePage.setVisible(true);
                StaticProcess.login.setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ui.button.Button btnAvatar;
    private ui.button.Button btnGuide;
    private ui.button.Button btnLogout;
    private ui.button.Button btnNotification;
    private HomeSlide homeSlide1;
    private javax.swing.JLabel lbHeaderDate;
    private javax.swing.JLabel lbLogo;
    private javax.swing.JLabel lblEmplName;
    private ui.menu.Menu menu;
    private JPanel pCenter;
    private JPanel pNorth;
    private JPanel pWest;
    private ui.scroll.win11.ScrollPaneWin11 scrollPaneWin;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if((o.equals(btnLogout))){

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    Confirm dialog = new Confirm(HomePage.this, true, "Thông báo", "Bạn có chắc chắn muốn đăng xuất?", "src/main/java/ui/dialog/logout.jpg", "Đăng xuất", "Hủy bỏ");
                    dialog.showAlert();

                    int response = dialog.getResponse();
                    if(response == 1) {
                        StaticProcess.loginSuccess = false;
                        WelcomeMyApp.main(null);
                        HomePage.this.dispose();
//                        new ui.dialog.Message(HomePage.this, true, "Thông báo", "Đăng xuất thành công", "src/main/java/ui/dialog/done.png").showAlert();
                    }
                }
            });
        }
        }
}
    // End of variables declaration//GEN-END:variables

