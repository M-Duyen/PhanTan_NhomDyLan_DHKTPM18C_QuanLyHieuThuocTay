package ui.main;

import dao.OrderDetails_DAO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ui.dialog.Message;
import ui.model.ModelDataPS;
import ui.model.ModelDataPS_Circle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ProductStatistics extends javax.swing.JPanel implements ActionListener {
    HomePage homePage;
    ArrayList<ModelDataPS> modelDataPSList = new ArrayList<>();
    ArrayList<ModelDataPS_Circle> listCircle1 = new ArrayList<>();
    ArrayList<ModelDataPS_Circle> listCircle2 = new ArrayList<>();

    public ProductStatistics(HomePage homePage) {
        this.homePage = homePage;
        initComponents();

        ArrayList<ModelDataPS> modelDataPSList = new OrderDetails_DAO().getProductStatistical(convertDateFormat(txtStartDate.getText()), convertDateFormat(txtEndDate.getText()));
        if(!modelDataPSList.isEmpty()){
            dashboardForm1.setFormattedDataset(dashboardForm1.createData(modelDataPSList, 0), dashboardForm1.barChart1);
            dashboardForm1.setFormattedDataset(dashboardForm1.createData(modelDataPSList, 1), dashboardForm1.barChart2);
        }

        ArrayList<ModelDataPS_Circle> listCircle1 = new OrderDetails_DAO().getProductStatics_ByType(convertDateFormat(txtStartDate.getText()), convertDateFormat(txtEndDate.getText()));
        if(!listCircle1.isEmpty()){
            dashboardForm11.setFormattedDataset(dashboardForm11.createData(listCircle1), dashboardForm11.pieChart1);
            dashboardForm11.pieChart1.startAnimation();
            dashboardForm11.repaint();
        }

        ArrayList<ModelDataPS_Circle> listCircle2 = new OrderDetails_DAO().getProductStatics_ByCategory(convertDateFormat(txtStartDate.getText()), convertDateFormat(txtEndDate.getText()));
        if(!listCircle2.isEmpty()){
            dashboardForm21.setFormattedDataset(dashboardForm21.createData(listCircle2), dashboardForm21.pieChart1);
            dashboardForm21.pieChart1.startAnimation();
            dashboardForm21.repaint();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateStart = new ui.datechooser.DateChooser();
        dateEnd = new ui.datechooser.DateChooser();
        pCenter = new javax.swing.JPanel();
        pCriterias = new ui.panel.PanelRound();
        lbStartDate = new javax.swing.JLabel();
        txtStartDate = new ui.textfield.TextField();
        lbEndDate = new javax.swing.JLabel();
        txtEndDate = new ui.textfield.TextField();
        btnRefresh = new ui.button.Button();
        btnPrintOut = new ui.button.Button();
        btnStartDate = new ui.button.Button();
        btnEndDate = new ui.button.Button();
        panelRound1 = new ui.panel.PanelRound();
        dashboardForm1 = new ui.forms.DashboardForm();
        dashboardForm11 = new ui.forms.DashboardForm1();
        dashboardForm21 = new ui.forms.DashboardForm2();

        dateStart.setForeground(new java.awt.Color(102, 204, 255));
        dateStart.setTextRefernce(txtStartDate);

        dateEnd.setForeground(new java.awt.Color(102, 204, 255));
        dateEnd.setTextRefernce(txtEndDate);

        setPreferredSize(new java.awt.Dimension(1620, 1000));
        setLayout(new java.awt.BorderLayout());

        pCenter.setBackground(new java.awt.Color(242, 249, 255));
        pCenter.setPreferredSize(new java.awt.Dimension(1600, 1000));

        pCriterias.setBackground(new java.awt.Color(255, 255, 255));
        pCriterias.setRoundBottomLeft(30);
        pCriterias.setRoundBottomRight(30);
        pCriterias.setRoundTopLeft(30);
        pCriterias.setRoundTopRight(30);

        lbStartDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbStartDate.setText("Thống kê từ ngày");

        txtStartDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lbEndDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbEndDate.setText("Thống kê đến ngày");

        txtEndDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnRefresh.setBackground(new java.awt.Color(102, 204, 255));
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Làm mới");
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        btnPrintOut.setBackground(new java.awt.Color(102, 204, 255));
        btnPrintOut.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintOut.setText("Xuất báo cáo thống kê");
        btnPrintOut.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPrintOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPrintOutActionPerformed(e);
            }
        });

        btnStartDate.setIcon(new javax.swing.ImageIcon( "src/main/java/ui/button/calendar.png")); // NOI18N
        btnStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDateActionPerformed(evt);
            }
        });

        btnEndDate.setIcon(new javax.swing.ImageIcon( "src/main/java/ui/button/calendar.png")); // NOI18N
        btnEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEndDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pCriteriasLayout = new javax.swing.GroupLayout(pCriterias);
        pCriterias.setLayout(pCriteriasLayout);
        pCriteriasLayout.setHorizontalGroup(
            pCriteriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCriteriasLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pCriteriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStartDate)
                    .addGroup(pCriteriasLayout.createSequentialGroup()
                        .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69)
                .addGroup(pCriteriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pCriteriasLayout.createSequentialGroup()
                        .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbEndDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnPrintOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pCriteriasLayout.setVerticalGroup(
            pCriteriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCriteriasLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pCriteriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbStartDate)
                    .addComponent(lbEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pCriteriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pCriteriasLayout.createSequentialGroup()
                        .addComponent(btnStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(63, 63, 63))
                    .addGroup(pCriteriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPrintOut, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pCriteriasLayout.createSequentialGroup()
                        .addComponent(btnEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setToolTipText("");
        panelRound1.setRoundBottomLeft(30);
        panelRound1.setRoundBottomRight(30);
        panelRound1.setRoundTopLeft(30);
        panelRound1.setRoundTopRight(30);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboardForm1, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboardForm11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dashboardForm21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addComponent(dashboardForm1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addComponent(dashboardForm11, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(dashboardForm21, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );

        javax.swing.GroupLayout pCenterLayout = new javax.swing.GroupLayout(pCenter);
        pCenter.setLayout(pCenterLayout);
        pCenterLayout.setHorizontalGroup(
            pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCenterLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pCriterias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pCenterLayout.setVerticalGroup(
            pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCenterLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pCriterias, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(pCenter, java.awt.BorderLayout.CENTER);

        LocalDate dateThreeMonthsAgo = LocalDate.now().minusMonths(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        txtStartDate.setText(dateThreeMonthsAgo.format(formatter));
        btnRefresh.addActionListener(this);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintOutActionPerformed(ActionEvent e) {
        String startDate = convertDateFormat(txtStartDate.getText().trim());
        String endDate = convertDateFormat(txtEndDate.getText().trim());
        if(startDate != null && endDate != null){
            String nameFile = "ProductStatistics_" + startDate.replace("-", "") + "_to_" + endDate.replace("-","");
            String path = ExcelFileChooser.getObject().chooseExcelFileSavePath(nameFile);
            if(path != null) {
                path = getUniqueFilePath(path);

                exportProductStatistic(startDate, endDate, path);
            } else {
                new Message(homePage, true, "THÔNG BÁO", "Thao tác xuất báo cáo đã bị hủy!", "src/main/java/ui/dialog/warning.png").showAlert();
            }
        } else {
            new Message(homePage, true, "THÔNG BÁO", "Chưa chọn ngày để thống kê!", "src/main/java/ui/dialog/warning.png").showAlert();
        }
    }

    public void exportProductStatistic(String startDate, String endDate, String filePath) {
        try {
            FileInputStream fis = new FileInputStream("src/main/java/ui/custom/Report_ProductStatistic.xlsx");
            Workbook workbook = new XSSFWorkbook(fis);

            //Sheet1: Biểu đồ cột ngang thống kê sản phẩm
            Sheet sheet1 = workbook.getSheetAt(0);
            workbook.setSheetName(0, "Sản phẩm bán chạy");
            Row title1 = sheet1.getRow(0);
            if(title1 == null) title1 = sheet1.createRow(0);
            Cell cellB11 = title1.getCell(1);
            if(cellB11 == null) cellB11 = title1.createCell(1);
            cellB11.setCellValue("THỐNG KÊ SẢN PHẨM BÁN CHẠY_" + startDate.replace("-", "/") + "_ĐẾN_" + endDate.replace("-", "/"));
            createProductStatisticSheet(sheet1, OrderDetails_DAO.getInstance().getProductStatistical(startDate, endDate));
//
//            //Sheet2: Biểu đồ tròn theo loại
//            Sheet sheet2 = workbook.getSheetAt(1);
//            workbook.setSheetName(1, "Thống kê theo loại");
//            Row title2 = sheet2.getRow(0);
//            if(title2 == null) title2 = sheet2.createRow(0);
//            Cell cellB12 = title2.getCell(1);
//            if(cellB12 == null) cellB12 = title2.createCell(1);
//            cellB12.setCellValue("THỐNG KÊ SẢN PHẨM THEO LOẠI_" + startDate.replace("-", "/") + "_đến_" + endDate.replace("_", "/"));
//            createProductCircleSheet(sheet2, OrderDetails_DAO.getInstance().getProductStatics_ByType(startDate, endDate));
//
//            //Sheet3: Biểu đồ tròn theo loại
//            Sheet sheet3 = workbook.getSheetAt(2);
//            workbook.setSheetName(2, "Thống kê theo danh mục");
//            Row title3 = sheet3.getRow(0);
//            if(title3 == null) title3 = sheet3.createRow(0);
//            Cell cellB13 = title3.getCell(1);
//            if(cellB13 == null) cellB13 = title3.createCell(1);
//            cellB13.setCellValue("THỐNG KÊ SẢN PHẨM THEO DANH MỤC_" + startDate.replace("-", "/") + "_đến_" + endDate.replace("_", "/"));
//            createProductCircleSheet(sheet3, OrderDetails_DAO.getInstance().getProductStatics_ByCategory(startDate, endDate));

            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
                new Message(homePage, true, "THÔNG BÁO", "Xuất file thành công!", "src/main/java/ui/dialog/checked.png").showAlert();
            } catch (IOException err){
                new Message(homePage, true, "THÔNG BÁO", "Không thể lưu file, hãy kiểm tra lại đường dẫn!", "src/main/java/ui/dialog/warning.png").showAlert();
                err.printStackTrace();
            }
        } catch (IOException e) {
            new Message(homePage, true, "THÔNG BÁO", "Lỗi khi mở file mẫu!", "src/main/java/ui/dialog/warning.png").showAlert();
            throw new RuntimeException(e);
        }
    }

    private void createProductStatisticSheet(Sheet sheet, ArrayList<ModelDataPS> dataPS) {
        int rowIndex = 4;
        for (ModelDataPS model : dataPS){
            Row row = sheet.getRow(rowIndex++);
            if(row == null) row = sheet.createRow(rowIndex);

            Cell cellB = row.getCell(1);
            if(cellB == null) cellB = row.createCell(1);
            cellB.setCellValue(model.getProductName());

            Cell cellC = row.getCell(2);
            if(cellC == null) cellC = row.createCell(2);
            cellC.setCellValue(model.getPurchasePrice());

            Cell cellD = row.getCell(3);
            if(cellD == null) cellD = row.createCell(3);
            cellD.setCellValue(model.getSellPrice());

            Cell cellE = row.getCell(4);
            if(cellE == null) cellE = row.createCell(4);
            cellE.setCellValue(model.getSold());

            Cell cellG = row.getCell(5);
            if(cellG == null) cellG = row.createCell(6);
            cellG.setCellValue(model.getInStock());
        }
    }

    private void createProductCircleSheet(Sheet sheet, ArrayList<ModelDataPS_Circle> data) {
      int rowIndex = 4;
        for (ModelDataPS_Circle model : data){
            Row row = sheet.getRow(rowIndex++);
            if(row == null) row = sheet.createRow(rowIndex);

            Cell cellB = row.getCell(1);
            if(cellB == null) cellB = row.createCell(1);
            cellB.setCellValue(model.getName());

            Cell cellE = row.getCell(4);
            if(cellE == null) cellE = row.createCell(4);
            cellE.setCellValue(model.getCount());
        }
    }

    private void createProductCircleSheet_ByCategory(Sheet sheet, ArrayList<ModelDataPS_Circle> data) {
      int rowIndex = 5;
        for (ModelDataPS_Circle model : data){
            Row row = sheet.getRow(rowIndex++);
            if(row == null) row = sheet.createRow(rowIndex);

            Cell cellB = row.getCell(1);
            if(cellB == null) cellB = row.createCell(1);
            cellB.setCellValue(model.getName());

            Cell cellE = row.getCell(4);
            if(cellE == null) cellE = row.createCell(4);
            cellE.setCellValue(model.getCount());
        }
    }

    private String getUniqueFilePath(String filePath) {
        File file = new File(filePath);
        String baseName = file.getName();
        String directory = file.getParent();
        String extension = "";

        int dotIndex = baseName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < baseName.length() - 1) {
            extension = baseName.substring(dotIndex); //Lấy đuôi file (từ . đến hết)
            baseName = baseName.substring(0, dotIndex); //Lấy phần còn lại (từ đầu đến .)
        }

        int index = 1;
        while (file.exists()) {
            String newFileName = baseName + " (" + index + ")" + extension;
            file = new File(directory, newFileName);
            index++;
        }
        return file.getAbsolutePath();
    }


    private void btnStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDateActionPerformed
       dateStart.showPopup();
    }//GEN-LAST:event_btnStartDateActionPerformed

    private void btnEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEndDateActionPerformed
        dateEnd.showPopup();
    }//GEN-LAST:event_btnEndDateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ui.button.Button btnEndDate;
    private ui.button.Button btnPrintOut;
    private ui.button.Button btnRefresh;
    private ui.button.Button btnStartDate;
    private ui.forms.DashboardForm dashboardForm1;
    private ui.forms.DashboardForm1 dashboardForm11;
    private ui.forms.DashboardForm2 dashboardForm21;
    private ui.datechooser.DateChooser dateEnd;
    private ui.datechooser.DateChooser dateStart;
    private javax.swing.JLabel lbEndDate;
    private javax.swing.JLabel lbStartDate;
    private javax.swing.JPanel pCenter;
    private ui.panel.PanelRound pCriterias;
    private ui.panel.PanelRound panelRound1;
    private ui.textfield.TextField txtEndDate;
    private ui.textfield.TextField txtStartDate;
    // End of variables declaration//GEN-END:variables

    /**
     * Chuyển định dạng dd-mm-yyyy thành yyyy-mm-dd
     *
     * @param inputDate
     * @return
     */
    public static String convertDateFormat(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Chạy hiệu ứng biểu đồ cột
     *
     */
    public void startAnimation(){
        dashboardForm1.barChart1.startAnimation();
        dashboardForm1.barChart2.startAnimation();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnRefresh)) {
            //Đưa dữ liệu vào biểu đồ cột
            modelDataPSList = OrderDetails_DAO.getInstance().getProductStatistical(convertDateFormat(txtStartDate.getText()), convertDateFormat(txtEndDate.getText()));
            modelDataPSList.forEach(x -> System.out.println(x));
            System.out.println(modelDataPSList.getFirst().getProductName());
            if(!modelDataPSList.isEmpty()){
                dashboardForm1.barChart1.setDataset(dashboardForm1.createData(modelDataPSList, 0));
                dashboardForm1.setFormattedDataset(dashboardForm1.createData(modelDataPSList, 0), dashboardForm1.barChart1);
                dashboardForm1.setFormattedDataset(dashboardForm1.createData(modelDataPSList, 1), dashboardForm1.barChart2);
                dashboardForm1.barChart1.startAnimation();
                dashboardForm1.barChart2.startAnimation();
            } else {
                new Message(homePage, true, "THÔNG BÁO", "Không có dữ liệu thống kê!", "src/main/java/ui/dialog/warning.png").showAlert();
            }

            //Đưa dữ liệu vào biểu đồ tròn, thống kê theo loại sản phẩm
            listCircle1 = new OrderDetails_DAO().getProductStatics_ByType(convertDateFormat(txtStartDate.getText()), convertDateFormat(txtEndDate.getText()));
            if(!listCircle1.isEmpty()){
                dashboardForm11.setFormattedDataset(dashboardForm11.createData(listCircle1), dashboardForm11.pieChart1);
                dashboardForm11.pieChart1.startAnimation();
                dashboardForm11.repaint();
            } else {
                new Message(homePage, true, "THÔNG BÁO", "Không có dữ liệu thống kê!", "src/main/java/ui/dialog/warning.png").showAlert();
            }

            //Đưa dữ liệu vào biểu đồ tròn, thống kê theo danh mục
            listCircle2 = new OrderDetails_DAO().getProductStatics_ByCategory(convertDateFormat(txtStartDate.getText()), convertDateFormat(txtEndDate.getText()));
            if(!listCircle2.isEmpty()){
                dashboardForm21.setFormattedDataset(dashboardForm21.createData(listCircle2), dashboardForm21.pieChart1);
                dashboardForm21.pieChart1.startAnimation();
                dashboardForm21.repaint();
            } else {
                new Message(homePage, true, "THÔNG BÁO", "Không có dữ liệu thống kê!", "src/main/java/ui/dialog/warning.png").showAlert();
            }
        }
    }
}
