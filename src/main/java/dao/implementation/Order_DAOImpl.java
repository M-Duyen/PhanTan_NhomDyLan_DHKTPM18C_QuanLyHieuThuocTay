package dao.implementation;

import dao.*;
import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order_DAOImpl extends GenericDAOImpl<Order, String> {
    public Order_DAOImpl(Class<Order> clazz) {
        super(clazz);
    }

    public Order_DAOImpl(EntityManager em, Class<Order> clazz) {
        super(em, clazz);
    }


    public static void main(String[] args) {
        Order_DAOImpl dao = new Order_DAOImpl(Order.class);


    }


}