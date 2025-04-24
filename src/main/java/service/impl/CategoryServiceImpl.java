package service.impl;

import dao.CategoryDAO;
import model.Category;
import service.CategoryService;

import java.rmi.RemoteException;

public class CategoryServiceImpl extends GenericServiceImpl<Category, String> implements CategoryService {
    private CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) throws RemoteException {
        super(categoryDAO);
        this.categoryDAO = categoryDAO;
    }
}
