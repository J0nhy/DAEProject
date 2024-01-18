package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Product;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

import java.util.List;
@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String productName, String productDescription, String productCategory, String productManufacturer, String productBrand, String productImage, String productPrice, String productWeight) {
        Product product = new Product(productName, productDescription, productCategory, productManufacturer, productBrand, productImage, productPrice, productWeight);
        entityManager.persist(product);
    }

    public List<Product> all() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(long id) throws MyEntityNotFoundException {

        Product product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new MyEntityNotFoundException("Product '" + id + "' not found");
        }
        return entityManager.find(Product.class, id);
    }

    public void update(long id, String productName, String productDescription, String productCategory, String productManufacturer, String productBrand, String productImage, String productPrice, String productWeight) throws Exception {

        Product product = find(id);

        try {
            entityManager.lock(product, LockModeType.OPTIMISTIC);

            product.setId(id);
            product.setProductName(productName);
            product.setProductDescription(productDescription);
            product.setProductCategory(productCategory);
            product.setProductManufacturer(productManufacturer);
            product.setProductBrand(productBrand);
            product.setProductImage(productImage);
            product.setProductPrice(productPrice);
            product.setProductWeight(productWeight);

            entityManager.merge(product);

        } catch (ConstraintViolationException e) {
            throw new Exception(e);
        }
    }

    public void remove(Long id) throws Exception {
        Product product = find(id);

        if (product != null) {
            entityManager.remove(product);
        }
    }
}
