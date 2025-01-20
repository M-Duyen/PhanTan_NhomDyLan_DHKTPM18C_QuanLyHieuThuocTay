package ui.main;

import dao.Product_DAO;
import dao.Unit_DAO;
import entity.Enum_PackagingUnit;
import entity.Product;
import ui.button.Button;
import ui.dialog.Message;
import ui.dialog.ProductConfirm;
import ui.forms.TempOrderForm;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CreateOrder extends JPanel {
    private HomePage homePage;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
       private Map<String, ScheduledFuture<?>> tabRemovalTasks = new HashMap<>();
    
    public CreateOrder(HomePage homePage) {
        this.homePage = homePage;
        initComponents();
        initFirstTab();
        
        pCenter.requestFocusInWindow();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pCenter = new JPanel();
        tabbedPane = new ui.tabbed.TabbedPaneCustom();
        txtSearch = new ui.textfield.TextField();
        btnSearch = new Button();
        btnAddTab = new Button();

        setPreferredSize(new java.awt.Dimension(1620, 1000));
        setLayout(new java.awt.BorderLayout());

        pCenter.setBackground(new java.awt.Color(242, 249, 255));

        tabbedPane.setBackground(new java.awt.Color(242, 249, 255));
        tabbedPane.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtSearch.setForeground(new java.awt.Color(153, 153, 153));
        txtSearch.setText("Nhập mã sản phẩm ...");
        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSearch.setMargin(new Insets(3, 6, 3, 6));
        txtSearch.setName(""); // NOI18N
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFocusLost(evt);
            }
        });
        txtSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(102, 204, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon("src/main/java/ui/button/magnifying-glass_32.png")); // NOI18N
        btnSearch.setMargin(new Insets(2, 10, 3, 10));
        btnSearch.setRound(40);
        btnSearch.setShadowColor(new java.awt.Color(102, 204, 255));
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnAddTab.setBackground(new java.awt.Color(102, 204, 255));
        btnAddTab.setIcon(new javax.swing.ImageIcon("src/main/java/ui/button/add.png")); // NOI18N
        btnAddTab.setRound(50);
        btnAddTab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAddTabActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pCenterLayout = new javax.swing.GroupLayout(pCenter);
        pCenter.setLayout(pCenterLayout);
        pCenterLayout.setHorizontalGroup(
            pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pCenterLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1039, Short.MAX_VALUE))
                    .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        pCenterLayout.setVerticalGroup(
            pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCenterLayout.createSequentialGroup()
                        .addComponent(btnAddTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 934, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(pCenter, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        String searchText = txtSearch.getText().trim();
        if(!searchText.isEmpty()){
            if(tabbedPane.getSelectedComponent() instanceof TempOrderForm) {
                TempOrderForm currentTab = (TempOrderForm) tabbedPane.getSelectedComponent();
                Product product = new Product_DAO().getProduct_ByBarcode(searchText);
                if(product != null){
                    ProductConfirm productConfirm = new ProductConfirm(homePage, product, true);
                    openProductConfirm(productConfirm, product, currentTab, false);
                } else {
                    new Message(homePage, true, "Thông báo", "Không tìm thấy sản phẩm", "src/main/java/ui/dialog/warning.png").showAlert();
                }
            } else {
                new Message(homePage, true, "Thông báo", "Không có tab hợp lệ được chọn", "src/main/java/ui/dialog/warning.png").showAlert();
            }
        } else {
            new Message(homePage, true, "Thông báo", "Chưa nhập mã vạch", "src/main/java/ui/dialog/warning.png").showAlert();
        }
        txtSearch.setText("");
    }//GEN-LAST:event_txtSearchActionPerformed

    /**
     * Mở dialog ProductConfirm (flag == true - editing, false - no-edit)
     *
     * @param product
     * @param currentTab
     */
    public void openProductConfirm(ProductConfirm productConfirm, Product product, TempOrderForm currentTab, boolean flag){
        productConfirm.showAlert();

        if(productConfirm.flag) {
            Enum_PackagingUnit unit = productConfirm.getEnumUnit();
            int qty = productConfirm.getQuantity();
            double sellPrice = productConfirm.getSellPrice();

            if(qty > 0) {
                String productID = product.getProductID();

                int index = currentTab.findRowByProductID(productID, unit);

                if(index != -1){
                    int quantity = currentTab.getQuantityProductInTable(productID) + qty;

                    if(flag) {
                        currentTab.updateProductRow(index, 3, qty, sellPrice);
                    } else {
                        currentTab.updateProductRow(index, 3, quantity, sellPrice);
                    }
                    currentTab.updatePanelNotice();
                    currentTab.updateTXTCustNeedPay();
                    currentTab.updateTXTChange();
                    currentTab.updatePointOrder();
                } else {
                    addRow(currentTab, product, qty, unit);
                    currentTab.updatePanelNotice();
                    currentTab.updateTXTCustNeedPay();
                    currentTab.updateTXTChange();
                    currentTab.updatePointOrder();
                }
            } else {
                new Message(homePage, true, "Thông báo", "Hết hàng", "src/main/java/ui/dialog/warning.png").showAlert();
            }
        }
    }

    private void btnSearchActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchText = txtSearch.getText().trim();
        if(!searchText.isEmpty()){
            if(tabbedPane.getSelectedComponent() instanceof TempOrderForm) {
                TempOrderForm currentTab = (TempOrderForm) tabbedPane.getSelectedComponent();
                Product product = new Product_DAO().getProduct_ByBarcode(searchText);
                if(product != null){
                    ProductConfirm productConfirm = new ProductConfirm(homePage, product, true);
                    openProductConfirm(productConfirm, product, currentTab, false);
                } else {
                    new Message(homePage, true, "Thông báo", "Không tìm thấy sản phẩm", "src/main/java/ui/dialog/warning.png").showAlert();
                }
            } else {
                new Message(homePage, true, "Thông báo", "Không có tab hợp lệ được chọn", "src/main/java/ui/dialog/warning.png").showAlert();
            }
        } else {
            new Message(homePage, true, "Thông báo", "Chưa nhập mã vạch", "src/main/java/ui/dialog/warning.png").showAlert();
        }
        txtSearch.setText("");
    }//GEN-LAST:event_btnSearchActionPerformed

    private List<String> availableCounts = new ArrayList<>(List.of("1", "2", "3", "4", "5"));
    private List<String> usedCounts = new ArrayList<>();

    private void btnAddTabActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAddTabActionPerformed
        if (!availableCounts.isEmpty()) {
            String countString = availableCounts.remove(0);
            usedCounts.add(countString);

            tabbedPane.addTab("Hóa đơn " + countString, new TempOrderForm(homePage));
            int newTabIndex = tabbedPane.getTabCount() - 1;
            addCloseButtonToTab(newTabIndex);
               
            scheduleTabRemoval(countString, newTabIndex);
        } else {
            new Message(homePage, true, "Thông báo", "Chỉ cho lưu tạm tối đa 5 hóa đơn", "src/main/java/ui/dialog/warning.png").showAlert();
        }
    }//GEN-LAST:event_btnAddTabActionPerformed

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        if(txtSearch.getText().equals("Nhập mã sản phẩm ...")){
            txtSearch.setText("");
        }
    }//GEN-LAST:event_txtSearchFocusGained

    private void txtSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusLost
        if(txtSearch.getText().equals("")){
            txtSearch.setText("Nhập mã sản phẩm ...");
        }
    }//GEN-LAST:event_txtSearchFocusLost

    /**
     * Khởi tạo tab khi mở trang CreatOrder
     *
     */
    public void initFirstTab(){
        String countString = availableCounts.remove(0);
        usedCounts.add(countString);

        tabbedPane.addTab("Hóa đơn " + countString, new TempOrderForm(homePage));
        int newTabIndex = tabbedPane.getTabCount() - 1;
        addCloseButtonToTab(newTabIndex);
        
        scheduleTabRemoval(countString, newTabIndex);
    }

    /**
     * Thêm dấu X để đóng tab
     *
     * @param tabIndex
     */
    private void addCloseButtonToTab(int tabIndex) {
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        JLabel titleLabel = new JLabel(tabbedPane.getTitleAt(tabIndex));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        titleLabel.setOpaque(false);
        tabPanel.add(titleLabel);

        Button closeButton = new Button("X");
        closeButton.setMargin(new Insets(0, 5, 0, 5));
        closeButton.setFocusable(false); 
        closeButton.setOpaque(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setContentAreaFilled(false);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usedCounts.size() > 1){
                    int i = tabbedPane.indexOfTabComponent(tabPanel);
                    if (i != -1) {
                        String tabTitle = tabbedPane.getTitleAt(i).replace("Hóa đơn ", "").trim();
                        usedCounts.remove(tabTitle);
                        availableCounts.add(tabTitle);
                        Collections.sort(availableCounts);
                        tabbedPane.remove(i);
                    }
                }
            }
        });

        tabPanel.add(closeButton);
        tabPanel.setOpaque(false);
        tabbedPane.setTabComponentAt(tabIndex, tabPanel);
    }

    /**
     *  Nếu hơn 30p thì tab sẽ tự động xóa
     *
     * @param tabTitle
     * @param tabIndex
     */
    private void scheduleTabRemoval(String tabTitle, int tabIndex) {
    ScheduledFuture<?> future = scheduler.schedule(() -> {
        SwingUtilities.invokeLater(() -> {
                int index = findTabIndexByTitle(tabTitle);
                if (index != -1 && tabbedPane.getTabCount() > 1) {
                    removeTabAt(index);
                } else if (tabbedPane.getTabCount() == 1) {
                    replaceLastTabWithInvoiceOne();
                }
            });
        }, 30, TimeUnit.MINUTES);

        tabRemovalTasks.put(tabTitle, future);
    }

    /**
     * Xóa tab
     *
     * @param tabIndex
     */
    private void removeTabAt(int tabIndex) {
        String tabTitle = tabbedPane.getTitleAt(tabIndex).replace("Hóa đơn ", "").trim();
        usedCounts.remove(tabTitle); 
        availableCounts.add(tabTitle);
        Collections.sort(availableCounts);
        tabbedPane.remove(tabIndex);

        ScheduledFuture<?> future = tabRemovalTasks.remove(tabTitle);
        if (future != null) {
            future.cancel(false);
        }
    }

    /**
     * Tìm index của tabPane
     *
     * @param tabTitle
     * @return
     */
    private int findTabIndexByTitle(String tabTitle) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).contains(tabTitle)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Nếu xóa tất cả tab sẽ reset về 1
     *
     */
    private void replaceLastTabWithInvoiceOne() {
        if (tabbedPane.getTabCount() == 1) {
            removeTabAt(0);
            String newTabTitle = "Hóa đơn 1";
            usedCounts.add("1");
            availableCounts.remove("1");
            tabbedPane.addTab(newTabTitle, new TempOrderForm(homePage));
            addCloseButtonToTab(tabbedPane.getTabCount() - 1);
        }
    }

    /**
     *
     * Thêm hàng vào bảng
     * @param tempOrderForm
     * @param product
     * @param quantity
     * @param unit
     */
    private void addRow(TempOrderForm tempOrderForm, Product product, int quantity, Enum_PackagingUnit unit){
        DecimalFormat df = new DecimalFormat("#,##0.00 VND");
        Object[] rowData = {product.getProductID(), product.getProductName(), new Unit_DAO().convertUnit(unit), quantity, df.format(product.getSellPrice(unit)), df.format(quantity * product.getSellPrice(unit))}; //product.getSellPrice(), product.getSellPrice()
        tempOrderForm.addProductRow(rowData);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Button btnAddTab;
    private Button btnSearch;
    private JPanel pCenter;
    private javax.swing.JTabbedPane tabbedPane;
    private ui.textfield.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
