    package model;

    import jakarta.persistence.*;
    import lombok.*;

    import java.io.Serializable;
    import java.time.LocalDate;
    import java.util.*;
    @Entity
    @Inheritance(strategy = InheritanceType.JOINED)
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @Table(name = "products")
    @Data
    @AllArgsConstructor
    @ToString(exclude = {"vendor", "category", "promotion"})
    public class Product implements Serializable{
        @Id
        @EqualsAndHashCode.Include
        @Column(name = "product_id", nullable = false, columnDefinition = "char(14)")
        private String productID;

        @Column(name = "product_name", columnDefinition = "nvarchar(50)")
        private String productName;

        @Column(name = "registration_number", columnDefinition = "varchar(16)")
        private String registrationNumber;

        @Column(name = "purchase_price")
        private double purchasePrice;

        @Column(name = "tax_percentage", columnDefinition = "float")
        private double taxPercentage;

        @Column(name = "end_date", columnDefinition = "date")
        private LocalDate endDate;

        @ManyToOne
        @JoinColumn(name = "promotion_id")
        private Promotion promotion;

        @ManyToOne
        @JoinColumn(name = "vendor_id")
        private Vendor vendor;

        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;

        @Column(name ="unit_note", columnDefinition = "varchar(60)")
        private String unitNote;


        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "product_units", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"))
        @MapKeyEnumerated(EnumType.STRING)
        @MapKeyColumn(name = "unit_name")
        private Map<PackagingUnit, ProductUnit> unitDetails = new HashMap<>();

        public Product() {

        }

        public Product(String id, String productName, String registrationNumber, double purchasePrice, double taxPercentage, LocalDate endDate, Vendor vendor, Category category, String noteUnit) {
            this.productID = id;
            this.productName = productName;
            this.registrationNumber = registrationNumber;
            this.purchasePrice = purchasePrice;
            this.taxPercentage = taxPercentage;
            this.endDate = endDate;
            this.vendor = vendor;
            this.category = category;
            this.unitNote = noteUnit;
            this.unitDetails = new HashMap<>();
        }


        public double getSellPrice(PackagingUnit unit) {
            return unitDetails.get(unit).getSellPrice();
        }

        public int getInstockQuantity(PackagingUnit unit) {
            return unitDetails.get(unit).getInStock();
        }

        public Map<PackagingUnit, Integer> parseUnitNote() {
            Map<PackagingUnit, Integer> unitMap = new LinkedHashMap<>();
            if (unitNote == null || unitNote.isEmpty()) return unitMap;
            String[] units = unitNote.split(",");
            for (String u : units) {
                u = u.trim();
                String unitName = u.substring(0, u.indexOf('(')).trim();
                int quantity = Integer.parseInt(u.substring(u.indexOf('(') + 1, u.indexOf(')')));
                PackagingUnit packagingUnit = PackagingUnit.fromString(unitName);
                unitMap.put(packagingUnit, quantity);
            }
            return unitMap;
        }

        public boolean isMedicine() {
            return productID.substring(0, 2).equals("PM");
        }

        public boolean isMedicalSupplies() {
            return productID.substring(0, 2).equals("PS");
        }

        public boolean isFunctionalFood() {
            return productID.substring(0, 2).equals("PF");
        }

        public void addUnit(PackagingUnit unit, ProductUnit productUnit){
            unitDetails.put(unit, productUnit);
        }
    }
