package entity;

public enum PackagingUnit {
    PILL("PILL"),  //Viên
    BLISTER_PACK("BLISTER_PACK"), //Vỉ
    PACK("PACK"), //Gói
    BOTTLE("BOTTLE"), //Chai
    JAR("JAR"), //Lọ
    TUBE("TUBE"), //Tuýp
    BAG("BAG"), //Túi
    AMPOULE("AMPOULE"), //Ống
    SPRAY_BOTTLE("SPRAY_BOTTLE"), //Chai xịt
    AEROSOL_CAN("AEROSOL_CAN"), //Lọ xịt
    KIT("KIT"), //Bộ kit
    BIN("BIN"), //Thùng
    BOX("BOX"); //Hộp
    private  String packagingUnit;

    public String getPackagingUnit() {
        return packagingUnit;
    }

    public static entity.PackagingUnit fromString(String value) {
        for (entity.PackagingUnit unit : entity.PackagingUnit.values()) {
            if (unit.getPackagingUnit().equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("LỖI: ĐƠN VỊ KHÔNG TỒN TẠI " + value);
    }

    public String convertUnit(entity.PackagingUnit enumUnit) {
        String name = "";
        if(enumUnit.equals(PILL)) {
            name = "Viên";
        } else if(enumUnit.equals(BLISTER_PACK)) {
            name = "Vỉ";
        } else if(enumUnit.equals(PACK)) {
            name = "Gói";
        } else if (enumUnit.equals(BOTTLE)) {
            name = "Chai";
        } else if (enumUnit.equals(JAR)) {
            name = "Lọ";
        }
        return name;
    }

    PackagingUnit(String packagingUnit) {
        this.packagingUnit = packagingUnit;
    }

    @Override
    public String toString() {
        return packagingUnit;
    }
}
