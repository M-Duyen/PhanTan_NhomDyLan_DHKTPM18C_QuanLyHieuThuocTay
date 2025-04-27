package dao;


import jakarta.persistence.EntityManager;
import model.Vendor;
import service.VendorService;
import utils.JPAUtil;

import java.rmi.RemoteException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VendorDAO extends GenericDAO<Vendor, String> implements VendorService {
    public VendorDAO(EntityManager em, Class<Vendor> entityClass) {
        super(em, entityClass);
    }

    public VendorDAO(Class<Vendor> clazz) {
        super(clazz);
        this.em = JPAUtil.getEntityManager();
    }

    /**
     * Chuyển đầu vào thành ký tự không dấu
     *
     * @param country
     * @return
     */
    @Override
    public String removeAccent(String country) {
        String normalized = Normalizer.normalize(country, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");
    }

    /**
     * Lấy mã quốc gia theo quốc gia
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
                countryCode.append(words[0].substring(0, Math.min(2, words[0].length())));
            } else {
                countryCode.append(words[0].charAt(0));
                countryCode.append(words[1].charAt(0));
            }
        }
        return countryCode.toString().toUpperCase();
    }

    /**
     * Tạo mã tự động cho nhà cung cấp
     *
     * @param country
     * @return
     */
    @Override
    public String createVendorID(String country) {
        String newMaNCC = null;

        String countryCode = getCountryID(country);
        String prefix = "VD" + countryCode;

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

    @Override
    public ArrayList<Vendor> getVendorListByCriteriasByCountry(String criterious, ArrayList<Vendor> arrayList) throws RemoteException {
        ArrayList<Vendor> vendorByCriList = new ArrayList<>();

        for (Vendor vendor : arrayList) {
            if (vendor.getVendorID().toLowerCase().trim().contains(criterious.toLowerCase().trim()) ||
                    vendor.getVendorName().toLowerCase().trim().contains(criterious.toLowerCase().trim())
            ){
                vendorByCriList.add(vendor);
            }
        }
        return vendorByCriList;
    }

    public static void main(String[] args) {
        VendorDAO dao = new VendorDAO(Vendor.class);
        dao.searchByMultipleCriteria("vendor", "s").forEach(System.out::println);
    }

}
