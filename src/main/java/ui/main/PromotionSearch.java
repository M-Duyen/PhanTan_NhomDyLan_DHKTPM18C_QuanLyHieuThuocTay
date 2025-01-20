package ui.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import dao.PromotionType_DAO;
import dao.Promotion_DAO;
import entity.Promotion;
import entity.PromotionType;
import entity.Vendor;
import ui.button.Button;
import ui.combobox.Combobox;
import ui.panel.PanelRound;
import ui.table.TableCustom;
import ui.textfield.TextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PromotionSearch extends JPanel{
    
    public PromotionSearch() {
        initComponents();
        promotionType_dao = new PromotionType_DAO();
        promotion_dao = new Promotion_DAO();
        setupTable();
        TableCustom.apply(scrollPane, TableCustom.TableType.MULTI_LINE);
        loadCbb();
        cbbPromoType.setSelectedIndex(-1);
        cbbPromoType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPromotion();
            }
        });
        cbbStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPromotion();
            }
        });
        txtSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                searchPromotion();
            }
        });
    }
    
    private void setupTable() {
        JTableHeader theader = tableKhuyenMai.getTableHeader();
        theader.setFont(new Font("Segoe UI", 0, 18));   
        tableKhuyenMai.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private void loadCbb(){
        ArrayList<PromotionType> listPromotionType = new ArrayList<>();
        listPromotionType = promotionType_dao.getPromotionTypeList();
        for( PromotionType promotionType : listPromotionType){
            cbbPromoType.addItem(promotionType.getPromotionTypeName());
        }
    }
    private void loadTable(ArrayList<Promotion> promotionList) {
        DefaultTableModel model = (DefaultTableModel) tableKhuyenMai.getModel();
        model.setRowCount(0);
        for (Promotion promotion : promotionList) {
            model.addRow(new Object[]{
                    promotion.getPromotionID(),
                    promotion.getPromotionName(),
                    promotion.getStartDate(),
                    promotion.getEndDate(),
                    promotion.getDiscount(),
                    promotion.isStats()?"Đang áp dụng":"Ngừng áp dụng",
                    promotion.getPromotionType().getPromotionTypeName()
            });
        }
    }

    private void searchPromotion() {
        // Xóa bảng hiện tại và thêm dữ liệu tìm kiếm
        DefaultTableModel model = (DefaultTableModel) tableKhuyenMai.getModel();
        model.setRowCount(0); // Xóa các dòng hiện tại

        String type = (String) cbbPromoType.getSelectedItem();
        String stt = (String) cbbStatus.getSelectedItem();
        String search = txtSearch.getText().trim();
        ArrayList<Promotion> listPromotions = new ArrayList<>();

        // Nếu tất cả đều rỗng
        if ((type == null || type.isEmpty()) && (stt == null || stt.isEmpty()) && search.isEmpty()) {
            listPromotions = promotion_dao.getPromotionList(); // Lấy tất cả khuyến mãi
        }
        //  Nếu chỉ có `type` không rỗng
        else if (type != null && !type.isEmpty() && (stt == null || stt.isEmpty()) && search.isEmpty()) {
            listPromotions = promotion_dao.getPromotionListByCriterous(type); // Lọc theo loại
        }
        // Nếu chỉ có `stt` không rỗng
        else if ((type == null || type.isEmpty()) && stt != null && !stt.isEmpty() && search.isEmpty()) {
            listPromotions = promotion_dao.getPromotionList(); // Lấy tất cả khuyến mãi, sau đó lọc theo trạng thái
            boolean status = stt.equals("Đang áp dụng");
            ArrayList<Promotion> listPromotionsByStatus = new ArrayList<>();
            for (Promotion promotion : listPromotions) {
                if (promotion.isStats() == status) {
                    listPromotionsByStatus.add(promotion);
                }
            }
            listPromotions = listPromotionsByStatus;
        }
        // Nếu chỉ có `search` không rỗng
        else if ((type == null || type.isEmpty()) && (stt == null || stt.isEmpty()) && !search.isEmpty()) {
            listPromotions = promotion_dao.getPromotionList(); // Lấy tất cả khuyến mãi, sau đó lọc theo từ khóa
            ArrayList<Promotion> listPromotionsBySearch = new ArrayList<>();
            for (Promotion promotion : listPromotions) {
                if (promotion.getPromotionID().toLowerCase().contains(search.toLowerCase()) ||
                        promotion.getPromotionName().toLowerCase().contains(search.toLowerCase())) {
                    listPromotionsBySearch.add(promotion);
                }
            }
            listPromotions = listPromotionsBySearch;
        }
        // Nếu `type` và `stt` không rỗng, `search` rỗng
        else if (type != null && !type.isEmpty() && stt != null && !stt.isEmpty() && search.isEmpty()) {
            listPromotions = promotion_dao.getPromotionListByCriterous(type); // Lọc theo loại
            boolean status = stt.equals("Đang áp dụng");
            ArrayList<Promotion> listPromotionsByStatus = new ArrayList<>();
            for (Promotion promotion : listPromotions) {
                if (promotion.isStats() == status) {
                    listPromotionsByStatus.add(promotion);
                }
            }
            listPromotions = listPromotionsByStatus;
        }
        // Nếu `type` và `search` không rỗng, `stt` rỗng
        else if (type != null && !type.isEmpty() && (stt == null || stt.isEmpty()) && !search.isEmpty()) {
            listPromotions = promotion_dao.getPromotionListByCriterous(type); // Lọc theo loại
            ArrayList<Promotion> listPromotionsBySearch = new ArrayList<>();
            for (Promotion promotion : listPromotions) {
                if (promotion.getPromotionID().toLowerCase().contains(search.toLowerCase()) ||
                        promotion.getPromotionName().toLowerCase().contains(search.toLowerCase())) {
                    listPromotionsBySearch.add(promotion);
                }
            }
            listPromotions = listPromotionsBySearch;
        }
        // Nếu `stt` và `search` không rỗng, `type` rỗng
        else if ((type == null || type.isEmpty()) && stt != null && !stt.isEmpty() && !search.isEmpty()) {
            listPromotions = promotion_dao.getPromotionList(); // Lấy tất cả khuyến mãi
            boolean status = stt.equals("Đang áp dụng");
            ArrayList<Promotion> listPromotionsByStatus = new ArrayList<>();
            for (Promotion promotion : listPromotions) {
                if (promotion.isStats() == status) {
                    listPromotionsByStatus.add(promotion);
                }
            }
            // Tiếp tục lọc theo từ khóa
            ArrayList<Promotion> listPromotionsBySearch = new ArrayList<>();
            for (Promotion promotion : listPromotionsByStatus) {
                if (promotion.getPromotionID().toLowerCase().contains(search.toLowerCase()) ||
                        promotion.getPromotionName().toLowerCase().contains(search.toLowerCase())) {
                    listPromotionsBySearch.add(promotion);
                }
            }
            listPromotions = listPromotionsBySearch;
        }
        // Trường hợp 8: Nếu tất cả đều không rỗng
        else {
            listPromotions = promotion_dao.getPromotionListByCriterous(type); // Lọc theo loại
            boolean status = stt.equals("Đang áp dụng");
            ArrayList<Promotion> listPromotionsByStatus = new ArrayList<>();
            for (Promotion promotion : listPromotions) {
                if (promotion.isStats() == status) {
                    listPromotionsByStatus.add(promotion);
                }
            }
            // Tiếp tục lọc theo từ khóa
            ArrayList<Promotion> listPromotionsBySearch = new ArrayList<>();
            for (Promotion promotion : listPromotionsByStatus) {
                if (promotion.getPromotionID().toLowerCase().contains(search.toLowerCase()) ||
                        promotion.getPromotionName().toLowerCase().contains(search.toLowerCase())) {
                    listPromotionsBySearch.add(promotion);
                }
            }
            listPromotions = listPromotionsBySearch;
        }

        // Hiển thị kết quả hoặc thông báo nếu không tìm thấy
        if (listPromotions.isEmpty()) {
            model.addRow(new Object[]{"...", "...", "...", "...", "...", "...", "..."});
        } else {
            loadTable(listPromotions);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pCenter = new PanelRound();
        cbbPromoType = new Combobox();
        cbbStatus = new Combobox();
        scrollPane = new JScrollPane();
        tableKhuyenMai = new JTable();
        lblTitle = new JLabel();
        txtSearch = new TextField();
        btnSearch = new Button();
        lblTitleList = new JLabel();

        setBackground(new Color(242, 249, 255));
        setPreferredSize(new Dimension(1620, 1000));
        setLayout(new BorderLayout());

        pCenter.setBackground(new Color(242, 249, 255));
        pCenter.setForeground(new Color(102, 204, 255));
        pCenter.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        pCenter.setPreferredSize(new Dimension(1600, 1000));

        cbbPromoType.setBackground(new Color(242, 249, 255));
        cbbPromoType.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        cbbPromoType.setLabeText("Loại khuyến mãi");
        cbbPromoType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cbbPromoTypeActionPerformed(evt);
            }
        });

        cbbStatus.setBackground(new Color(242, 249, 255));
        cbbStatus.setModel(new DefaultComboBoxModel(new String[] { "Đang áp dụng", "Ngừng áp dụng" }));
        cbbStatus.setSelectedIndex(-1);
        cbbStatus.setToolTipText("");
        cbbStatus.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        cbbStatus.setLabeText("Tình trạng");

        tableKhuyenMai.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        tableKhuyenMai.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khuyến mãi", "Tên khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc", "Giảm giá", "Trạng thái", "Thông tin loại khuyến mãi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(tableKhuyenMai);

        lblTitle.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new Color(102, 204, 255));
        lblTitle.setText("TÌM KIẾM KHUYẾN MÃI");

        txtSearch.setFont(new Font("Segoe UI", 0, 18)); // NOI18N

        btnSearch.setBackground(new Color(102, 204, 255));
        btnSearch.setForeground(new Color(255, 255, 255));
        btnSearch.setText("Tìm kiếm");
        btnSearch.setFont(new Font("Segoe UI", 1, 18)); // NOI18N

        lblTitleList.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        lblTitleList.setForeground(new Color(102, 204, 255));
        lblTitleList.setText("DANH SÁCH KHUYẾN MÃI");

        GroupLayout pCenterLayout = new GroupLayout(pCenter);
        pCenter.setLayout(pCenterLayout);
        pCenterLayout.setHorizontalGroup(
            pCenterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pCenterLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(pCenterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(pCenterLayout.createSequentialGroup()
                        .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                    .addGroup(pCenterLayout.createSequentialGroup()
                        .addComponent(cbbPromoType, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143)
                        .addComponent(cbbStatus, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, pCenterLayout.createSequentialGroup()
                .addContainerGap(619, Short.MAX_VALUE)
                .addGroup(pCenterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pCenterLayout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addGap(582, 582, 582))
                    .addGroup(GroupLayout.Alignment.TRAILING, pCenterLayout.createSequentialGroup()
                        .addComponent(lblTitleList)
                        .addGap(667, 667, 667))))
            .addGroup(pCenterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pCenterLayout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1516, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );
        pCenterLayout.setVerticalGroup(
            pCenterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pCenterLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblTitle)
                .addGap(36, 36, 36)
                .addGroup(pCenterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(pCenterLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbPromoType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(lblTitleList)
                .addContainerGap(632, Short.MAX_VALUE))
            .addGroup(pCenterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pCenterLayout.createSequentialGroup()
                    .addGap(382, 382, 382)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 552, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(46, Short.MAX_VALUE)))
        );

        add(pCenter, BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cbbPromoTypeActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cbbPromoTypeActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbbPromoTypeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Button btnSearch;
    private Combobox cbbPromoType;
    private Combobox cbbStatus;
    private JLabel lblTitle;
    private JLabel lblTitleList;
    private PanelRound pCenter;
    private JScrollPane scrollPane;
    private JTable tableKhuyenMai;
    private TextField txtSearch;
    private PromotionType_DAO promotionType_dao;
    private Promotion_DAO promotion_dao;
    // End of variables declaration//GEN-END:variables
}
