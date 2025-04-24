package model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BarcodeGenerator {
    /**
     * Tạo mã vạch và lưu vào file hình ảnh
     *
     * @param barcodeText Mã cần tạo mã vạch
//     * @param filePath    Đường dẫn file để lưu hình ảnh
     * @throws com.itextpdf.barcodes.qrcode.WriterException
     * @throws IOException
     */
    public static void generateBarcode(String barcodeText) throws com.itextpdf.barcodes.qrcode.WriterException, IOException {
        com.google.zxing.oned.Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128.CODE_128, 300, 40);

        Path path = Paths.get("src/main/java/ui/barcodeprocess/temp.png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static String orderIDToBarcode(String input) {
        if (input == null || input.length() <= 2) {
            throw new IllegalArgumentException("Lỗi: Invalid input format");
        }
        String prefix = input.substring(0, 2);
        String restOfString = input.substring(2);
        switch (prefix) {
            case "OC":
                return "1" + restOfString;
            case "OR":
                return "0" + restOfString;
            default:
                throw new IllegalArgumentException("Lỗi định dạng: " + prefix);
        }
    }

    public static String orderIDFromBarcode(String input) {
        if (input == null || input.length() <= 1) {
            throw new IllegalArgumentException("Lỗi: Invalid input format");
        }
        char prefix = input.charAt(0);
        String restOfString = input.substring(1);
        switch (prefix) {
            case '1':
                return "OC" + restOfString;
            case '0':
                return "OR" + restOfString;
            default:
                throw new IllegalArgumentException("Lỗi định dạng: " + prefix);

        }
    }
//    public static void main(String[] args) {
//        try {
//            String barcodeText = "PhanPhuocHiep";
//            String filePath = "src/main/java/ui/barcodeprocess/temp.png";
//            int width = 300;
//            int height = 100;
//
//            generateBarcode(barcodeText);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (com.itextpdf.barcodes.qrcode.WriterException e) {
//            throw new RuntimeException(e);
//        }
//    }
}