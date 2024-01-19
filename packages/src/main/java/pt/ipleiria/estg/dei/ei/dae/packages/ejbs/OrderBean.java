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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private PackageBean packageBean;

    @EJB
    private ProductBean productBean;

    @EJB LogisticsOperatorBean logisticsOperatorBean;


    public long create(String status, Customer customer, List<Product> products)
        throws MyEntityNotFoundException{
        Order order = new Order(status, customer);
        /*for (Package p : packages) {
            try {
                Package package_ = packageBean.find(p.getId());
                order.addPackage(package_);
                package_.setOrder(order);
            } catch (MyEntityNotFoundException e) {
                throw new MyEntityNotFoundException("Package with id: " + p.getId() + " not found");
            }
        }*/
        for (Product p : products) {
            try {
                Product product = productBean.find(p.getId());
                order.addProduct(product);
            } catch (MyEntityNotFoundException e) {
                throw new MyEntityNotFoundException("Product with id: " + p.getId() + " not found");
            }
        }
        entityManager.persist(order);
        return order.getId();
    }

    public List<Order> all() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public List<Order> allByCustomer(String customerId) {
        return entityManager
                .createNamedQuery("getAllOrdersByCustomer", Order.class)
                .setParameter("customer", customerId)  // Assuming "customer" is the parameter name in the named query
                .getResultList();
    }

    public List<Order> allByLogisticsOperator(String logisticsOperatorId) {
        return entityManager
                .createNamedQuery("getAllOrdersByLogisticsOperator", Order.class)
                .setParameter("logisticsoperator", logisticsOperatorId)  // Assuming "logisticsOperatorId" is the parameter name in the named query
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

    public void update(long id, String status, Customer customer, LogisticsOperator logisticsOperator) throws Exception {
        
        Order order = find(id);

        try {
            entityManager.lock(order, LockModeType.OPTIMISTIC);

            order.setId(id);
            order.setStatus(status);
            order.setCustomer(customer);
            order.setLogisticsOperators(logisticsOperator);

            entityManager.merge(order);
            
        } catch (ConstraintViolationException e) {
            throw new Exception(e);
        }
    }

    public void updateStatus(long id, String status) throws Exception {

        Order order = find(id);

        try {
            entityManager.lock(order, LockModeType.OPTIMISTIC);

            order.setId(id);
            order.setStatus(status);

            entityManager.merge(order);

        } catch (ConstraintViolationException e) {
            throw new Exception(e);
        }
    }

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
        entityManager.merge(order);
    }
}