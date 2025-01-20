package dao;

import database.ConnectDB;
import entity.FunctionalFood;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FunctionalFood_DAO {
    public FunctionalFood_DAO() {
    }

    /**
     * Lọc danh sách tất cả thực phẩm chức năng
     *
     * @return
     */
    public ArrayList<FunctionalFood> getFunctionalFoodList(){
        Connection con = ConnectDB.getConnection();
        ArrayList<FunctionalFood> functionalFoodsList = new ArrayList<>();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT mainNutrients, supplementaryIngredients FROM FunctionalFood");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String mainNutrients = rs.getString(1);
                String supplementaryIngredients = rs.getString(2);

                FunctionalFood functionalFood = new FunctionalFood(mainNutrients, supplementaryIngredients);
                functionalFoodsList.add(functionalFood);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return functionalFoodsList;
    }

    /**
     * Lọc thực phẩm chức năng theo mã
     *
     * @param functionID
     * @return
     */
    public ArrayList<FunctionalFood> getFunctionFoodListByID(String functionID){
        Connection con = ConnectDB.getInstance().getConnection();
        ArrayList<FunctionalFood> functionalFoodsListByID = new ArrayList<>();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT mainNutrients, supplementaryIngredients FROM FunctionalFood WHERE functionalFoodID =? ");
            stmt.setString(1, functionID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String mainNutrients = rs.getString(1);
                String supplementaryIngredients = rs.getString(2);

                FunctionalFood functionalFood = new FunctionalFood(mainNutrients, supplementaryIngredients);
                functionalFoodsListByID.add(functionalFood);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return functionalFoodsListByID;
    }

    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
        FunctionalFood_DAO functionalFood_dao = new FunctionalFood_DAO();
//        functionalFood_dao.getFunctionalFoodList().forEach(x -> System.out.println(x));
        functionalFood_dao.getFunctionFoodListByID("PF021024000004").forEach(x -> System.out.println(x));
    }
}
