package ui.main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ExcelFileChooser {
    public static ExcelFileChooser getObject(){
        return new ExcelFileChooser();
    }
    public String chooseExcelFileSavePath(String namefile){
        JFileChooser fileBaoCao = new JFileChooser();
        fileBaoCao.setDialogTitle("Chọn vị trí lưu file báo cáo");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "xls");
        fileBaoCao.setFileFilter(filter);

        fileBaoCao.setSelectedFile(new File(namefile + ".xlsx"));

        int userSelection = fileBaoCao.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileBaoCao.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx") && !filePath.endsWith(".xls")) {
                filePath += ".xlsx";
            }
            return filePath;
        } else {
            return null;
        }
    }
}
