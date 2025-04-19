package service;

import entity.PackagingUnit;
import entity.Product;
import entity.ProductUnit;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProductService extends GenericService<Product, String>{
    boolean createMultiple(List<Product> products);

}
