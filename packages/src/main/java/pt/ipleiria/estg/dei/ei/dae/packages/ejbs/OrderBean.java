package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.util.List;

@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private PackageBean packageBean;

    @EJB
    private ProductBean productBean;


    public void create(Long id, String status, Customer customer) {

        Order order = new Order(id, status, customer);
        entityManager.persist(order);
    }

    public void create(Long id, String status, Customer customer, LogisticsOperator logisticsOperator, List<Package> packages, List<Product> products) {

        Order order = new Order(id, status, customer, logisticsOperator, packages, products);
        entityManager.persist(order);
    }

    public List<Order> all() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public List<Order> allByCustomer(Long customerId) {
        return entityManager
                .createNamedQuery("getAllOrdersByCustomer", Order.class)
                .setParameter("customer", customerId)  // Assuming "customer" is the parameter name in the named query
                .getResultList();
    }

    public Order find(Long id) throws Exception {

        Order order = entityManager.find(Order.class, id);
        if (order == null) {
            throw new Exception("Order '" + id + "' not found");
        }
        return entityManager.find(Order.class, order);
    }

    public void update(Long id, String status, Customer customer, LogisticsOperator logisticsOperator) throws Exception {
        
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

    public void updateStatus(Long id, String status) throws Exception {

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

    public void addPackageToOrder(Long id, Long packageId) throws Exception {
        Order order = find(id);

        Package package_ = packageBean.find(packageId);

        if (package_ == null) {
            throw new Exception("Package '" + packageId + "' not found");
        }

        order.addPackage(package_);
        entityManager.merge(order);
    }

    public void addProductToOrder(Long id, Long productId) throws Exception {
        Order order = find(id);

        Product product = productBean.find(productId);

        if (product == null) {
            throw new Exception("Product '" + productId + "' not found");
        }

        order.addProduct(product);
        entityManager.merge(order);
    }

    public void removePackageFromOrder(Long id, Long packageId) throws Exception {
        Order order = find(id);

        Package package_ = packageBean.find(packageId);

        if (package_ == null) {
            throw new Exception("Package '" + packageId + "' not found");
        }

        order.removePackage(package_);
        entityManager.merge(order);
    }

    public void removeProductFromOrder(Long id, Long productId) throws Exception {
        Order order = find(id);

        Product product = productBean.find(productId);

        if (product == null) {
            throw new Exception("Product '" + productId + "' not found");
        }

        order.removeProduct(product);
        entityManager.merge(order);
    }

    public void remove(Long id) throws Exception {
        Order order = find(id);

        if (order != null) {
            entityManager.remove(order);
        }
    }
}