package dao;


import model.Vendor;
import jakarta.persistence.EntityManager;
import service.VendorService;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

public class VendorDAO extends GenericDAO<Vendor, String> implements VendorService {
    public VendorDAO(EntityManager em, Class<Vendor> entityClass) {
        super(em, entityClass);
    }

    public VendorDAO(Class<Vendor> clazz) {
        super(clazz);
    }

    /**
     * Chuyển đầu vào thành ký tự không dấu
     *
     * @param country
     * @return
     */
    @Override
    public String removeAccent(String country) {
        // Chuyển đổi sang dạng chuẩn
        String normalized = Normalizer.normalize(country, Normalizer.Form.NFD);
        // Sử dụng biểu thức chính quy để loại bỏ các ký tự dấu
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");
    }

    /**
     *  Lấy mã quốc gia theo quốc gia
     *
     * @param country
     * @return
     */
    @Override
    public String getCountryID(String country) {
        String countryNot = removeAccent(country);
        String[] words = countryNot.split(" ");
        StringBuilder countryCode = new StringBuilder();

        if (words.length > 0) {
            if (words.length == 1) {
                // Nếu tên quốc gia chỉ có một từ, lấy 2 ký tự đầu của từ đó
                countryCode.append(words[0].substring(0, Math.min(2, words[0].length())));
            } else {
                // Lấy ký tự đầu của từ đầu tiên
                countryCode.append(words[0].charAt(0));
                // Lấy ký tự đầu của từ thứ hai
                countryCode.append(words[1].charAt(0));
            }
        }
        // Chuyển mã quốc gia thành chữ in hoa
        return countryCode.toString().toUpperCase();
    }

    /**
     * Tạo mã tự động cho nhà cung cấp
     *
     * @param country
     * @return
     * @throws SQLException
     */
    @Override
    public String createVendorID(String country) {
        String newMaNCC = null;

        // Lấy mã quốc gia (2 ký tự đầu của tên quốc gia, in hoa)
        String countryCode = getCountryID(country);
        String prefix = "VD" + countryCode;

        // JPQL để lấy tất cả vendorID bắt đầu với prefix
        String jpql = "SELECT v.vendorID FROM Vendor v WHERE v.vendorID LIKE :prefix";

        List<String> vendorIDs = em.createQuery(jpql, String.class)
                .setParameter("prefix", prefix + "%")
                .getResultList();

        // Lấy max số thứ tự (VDVN001 -> 1)
        int currentMax = vendorIDs.stream()
                .map(id -> id.substring(4)) // Lấy phần số
                .mapToInt(numStr -> {
                    try {
                        return Integer.parseInt(numStr);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);

        int nextNumber = currentMax + 1;
        newMaNCC = prefix + String.format("%03d", nextNumber);
        return newMaNCC;
    }


    public static void main(String[] args) {

        VendorDAO vendor_dao = new VendorDAO(Vendor.class);
//        vendor_dao.getAll().forEach(System.out::println);

        Vendor vendor = new Vendor();
        String newID = vendor_dao.createVendorID("Canada");
        vendor.setVendorID(newID);
        vendor.setVendorName("Hồ Quang Nhân");
        vendor.setCountry("Canada");
        vendor_dao.create(vendor);

//        System.out.println(vendor_dao.findById(newID));
//        vendor.setCountry("USA");
//        vendor_dao.update(vendor);

//        vendor_dao.delete("V-123");
        vendor_dao.getAll().forEach(System.out::println);


    }

}
