package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.LogisticsOperator;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Hasher;

import java.util.List;

@Stateless
public class LogisticsOperatorBean {
    @PersistenceContext
    private EntityManager entityManager;

    private Hasher hasher = new Hasher();

    public void create (String username, String password, String name, String email, String company)
            throws MyEntityExistsException, MyConstraintViolationException {
        LogisticsOperator logisticsOperator = null;
       if (entityManager.find(Customer.class, username) != null){
           throw new MyEntityExistsException("Customer with username: " + username + " already exists");
        }
        try{
            logisticsOperator = new LogisticsOperator(username, hasher.hash(password), name, email, company);
            entityManager.persist(logisticsOperator);
        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }
    public List<LogisticsOperator> getAll() {
        return entityManager.createNamedQuery("getAllLogisticsOperators", LogisticsOperator.class).getResultList();
    }

    public LogisticsOperator findLogisticOperator(String username) throws MyEntityNotFoundException {
        LogisticsOperator logisticsOperator = entityManager.find(LogisticsOperator.class, username);
        if (logisticsOperator == null) {
            throw new MyEntityNotFoundException("Logistic Operator with username: " + username + " not found");
        }
        //Hibernate.initialize(logisticsOperator.getOrders());
        return logisticsOperator;
    }

    public LogisticsOperator update(String username, String password, String name, String email, String company)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        LogisticsOperator logisticsOperator = findLogisticOperator(username);
        try {
            entityManager.lock(logisticsOperator, LockModeType.OPTIMISTIC);
            logisticsOperator.setPassword(hasher.hash(password));
            logisticsOperator.setName(name);
            logisticsOperator.setEmail(email);
            logisticsOperator.setCompany(company);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        return logisticsOperator;
    }

        public void removeLogisticsOperator(String username) throws MyEntityNotFoundException {
            LogisticsOperator logisticsOperator = findLogisticOperator(username);
            entityManager.remove(logisticsOperator);
        }
}