package dao;

public class Product_DAO {
   public Product_DAO() {
    }

    public static Product_DAO getInstance(){
        return new Product_DAO();
    }

    /**
     * Chuyển mã sản phẩm sang mã vạch
     *
     * @param barcode
     * @return
     */
    public String convertBarcode_ToProductID(String barcode){
        String productID = barcode.substring(1);
        String temp = String.valueOf(barcode.charAt(0));
        switch (temp){
            case "7":
                return "PF" + productID;
            case "8":
                return "PM" + productID;
            case "9":
                return "PS" + productID;
        }
        return null;
    }

    /**
     * Xử lý cắt tên từ 0 đến ( cho unitNote
     *
     * @param input
     * @return
     */
    public String extractUnitName(String input) {
        if (input == null || !input.contains("(")) {
            return null;
        }

        return input.substring(0, input.indexOf("(")).trim();
    }

    /**
     * Lấy index của phần tử trong array
     *
     * @param parts
     * @param element
     * @return
     */
    public int getIndexPart_UnitNote(String[] parts, String element) {
        for(int i = 0; i < parts.length; i++) {
            if(element.equals(extractUnitName(parts[i]))) {
                return i;
            }
        }
        return  -1;
    }

    public static void main(String[] args) {

        //product_dao.updateProductInStock_WithTransaction("PM121224000003", 10, unitEnum2, false, con);
    }
}

