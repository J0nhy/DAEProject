package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.LogisticsOperator;
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
       // if (entityManager.find(Customer.class, username) != null){
         //   throw new MyEntityExistsException("Customer with username: " + username + " already exists");
        //}
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
}