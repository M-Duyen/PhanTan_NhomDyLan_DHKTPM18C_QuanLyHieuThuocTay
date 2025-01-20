package ui.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import dao.*;
import database.ConnectDB;
import entity.*;
import ui.dialog.Message;
import ui.table.TableCustom;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.awt.Color.RED;

public class CategorySearch extends javax.swing.JPanel {
    private final ArrayList<Product> proFetchList;
    private HomePage homePage;
    private ArrayList<Product> productsListTemp;

    public CategorySearch(HomePage homePage) {
        this.homePage = homePage;
        ConnectDB.getInstance().connect();
        initComponents();
        JTableHeader theader = tableProduct.getTableHeader();
        theader.setFont(new java.awt.Font("Segoe UI", 0, 18));
        TableCustom.apply(jScrollPane_tableProduct, TableCustom.TableType.MULTI_LINE);
        tableProduct.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setupTable();
        showDataComboBoxVendor();
        showDataComboBoxCategory();
        showDataComboBoxAdmintrationRoute();
        proFetchList = product_dao.fetchProducts();
    }


    private void setupTable() {
        JTableHeader theader = tableProduct.getTableHeader();
        theader.setFont(new java.awt.Font("Segoe UI", 0, 18));
        tableProduct.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        if (tableProduct.getColumnModel().getColumnCount() > 0) {
            tableProduct.getColumnModel().getColumn(0).setPreferredWidth(200);
            tableProduct.getColumnModel().getColumn(1).setPreferredWidth(400);
            tableProduct.getColumnModel().getColumn(2).setPreferredWidth(300);
            tableProduct.getColumnModel().getColumn(3).setPreferredWidth(200);
            tableProduct.getColumnModel().getColumn(4).setPreferredWidth(200);
            tableProduct.getColumnModel().getColumn(5).setPreferredWidth(300);
            tableProduct.getColumnModel().getColumn(6).setPreferredWidth(300);
            tableProduct.getColumnModel().getColumn(7).setPreferredWidth(300);
            tableProduct.getColumnModel().getColumn(8).setPreferredWidth(300);
            tableProduct.getColumnModel().getColumn(9).setPreferredWidth(300);
            tableProduct.getColumnModel().getColumn(10).setPreferredWidth(300);
            tableProduct.getColumnModel().getColumn(11).setPreferredWidth(300);
            tableProduct.getColumnModel().getColumn(12).setPreferredWidth(400);
            tableProduct.getColumnModel().getColumn(13).setPreferredWidth(400);
        }
    }

