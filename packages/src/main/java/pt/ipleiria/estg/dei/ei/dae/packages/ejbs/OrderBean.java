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
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyQueryException;

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

    @EJB
    LogisticsOperatorBean logisticsOperatorBean;


    public Order create(StatusMessage status, Customer customer)
            throws MyEntityNotFoundException, MyQueryException {
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

    public List<Order> all() throws MyEntityNotFoundException, MyQueryException {
        try {
            List<Order> resultList = entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No Orders found");
            }

            return resultList;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "all(): " + e);
        }
    }


    public List<Order> allByCustomer(String username) throws MyEntityNotFoundException, MyQueryException {

        try {
            List<Order> resultList = entityManager
                    .createNamedQuery("getAllOrdersByCustomer", Order.class)
                    .setParameter("username", username)  // Assuming "customer" is the parameter name in the named query
                    .getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No Orders found");
            }

            return resultList;


        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "allByCustomer(): " + e);
        }
    }

    public List<Order> allByLogisticsOperator(String username) throws MyEntityNotFoundException, MyQueryException {

        try {
            List<Order> resultList = entityManager
                    .createNamedQuery("getAllOrdersByLogisticsOperator", Order.class)
                    .setParameter("username", username)  // Assuming "logisticsOperatorId" is the parameter name in the named query
                    .setParameter("status", StatusMessage.PENDENTE)
                    .getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No Orders found");
            }

            return resultList;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "allByLogisticsOperator(): " + e);
        }
    }

    public Order find(long id) throws MyEntityNotFoundException, MyQueryException {
        try {

            Order order = entityManager.find(Order.class, id);
            if (order == null) {
                throw new MyEntityNotFoundException("Order '" + id + "' not found");
            }
            Hibernate.initialize(order.getProducts());
            Hibernate.initialize(order.getPackages());
            return order;
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "find(): " + e);
        }
    }

    public void update(long id, StatusMessage status, Customer customer, LogisticsOperator logisticsOperator) throws MyIncorrectDataType, MyQueryException, MyEntityNotFoundException {

        try {
            Order order = find(id);

            try {
                entityManager.lock(order, LockModeType.OPTIMISTIC);

                order.setId(id);
                order.setStatus(status);
                order.setCustomer(customer);

                if (logisticsOperator != null)
                    order.setLogisticsOperators(logisticsOperator);

                entityManager.merge(order);

            } catch (ConstraintViolationException e) {
                throw new MyIncorrectDataType("Data Type incorreto: " + e);
            }
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "update(): " + e);
        }
    }

    public void addPackageToOrder(long id, long packageId) throws MyQueryException, MyEntityNotFoundException {
        try {
            Order order = find(id);

            Package package_ = packageBean.find(packageId);

            if (package_ == null) {
                throw new MyEntityNotFoundException("Package '" + packageId + "' not found");
            }

            order.addPackage(package_);
            package_.setOrder(order);
            entityManager.merge(order);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "addPackageToOrder(): " + e);
        }
    }

    public void addProductToOrder(long id, long productId) throws MyQueryException, MyEntityNotFoundException {
        try {
            Order order = find(id);

            Product product = productBean.find(productId);

            if (product == null) {
                throw new MyEntityNotFoundException("Product '" + productId + "' not found");
            }

            order.addProduct(product);
            entityManager.merge(order);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "addProductToOrder(): " + e);
        }
    }

    public void removePackageFromOrder(long id, long packageId) throws MyEntityNotFoundException, MyQueryException {
        try {

            Order order = find(id);

            Package package_ = packageBean.find(packageId);

            if (package_ == null) {
                throw new MyEntityNotFoundException("Package '" + packageId + "' not found");
            }

            order.removePackage(package_);
            package_.removeOrder(order);
            entityManager.merge(order);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "removePackageFromOrder(): " + e);
        }
    }

    public void removeProductFromOrder(long id, long productId) throws MyEntityNotFoundException, MyQueryException {
        try {

            Order order = find(id);

            Product product = productBean.find(productId);

            if (product == null) {
                throw new MyEntityNotFoundException("Product '" + productId + "' not found");
            }

            order.removeProduct(product);
            entityManager.merge(order);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "removeProductFromOrder(): " + e);
        }
    }

    public void remove(long id) throws MyEntityNotFoundException, MyQueryException {
        try {
            Order order = find(id);

            if (order == null) {
                throw new MyEntityNotFoundException("Order '" + id + "' not found");
            }
            entityManager.remove(order);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "remove(): " + e);
        }
    }

    public void setLogisticsOperator(long id, String logisticsOperatorId) throws MyEntityNotFoundException, MyQueryException {
        try {
            Order order = find(id);

            LogisticsOperator logisticsOperator = logisticsOperatorBean.findLogisticOperator(logisticsOperatorId);

            if (logisticsOperator == null) {
                throw new MyEntityNotFoundException("LogisticsOperator '" + logisticsOperatorId + "' not found");
            }

            order.setLogisticsOperators(logisticsOperator);
            entityManager.persist(order);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "setLogisticsOperator(): " + e);
        }
    }

    public void setStatusEnviado(long id) throws MyQueryException {
        try {
            Order order = entityManager.find(Order.class, id);
            order.setStatus(StatusMessage.ENVIADA);
            entityManager.merge(order);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "setStatusEnviado(): " + e);
        }
    }

    public List<Sensor> getOrdersSensors(long id) {

        Order order = entityManager.find(Order.class, id);
        List<Sensor> sensors = new ArrayList<>();
        List<Package> packages = order.getPackages();
        for (Package p : packages) {
            sensors.addAll(p.getSensors());
        }
        return sensors;
    }
}