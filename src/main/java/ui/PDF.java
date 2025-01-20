package ui;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import dao.OrderDetails_DAO;
import dao.Order_DAO;
import database.ConnectDB;
import entity.PackagingUnit;
import entity.Order;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class PDF {
    public static void main(String[] args) throws IOException {
        ConnectDB.getInstance().connect();
        OrderDetails_DAO orderDetails_dao = new OrderDetails_DAO();
        Order_DAO order_dao = new Order_DAO();

        String path = "invoice.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A6);
        Document document = new Document(pdfDocument);
        PdfFont font = PdfFontFactory.createFont("src/main/font/arial.ttf", "Identity-H");

        float fullWidth[] = {300f};
        float[] columnWidths = {35f, 10f, 200f};
        float[] header = {20f,150f,30f,20f,50f,50f};
        float[] footer = {270f,50f};


//        Paragraph onesp = new Paragraph("\n");

        Table table =new Table(fullWidth);
        table.addCell(getCell10Center("NHÀ THUỐC DNGH",true).setFont(font).setFontSize(10f));
        table.addCell(getCell10Center("12 NVB, Q. Gò Vấp, TP HCM",false).setFont(font));
//        table.addCell(getCell10Center("----------------------",false).setFont(font));
        table.addCell(getCell10Center("HÓA ĐƠN BÁN HÀNG",true).setFontSize(8f).setFont(font));

        Border gb = new SolidBorder(Color.BLACK,1f/50);
        Table divider = new Table(fullWidth);
        divider.setBorder(gb);

//        Table nestedtabe = new Table(new float[]{twocol/2, twocol/2});
//        nestedtabe.addCell(getHeaderTextCell("DateTime").setFontSize(8f).setBold());
//        nestedtabe.addCell(getHeaderTextCellValue("22715701").setFontSize(8f));
//        table2.addCell(new Cell().add(nestedtabe).setBorder(Border.NO_BORDER));

        ArrayList<Order> list = order_dao.getOrderByCriterious("OR241124000001");
        Order o = new Order();
        if (!list.isEmpty()) { // Kiểm tra xem danh sách có phần tử hay không
             o = list.get(0); // Lấy phần tử đầu tiên
        } else {
            System.out.println("Danh sách rỗng, không có phần tử đầu tiên.");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Chuyển đổi LocalDateTime thành String
        String formattedDateTime = o.getOrderDate().format(formatter);
        ArrayList<OrderDetails> listOD = orderDetails_dao.getOrderDetailListByScri("OR241124000001");

        Table tableInformation = new Table(columnWidths);
        tableInformation.addCell(getCell10Left("Thời gian",false).setFont(font));
        tableInformation.addCell(getCell10Center(":",false));
        tableInformation.addCell(getCell10Left(formattedDateTime,false));

        tableInformation.addCell(getCell10Left("Mã HD",false).setFont(font));
        tableInformation.addCell(getCell10Center(":",false));
        tableInformation.addCell(getCell10Left(o.getOrderID(),false).setFont(font));

        tableInformation.addCell(getCell10Left("Nhân viên",false).setFont(font));
        tableInformation.addCell(getCell10Center(":",false));
        tableInformation.addCell(getCell10Left(o.getEmployee().getEmployeeName(),false).setFont(font));

        tableInformation.addCell(getCell10Left("Khách hàng",false).setFont(font));
        tableInformation.addCell(getCell10Center(":",false));
        tableInformation.addCell(getCell10Left(o.getCustomer().getCustomerName(),false).setFont(font));

        Table tableHeader = new Table(header);
        tableHeader.addCell(getCell10Center("STT",true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        tableHeader.addCell(getCell10Left("Tên sản phẩm",true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        tableHeader.addCell(getCell10Right("Đơn vị",true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        tableHeader.addCell(getCell10Center("SL",true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        tableHeader.addCell(getCell10Right("Giá bán",true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        tableHeader.addCell(getCell10Right("Thành tiền",true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        int i = 1;
        for(OrderDetails detail: listOD){
            tableHeader.addCell(getCell10Center(String.valueOf(i), false).setFont(font));
            tableHeader.addCell(getCell10Left(detail.getProduct().getProductName(),false).setFont(font));
            tableHeader.addCell(getCell10Center("Gói",false).setFont(font));
            tableHeader.addCell(getCell10Center(String.valueOf(detail.getOrderQuantity()),false).setFont(font));
            //TODO: XỬ LÝ ĐƠN VỊ CHỔ NÀY
            tableHeader.addCell(getCell10Right(String.valueOf(detail.getProduct().getSellPrice(PackagingUnit.AEROSOL_CAN)),false).setFont(font));
            tableHeader.addCell(getCell10Right(String.valueOf(detail.getLineTotal()),false).setFont(font));
            i++;
        }

        Table tableFooter = new Table(footer);
        tableFooter.addCell(getCell10Left("Tổng tiền (Đã bao gồm thuế)", false).setFont(font).setBorderTop(new SolidBorder(0)));
        tableFooter.addCell(getCell10Right(String.valueOf(o.getTotalDue()),false).setFont(font).setBorderTop(new SolidBorder(0)));

        tableFooter.addCell(getCell10Left("Giảm giá", false).setFont(font));
        tableFooter.addCell(getCell10Right(String.valueOf(o.getDiscount()),false).setFont(font));

        tableFooter.addCell(getCell10Left("Tổng thanh toán", true).setFont(font).setBorderTop(new SolidBorder(0)));
        tableFooter.addCell(getCell10Right(String.valueOf(o.getTotalDue()- o.getDiscount()),false).setFont(font).setBorderTop(new SolidBorder(0)));

        tableFooter.addCell(getCell10Left("Phương thức thanh toán ", false).setFont(font));
        tableFooter.addCell(getCell10Right(String.valueOf(o.getPaymentMethod()),false));

        Table tableEnd = new Table(fullWidth);
        tableEnd.addCell(getCell10Center("*Lưu ý: Sản phẩm có thể đổi trả trong n ngày !!!",true).setFont(font));
        tableEnd.addCell(getCell10Center("Cảm ơn quý khách",true).setFont(font));




        document.add(table);
        document.add(tableInformation);

//      document.add(onesp);
        document.add(tableHeader);
//        document.add(divider);
        document.add(tableFooter);
        document.add(tableEnd);

        document.close();
//        printPdf(path);
        printPDF(path);
    }
    static  Cell getHeaderTextCell(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }
    static Cell getHeaderTextCellValue(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    static Cell getBillingandShippingCell(String textValue){
        return new Cell().add(textValue).setFontSize(5f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    static Cell getCell10Left(String textValue, Boolean isBold){
        Cell myCell = new Cell().add(textValue).setFontSize(5f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ? myCell.setBold() : myCell;
    }
    static Cell getCell10Right(String textValue, Boolean isBold){
        Cell myCell = new Cell().add(textValue).setFontSize(5f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
        return isBold ? myCell.setBold() : myCell;
    }
    static Cell getCell10Center(String textValue, Boolean isBold) {
        Cell myCell = new Cell().add(textValue)
                .setFontSize(5f)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER); // Căn giữa
        return isBold ? myCell.setBold() : myCell;
    }

    /**
     * Hiển thị hộp thoại trước khi in
     * @param path
     */
    public static void printPDF(String path) {
        try {
            // Đường dẫn đến file PDF
            File pdfFile = new File(path);

            // Mở tài liệu PDF
            PDDocument document = PDDocument.load(pdfFile);

            // Tìm máy in mặc định
            PrinterJob job = PrinterJob.getPrinterJob();
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

            if (printService != null) {
                job.setPrintService(printService);

                // Gán nội dung PDF vào công việc in
                job.setPageable(new PDFPageable(document));

                // Hiển thị hộp thoại in
                if (job.printDialog()) {
                    job.print();
                }
            } else {
                System.out.println("Không tìm thấy máy in mặc định.");
            }

            // Đóng tài liệu PDF
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**
 * In không cần hiển thị hộp thoại
 */
//    public static void printPDF(String path) {
//        try {
//            // Đường dẫn đến file PDF
//            File pdfFile = new File(path);
//
//            // Mở tài liệu PDF
//            PDDocument document = PDDocument.load(pdfFile);
//
//            // Tìm máy in mặc định
//            PrinterJob job = PrinterJob.getPrinterJob();
//            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
//
//            if (printService != null) {
//                job.setPrintService(printService);
//
//                // Gán nội dung PDF vào công việc in
//                job.setPageable(new PDFPageable(document));
//
//                // Tự động in mà không hiển thị hộp thoại
//                job.print();
//            } else {
//                System.out.println("Không tìm thấy máy in mặc định.");
//            }
//
//            // Đóng tài liệu PDF
//            document.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
