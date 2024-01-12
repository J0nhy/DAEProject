package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Hasher;

import java.util.List;

@Stateless
public class CustomerBean {
    @PersistenceContext
    private EntityManager entityManager;

    private Hasher hasher = new Hasher();

    public void create (String username, String password, String name, String email, int nif, int phone, String  address){
        Customer customer = null;
        if (entityManager.find(Customer.class, username) != null){
            //throw exeption
            System.out.println("Username: " + username + "already in use");
            return;
        }
        try{
            customer = new Customer(username, hasher.hash(password), name, email, nif, phone, address);
            entityManager.persist(customer);

            System.out.println("Created user:" + username);
        }catch (Exception e){
            //throw exeption
            System.out.println("Error creating user:" + username);
            return;
        }

    }

    public List<Customer> getAll() {
        return entityManager.createNamedQuery("getAllCustomers", Customer.class).getResultList();
    }

    public Customer findCustomer(String username) {
        return entityManager.find(Customer.class, username);
    }

    public Customer update(String username, String password, String name, String email, int nif, int phone, String address) {
        Customer customer = findCustomer(username);
        if (customer == null) {
            return null;
        }
        entityManager.lock(customer, LockModeType.OPTIMISTIC);
        customer.setPassword(hasher.hash(password));
        customer.setName(name);
        customer.setEmail(email);
        customer.setNif(nif);
        customer.setPhone(phone);
        customer.setAddress(address);
        return customer;
    }

    public void removeStudent(String username) {
        Customer customer = findCustomer(username);
        if (customer == null) {
            return;
        }
        entityManager.remove(customer);
    }
}