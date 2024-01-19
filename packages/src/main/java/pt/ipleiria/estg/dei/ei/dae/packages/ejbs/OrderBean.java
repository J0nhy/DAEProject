package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyIncorrectDataType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private PackageBean packageBean;

    @EJB
    private ProductBean productBean;

    @EJB LogisticsOperatorBean logisticsOperatorBean;


    public Order create(StatusMessage status, Customer customer)
            throws MyEntityNotFoundException, MyIncorrectDataType {
        Order order = new Order(status, customer);

        Random rand = new Random();
        int i = rand.nextInt(6);
        Product product = null;
        for (int count = 0; count < i; count++) {
            product = productBean.create("Product" + count, "Product" + count + " Description", "Product" + count + " Category", "Manufacturer" + count, "Brand" + count, "Image" + count, 1.01 + count, 1 + count);
            order.addProduct(product);
        }
        entityManager.persist(order);
        return order;
    }

    public List<Order> all() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }


    public List<Order> allByCustomer(String username) {
        return entityManager
                .createNamedQuery("getAllOrdersByCustomer", Order.class)
                .setParameter("username", username)  // Assuming "customer" is the parameter name in the named query
                .getResultList();
    }

    public List<Order> allByLogisticsOperator(String username) {
        return entityManager
                .createNamedQuery("getAllOrdersByLogisticsOperator", Order.class)
                .setParameter("username", username)  // Assuming "logisticsOperatorId" is the parameter name in the named query
                .setParameter("status", StatusMessage.PENDENTE)
                .getResultList();
    }

    public Order find(long id) throws MyEntityNotFoundException {

        Order order = entityManager.find(Order.class, id);
        if (order == null) {
            throw new MyEntityNotFoundException("Order '" + id + "' not found");
        }
        Hibernate.initialize(order.getProducts());
        Hibernate.initialize(order.getPackages());
        return order;
    }

    public void update(long id, StatusMessage status, Customer customer, LogisticsOperator logisticsOperator) throws Exception, MyIncorrectDataType {
        
        Order order = find(id);

        try {
            entityManager.lock(order, LockModeType.OPTIMISTIC);

            order.setId(id);
            order.setStatus(status);
            order.setCustomer(customer);
            order.setLogisticsOperators(logisticsOperator);

            entityManager.merge(order);
            
        } catch (ConstraintViolationException e) {
            throw new MyIncorrectDataType("Data Type incorreto: " + e);
        }
    }

    //Temos de validar se está atribuida a outra encomenda
    public void addPackageToOrder(long id, long packageId) throws Exception {
        Order order = find(id);

        Package package_ = packageBean.find(packageId);

        if (package_ == null) {
            throw new Exception("Package '" + packageId + "' not found");
        }

        order.addPackage(package_);
        package_.setOrder(order);
        entityManager.merge(order);
    }

    public void addProductToOrder(long id, long productId) throws Exception {
        Order order = find(id);

        Product product = productBean.find(productId);

        if (product == null) {
            throw new Exception("Product '" + productId + "' not found");
        }

        order.addProduct(product);
        entityManager.merge(order);
    }

    public void removePackageFromOrder(long id, long packageId) throws Exception {
        Order order = find(id);

        Package package_ = packageBean.find(packageId);

        if (package_ == null) {
            throw new Exception("Package '" + packageId + "' not found");
        }

        order.removePackage(package_);
        package_.removeOrder(order);
        entityManager.merge(order);
    }

    public void removeProductFromOrder(long id, long productId) throws Exception {
        Order order = find(id);

        Product product = productBean.find(productId);

        if (product == null) {
            throw new Exception("Product '" + productId + "' not found");
        }

        order.removeProduct(product);
        entityManager.merge(order);
    }

    public void remove(long id) throws Exception {
        Order order = find(id);

        if (order != null) {
            entityManager.remove(order);
        }
    }

    public void setLogisticsOperator(long id, String logisticsOperatorId) throws Exception {
        Order order = find(id);

        LogisticsOperator logisticsOperator = logisticsOperatorBean.findLogisticOperator(logisticsOperatorId);

        if (logisticsOperator == null) {
            throw new Exception("LogisticsOperator '" + logisticsOperatorId + "' not found");
        }

        order.setLogisticsOperators(logisticsOperator);
        entityManager.persist(order);
    }

    public void setStatusEnviado(long id) {
        Order order = entityManager.find(Order.class, id);
        order.setStatus(StatusMessage.ENVIADA);
        entityManager.merge(order);
    }
}