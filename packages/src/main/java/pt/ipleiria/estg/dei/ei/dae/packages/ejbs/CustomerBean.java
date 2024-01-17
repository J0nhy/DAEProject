package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Hasher;

import java.util.List;

@Stateless
public class CustomerBean {
    @PersistenceContext
    private EntityManager entityManager;

    private Hasher hasher = new Hasher();

    public void create (String username, String password, String name, String email, int nif, int phone, String  address)
        throws MyEntityExistsException, MyConstraintViolationException {
        Customer customer = null;
        if (entityManager.find(Customer.class, username) != null){
            throw new MyEntityExistsException("Customer with username: " + username + " already exists");
        }
        try{
            customer = new Customer(username, hasher.hash(password), name, email, nif, phone, address);
            entityManager.persist(customer);
        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Customer> getAll() {
        return entityManager.createNamedQuery("getAllCustomers", Customer.class).getResultList();
    }

    public Customer findCustomer(String username) throws MyEntityNotFoundException {
        Customer customer = entityManager.find(Customer.class, username);
        if (customer == null) {
            throw new MyEntityNotFoundException("Customer with username: " + username + " not found");
        }
        Hibernate.initialize(customer.getOrders());
        return customer;
    }

    public Customer update(String username, String password, String name, String email, int nif, int phone, String address)
        throws MyEntityNotFoundException, MyConstraintViolationException {
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
            throw new MyConstraintViolationException(e);
        }
        return customer;
    }

    public void removeStudent(String username) throws MyEntityNotFoundException {
        Customer customer = findCustomer(username);
        entityManager.remove(customer);
    }

    public void delete(String username){
        var customer = entityManager.find(Customer.class, username);
        entityManager.remove(customer);
    }
}