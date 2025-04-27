package model;

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

    public static PackagingUnit fromString(String value) {
        for (PackagingUnit unit : PackagingUnit.values()) {
            if (unit.getPackagingUnit().equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("LỖI: ĐƠN VỊ KHÔNG TỒN TẠI " + value);
    }

    public static PackagingUnit convertToEnum(String unit) {
        switch (unit.trim()) {
            case "Viên":
                return PILL;
            case "Vỉ":
                return BLISTER_PACK;
            case "Gói":
                return PACK;
            case "Chai":
                return BOTTLE;
            case "Lọ":
                return JAR;
            case "Tuýp":
                return TUBE;
            case "Túi":
                return BAG;
            case "Ống":
                return AMPOULE;
            case "Chai xịt":
                return SPRAY_BOTTLE;
            case "Lọ xịt":
                return AEROSOL_CAN;
            case "Bộ kit":
                return KIT;
            case "Thùng":
                return BIN;
            case "Hộp":
                return BOX;
            default:
                throw new IllegalArgumentException("LỖI: ĐƠN VỊ KHÔNG HỢP LỆ: " + unit);
        }
    }

    public String convertUnit(PackagingUnit enumUnit) {
        switch (enumUnit) {
            case PILL:
                return "Viên";
            case BLISTER_PACK:
                return "Vỉ";
            case PACK:
                return "Gói";
            case BOTTLE:
                return "Chai";
            case JAR:
                return "Lọ";
            case TUBE:
                return "Tuýp";
            case BAG:
                return "Túi";
            case AMPOULE:
                return "Ống";
            case SPRAY_BOTTLE:
                return "Chai xịt";
            case AEROSOL_CAN:
                return "Lọ xịt";
            case KIT:
                return "Bộ kit";
            case BIN:
                return "Thùng";
            case BOX:
                return "Hộp";
            default:
                return "Không xác định";
        }
    }


    PackagingUnit(String packagingUnit) {
        this.packagingUnit = packagingUnit;
    }

    @Override
    public String toString() {
        return packagingUnit;
    }
}