    private void initComponents() {
        date = new ui.datechooser.DateChooser();
        tableCustom = new TableCustom();
        pCenter = new javax.swing.JPanel();
        txtDate = new ui.textfield.TextField();
        btnCalendar = new ui.button.Button();
        btnAdd = new ui.button.Button();
        cbbCategory = new ui.combobox.Combobox();
        cbbVendor = new ui.combobox.Combobox();
        cbbMethod = new ui.combobox.Combobox();
        tableScrollButton_Product = new ui.table.TableScrollButton();
        jScrollPane_tableProduct = new javax.swing.JScrollPane();
        tableProduct = new JTable();
        cbbAdministration = new ui.combobox.Combobox();
        txtSearch = new ui.textfield.TextField();
        btnSearch = new ui.button.Button();
        lbDate = new javax.swing.JLabel();

        date.setForeground(new java.awt.Color(102, 204, 255));

        date.setTextRefernce(txtDate);


        setPreferredSize(new java.awt.Dimension(1620, 1000));

        pCenter.setBackground(new java.awt.Color(242, 249, 255));
        pCenter.setPreferredSize(new java.awt.Dimension(1600, 1000));


        txtDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtDate.setMargin(new java.awt.Insets(3, 6, 3, 6));
        txtDate.setName(""); // NOI18N
        txtDate.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });

        btnCalendar.setForeground(new java.awt.Color(255, 255, 255));
        btnCalendar.setIcon(new javax.swing.ImageIcon("src/main/java/ui/button/calendar.png")); // NOI18N
        btnCalendar.setPreferredSize(new java.awt.Dimension(64, 64));
        btnCalendar.setRound(20);
        btnCalendar.setShadowColor(new java.awt.Color(255, 255, 255));
        btnCalendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalendarActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(102, 204, 255));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Thêm sản phẩm");
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAdd.setIconTextGap(2);
        btnAdd.setPreferredSize(new java.awt.Dimension(64, 64));
        btnAdd.setRound(30);
        btnAdd.setShadowColor(new java.awt.Color(0, 0, 0));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        cbbCategory.setBackground(new java.awt.Color(242, 249, 255));
        cbbCategory.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbbCategory.setForeground(new java.awt.Color(102, 102, 102));
        cbbCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[]{""}));
        cbbCategory.setSelectedIndex(-1);
        cbbCategory.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbbCategory.setLabeText("Danh mục");
        cbbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (txtSearch.getText().equals("Nhập tiêu chí tìm kiếm ...")) {
                    cbbCategoryActionPerformed(evt);
                }

            }
        });

        cbbVendor.setBackground(new java.awt.Color(242, 249, 255));
        cbbVendor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbbVendor.setForeground(new java.awt.Color(102, 102, 102));
        cbbVendor.setModel(new javax.swing.DefaultComboBoxModel(new String[]{""}));
        cbbVendor.setSelectedIndex(-1);
        cbbVendor.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbbVendor.setLabeText("Nhà cung cấp");
        cbbVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (txtSearch.getText().equals("Nhập tiêu chí tìm kiếm ...")) {
                    cbbVendorActionPerformed(evt);
                }
            }
        });


        cbbMethod.setBackground(new java.awt.Color(242, 249, 255));
        cbbMethod.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbbMethod.setForeground(new java.awt.Color(102, 102, 102));
        cbbMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"", "Sản phẩm sắp hết hạn", "Sản phẩm tồn kho thấp"}));
        cbbMethod.setSelectedIndex(-1);
        cbbMethod.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbbMethod.setLabeText("Khác");
        cbbMethod.setName(""); // NOI18N
        cbbMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMethodActionPerformed(evt);
            }

        });

        tableScrollButton_Product.setMinimumSize(new java.awt.Dimension(200, 15));
        tableScrollButton_Product.setPreferredSize(new java.awt.Dimension(1190, 400));

        jScrollPane_tableProduct.setBackground(new java.awt.Color(221, 221, 221));
        jScrollPane_tableProduct.setBorder(null);
        jScrollPane_tableProduct.setPreferredSize(new java.awt.Dimension(950, 400));

        tableProduct.setBackground(new java.awt.Color(242, 249, 255));
        tableProduct.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Mã sản phẩm", "Tên sản phẩm", "Số đăng ký", "Ngày hết hạn", "Đơn vị", "Số lượng tồn", "Giá bán", "Hoạt chất", "Đường dùng", "Đơn vị nhập", "Dưỡng chất chính", "Loại vật tư y tế", "Danh mục", "Nhà cung cấp"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableProduct.setGridColor(new java.awt.Color(218, 247, 249));
        tableProduct.setRequestFocusEnabled(false);
        tableProduct.setShowVerticalLines(true);
        jScrollPane_tableProduct.setViewportView(tableProduct);

        tableScrollButton_Product.add(jScrollPane_tableProduct, java.awt.BorderLayout.CENTER);

        cbbAdministration.setBackground(new java.awt.Color(242, 249, 255));
        cbbAdministration.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbbAdministration.setForeground(new java.awt.Color(102, 102, 102));
        cbbAdministration.setModel(new javax.swing.DefaultComboBoxModel(new String[]{""}));
        cbbAdministration.setSelectedIndex(-1);
        cbbAdministration.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbbAdministration.setLabeText("Đường dùng");
        cbbAdministration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (txtSearch.getText().equals("Nhập tiêu chí tìm kiếm ...")) {
                    cbbAdministrationActionPerformed(evt);
                }
            }
        });

        txtSearch.setForeground(new java.awt.Color(153, 153, 153));
        txtSearch.setText("Nhập tiêu chí tìm kiếm ...");
        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (!txtSearch.getText().equals("Nhập tiêu chí tìm kiếm ...")) {
//                    ArrayList<Product> result = product_dao.searchProductWithCriteria(txtSearch.getText(), cbbCategory, cbbVendor, cbbAdministration);
//                    if (result.isEmpty()) {
//                        new Message(homePage, true, "Thông báo", "Hết hàng", "src/main/java/ui/dialog/warning.png").showAlert(); new Message(homePage, true, "Thông báo", "Không tìm thấy", "src/main/java/ui/dialog/warning.png").showAlert();
//                    } else {
//                        tableProduct.setVisible(true);
//                        showTable(result);
//                    }
//                }
//                pCenter.revalidate(); // Cập nhật lại giao diện
//                pCenter.repaint();
            }
        });
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFocusLost(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(102, 204, 255));
        btnSearch.setForeground(new java.awt.Color(0, 0, 0));
        btnSearch.setIcon(new javax.swing.ImageIcon("src/main/java/ui/button/magnifying-glass_32.png")); // NOI18N
        btnSearch.setRound(20);
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtSearch.getText().equals("Nhập tiêu chí tìm kiếm ...")) {
                    ArrayList<Product> result = product_dao.searchProductWithCriteria(txtSearch.getText(), cbbCategory, cbbVendor, cbbAdministration);
                    DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
                    if (result.isEmpty()) {
//                        new Message(homePage, true, "Thông báo", "Không tìm thấy", "src/main/java/ui/dialog/warning.png").showAlert();
                        model.addRow(new Object[]{"...", "...", "...", "...", "...", "...", "...", "...", "...", "...", "...", "...", "...", "..."});
                    } else {
                        tableProduct.setVisible(true);
                        showTable(result);
                    }
                }
                pCenter.revalidate(); // Cập nhật lại giao diện
                pCenter.repaint();
            }
        });
        btnSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                }
            }
        });


        lbDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbDate.setText("Lọc theo ngày");

        javax.swing.GroupLayout pCenterLayout = new javax.swing.GroupLayout(pCenter);
        pCenter.setLayout(pCenterLayout);
        pCenterLayout.setHorizontalGroup(
                pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCenterLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pCenterLayout.createSequentialGroup()
                                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbDate)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(221, 221, 221)
                                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(123, 123, 123))
                                        .addGroup(pCenterLayout.createSequentialGroup()
                                                .addComponent(cbbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(31, 31, 31)
                                                .addComponent(cbbVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(cbbAdministration, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(31, 31, 31)
                                                .addComponent(cbbMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(505, 505, 505))))
                        .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCenterLayout.createSequentialGroup()
                                        .addContainerGap(28, Short.MAX_VALUE)
                                        .addComponent(tableScrollButton_Product, javax.swing.GroupLayout.PREFERRED_SIZE, 1572, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(115, 115, 115)))
        );
        pCenterLayout.setVerticalGroup(
                pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pCenterLayout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pCenterLayout.createSequentialGroup()
                                                .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(btnCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGap(18, 18, 18)
                                                .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(cbbVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cbbAdministration, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cbbMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cbbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(pCenterLayout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                        .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pCenterLayout.createSequentialGroup()
                                        .addGap(226, 226, 226)
                                        .addComponent(tableScrollButton_Product, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(84, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 1620, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pCenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Product> proListByDate = product_dao.getProductListByCriterious(convertDateFormat(txtDate.getText()));
        showTable(proListByDate);

    }

    private void btnCalendarActionPerformed(java.awt.event.ActionEvent evt) {
        date.showPopup();
        ArrayList<Product> proListByDate = product_dao.getProductListByCriterious(convertDateFormat(txtDate.getText()));
        showTable(proListByDate);
    }

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        AddProduct addProduct = new AddProduct();
        homePage.updateCurretPanel(addProduct);

        pCenter.removeAll();

        pCenter.setLayout(new BorderLayout());
        pCenter.add(addProduct, BorderLayout.CENTER);

        pCenter.revalidate();
        pCenter.repaint();
    }

    private void cbbCategoryActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Product> proListByCategory = product_dao.getProductListByCriterious((String) cbbCategory.getSelectedItem());
        showTable(proListByCategory);
    }

    private void cbbVendorActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Product> proListByVendor = product_dao.getProductListByCriterious((String) cbbVendor.getSelectedItem());
        showTable(proListByVendor);
    }

    private void cbbMethodActionPerformed(java.awt.event.ActionEvent evt) {
        searchByOtherCriterious();
    }

    private void cbbAdministrationActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Product> proListByAdmin = product_dao.getProductListByCriterious((String) cbbAdministration.getSelectedItem());
        showTable(proListByAdmin);
    }

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {
        if (txtSearch.getText().equals("Nhập tiêu chí tìm kiếm ...")) {
            txtSearch.setText("");
        }
    }

    private void txtSearchFocusLost(java.awt.event.FocusEvent evt) {
        if (txtSearch.getText().equals("")) {
            txtSearch.setText("Nhập tiêu chí tìm kiếm ...");
        }
    }


    // Variables declaration - do not modify
    private ui.button.Button btnAdd;
    private ui.button.Button btnCalendar;
    private ui.button.Button btnSearch;
    private ui.combobox.Combobox cbbAdministration;
    private ui.combobox.Combobox cbbCategory;
    private ui.combobox.Combobox cbbMethod;
    private ui.combobox.Combobox cbbVendor;
    private ui.datechooser.DateChooser date;
    private javax.swing.JScrollPane jScrollPane_tableProduct;
    private javax.swing.JLabel lbDate;
    private javax.swing.JPanel pCenter;
    private TableCustom tableCustom;
    private JTable tableProduct;
    private ui.table.TableScrollButton tableScrollButton_Product;
    private ui.textfield.TextField txtDate;
    private ui.textfield.TextField txtSearch;
    Product_DAO product_dao = new Product_DAO();


    public void showTable(ArrayList<Product> arrayList) {
        DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
        model.setRowCount(0);

        ArrayList<Medicine> medicineList = (ArrayList<Medicine>) arrayList.stream().filter(x -> x instanceof Medicine).map(product -> (Medicine) product).collect(Collectors.toList());
        List<MedicalSupplies> suppliesList = arrayList.stream().filter(x -> x instanceof MedicalSupplies).map(product -> (MedicalSupplies) product).collect(Collectors.toList());
        List<FunctionalFood> funtionFoodList = arrayList.stream().filter(x -> x instanceof FunctionalFood).map(product -> (FunctionalFood) product).collect(Collectors.toList());
        for (Medicine medicine : medicineList) {
            for (Map.Entry<PackagingUnit, Double> entry : medicine.getUnitPrice().entrySet()) {
                model.addRow(new Object[]{
                        medicine.getProductID(),
                        medicine.getProductName(),
                        medicine.getRegistrationNumber(),
                        medicine.getEndDate(),
                        entry.getKey().name(),
                        medicine.getInStockByUnit(entry.getKey()),
                        medicine.getSellPrice(entry.getKey()),
                        medicine.getActiveIngredient(),
                        medicine.getAdministrationRoute().getAdministrationID(),
                        medicine.getConversionUnit(),
                        "", "",
                        medicine.getCategory().getCategoryName(),
                        medicine.getVendor().getVendorName()
                });
            }
        }

        for (MedicalSupplies supplies : suppliesList) {
            for (Map.Entry<PackagingUnit, Double> entry : supplies.getUnitPrice().entrySet()) {
                model.addRow(new Object[]{
                        supplies.getProductID(),
                        supplies.getProductName(),
                        supplies.getRegistrationNumber(),
                        supplies.getEndDate(),
                        entry.getKey().name(), //supplies.getQuantityInStock()
                        supplies.getInStockByUnit(entry.getKey()),
                        supplies.getSellPrice(entry.getKey()),
                        "", "", "", "",
                        supplies.getMedicalSupplyType(),
                        supplies.getCategory().getCategoryName(),
                        supplies.getVendor().getVendorName()
                });
            }
        }
        for (FunctionalFood functionalFood : funtionFoodList) {
            for (Map.Entry<PackagingUnit, Double> entry : functionalFood.getUnitPrice().entrySet()) {
                model.addRow(new Object[]{
                        functionalFood.getProductID(),
                        functionalFood.getProductName(),
                        functionalFood.getRegistrationNumber(),
                        functionalFood.getEndDate(),
                        entry.getKey().name(), //functionalFood.getQuantityInStock()
                        functionalFood.getInStockByUnit(entry.getKey()),
                        functionalFood.getSellPrice(entry.getKey()),
                        "", "", "",
                        functionalFood.getMainNutrients(),
                        "",
                        functionalFood.getCategory().getCategoryName(),
                        functionalFood.getVendor().getVendorName()
                });
            }
        }
    }

    public void showDataComboBoxVendor() {
        Vendor_DAO vendor_dao = new Vendor_DAO();
        List<Vendor> list = vendor_dao.getVendorList();

        Set<Vendor> uniqueValues = new LinkedHashSet<>(list);
        List<Vendor> uniqueList = new ArrayList<>(uniqueValues);

        for (Vendor vendor : uniqueList) {
            cbbVendor.addItem(vendor.getVendorName());
        }

    }

    public void showDataComboBoxCategory() {
        Category_DAO category_dao = new Category_DAO();
        List<Category> list = category_dao.getCategoryList();

        Set<Category> uniqueValues = new LinkedHashSet<>(list);
        List<Category> uniqueList = new ArrayList<>(uniqueValues);

        for (Category category : uniqueList) {
            cbbCategory.addItem(category.getCategoryName());
        }
    }

    public void showDataComboBoxAdmintrationRoute() {
        AdministrationRoute_DAO administrationRoute_dao = new AdministrationRoute_DAO();
        List<AdministrationRoute> list = administrationRoute_dao.getAllAdministrationRoute();

        Set<AdministrationRoute> uniqueValues = new LinkedHashSet<>(list);
        List<AdministrationRoute> uniqueList = new ArrayList<>(uniqueValues);

        for (AdministrationRoute administrationRoute : uniqueList) {
            cbbAdministration.addItem(administrationRoute.getAdministrationID());
        }
    }

    private static String convertDateFormat(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;

        try {
            date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void searchByOtherCriterious() {
        Product_DAO product_dao = new Product_DAO();
        String searchByOther = (String) cbbMethod.getSelectedItem();
        if (searchByOther != null) {
            if (searchByOther.equals("Sản phẩm sắp hết hạn")) {
                ArrayList<Product> proNearExpire = product_dao.getProductListNearExpire();
                showTable(proNearExpire);

            } else if (searchByOther.equals("Sản phẩm tồn kho thấp")) {
                DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
                ArrayList<Product> lowStockProductsList = product_dao.getLowStockProducts(25);
                if (lowStockProductsList.isEmpty()) {
                    model.addRow(new Object[]{"...", "...", "...", "...", "...", "...", "...", "...", "...", "...", "...", "...", "...", "..."});
                } else {
                    showTable(lowStockProductsList);
                }
            } else if (searchByOther.equals("Tất cả")) {
                ArrayList<Product> productArrayList = proFetchList;
                showTable(productArrayList);
            }
        } else {
            System.out.println("Bị null");
        }
    }
}

