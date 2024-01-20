package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.*;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Hasher;

import java.util.List;

@Stateless
public class CustomerBean {
    @PersistenceContext
    private EntityManager entityManager;

    private Hasher hasher = new Hasher();

    public void create(String username, String password, String name, String email, int nif, int phone, String address)
            throws MyEntityExistsException, MyConstraintViolationException, MyIncorrectDataType, MyQueryException {

        try {
            Customer customer = entityManager.find(Customer.class, username);
            if (customer != null) {
                if (!customer.isDeleted()) {
                    throw new MyEntityExistsException("Customer with username: " + username + " already exists");
                }
                throw new MyEntityExistsException("Account with username: " + username + " was deleted.");
            }
            try {
                customer = new Customer(username, hasher.hash(password), name, email, nif, phone, address);
                entityManager.persist(customer);
            } catch (ConstraintViolationException e) {
                throw new MyIncorrectDataType("Data Type incorreto: " + e);
            }
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "create(): " + e);
        }
    }

    public List<Customer> getAll() throws MyQueryException, MyEntityNotFoundException {
        try {
            List<Customer> resultList = entityManager.createNamedQuery("getAllCustomers", Customer.class).getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No customers found");
            }

            return resultList;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "getAll(): " + e);
        }
    }


    public Customer findCustomer(String username) throws MyEntityNotFoundException, MyQueryException {
        try {
            Customer customer = entityManager.find(Customer.class, username);
            if (customer == null) {
                throw new MyEntityNotFoundException("Customer with username: " + username + " not found");
            }
            Hibernate.initialize(customer.getOrders());
            return customer;
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "findCustomer(): " + e);
        }
    }

    public Customer update(String username, String password, String name, String email, int nif, int phone, String address)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyIncorrectDataType, MyQueryException {
        try {
            Customer customer = findCustomer(username);
            try {
                entityManager.lock(customer, LockModeType.OPTIMISTIC);
                customer.setPassword(hasher.hash(password));
                customer.setName(name);
                customer.setEmail(email);
                customer.setNif(nif);
                customer.setPhone(phone);
                customer.setAddress(address);
            } catch (ConstraintViolationException e) {
                throw new MyIncorrectDataType("Data Type incorreto: " + e);
            }
            return customer;
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "update(): " + e);
        }
    }

    public void delete(String username) throws MyEntityNotFoundException, MyQueryException {
        try {
            Customer customer = entityManager.find(Customer.class, username);
            entityManager.remove(customer);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "delete(): " + e);
        }
    }
}