package service.impl;

import dao.CategoryDAO;
import dao.GenericDAO;
import model.Category;
import service.CategoryService;

import java.rmi.RemoteException;

public class CategoryImpl extends GenericServiceImpl<Category, String> implements CategoryService {
    private CategoryDAO categoryDAO;

    public CategoryImpl(CategoryDAO categoryDAO) throws RemoteException {
        super(categoryDAO);
        this.categoryDAO = categoryDAO;
    }
}
