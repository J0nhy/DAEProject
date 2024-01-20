package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Product;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyIncorrectDataType;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyQueryException;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;


    public Product create(String productName, String productDescription, String productCategory, String productManufacturer, String productBrand, String productImage, double productPrice, double productWeight) throws MyEntityNotFoundException, MyQueryException {

        try {

            Product product = new Product(productName, productDescription, productCategory, productManufacturer, productBrand, productImage, productPrice, productWeight);
            if (product == null) {
                throw new MyEntityNotFoundException("No products found");
            }

            entityManager.persist(product);
            return product;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "create(): " + e);
        }
    }

    public List<Product> all() throws MyQueryException, MyEntityNotFoundException {
        try {
            List<Product> resultList = entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No Products found");
            }

            return resultList;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "all(): " + e);
        }
    }

    public Product find(long id) throws MyEntityNotFoundException, MyQueryException {

        try {
            Product product = entityManager.find(Product.class, id);
            if (product == null) {
                throw new MyEntityNotFoundException("Product '" + id + "' not found");
            }
            return entityManager.find(Product.class, id);

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "find(): " + e);
        }
    }

    public void update(long id, String productName, String productDescription, String productCategory, String productManufacturer, String productBrand, String productImage, double productPrice, double productWeight)
            throws MyQueryException, MyEntityNotFoundException, MyIncorrectDataType {

        try {
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
                throw new MyIncorrectDataType("Data Type incorreto: " + e);
            }
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "update(): " + e);
        }
    }

    public void remove(long id) throws MyEntityNotFoundException, MyQueryException {
        try {
            Product product = find(id);

            if (product != null) {
                entityManager.remove(product);
            }
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "remove(): " + e);
        }
    }
}
