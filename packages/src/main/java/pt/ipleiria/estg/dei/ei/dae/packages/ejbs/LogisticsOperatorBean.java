package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.LogisticsOperator;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.*;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Hasher;

import java.util.List;

@Stateless
public class LogisticsOperatorBean {
    @PersistenceContext
    private EntityManager entityManager;

    private Hasher hasher = new Hasher();

    public void create(String username, String password, String name, String email, String company)
            throws MyEntityExistsException, MyConstraintViolationException, MyIncorrectDataType {
        LogisticsOperator logistics = entityManager.find(LogisticsOperator.class, username);
        if (logistics != null) {
            if (!logistics.isDeleted())
                throw new MyEntityExistsException("Logistics Operator with username: " + username + " already exists");
            throw new MyEntityExistsException("Account with username: " + username + " was deleted.");
        }
        try {
            logistics = new LogisticsOperator(username, hasher.hash(password), name, email, company);
            entityManager.persist(logistics);
        } catch (ConstraintViolationException e) {
            throw new MyIncorrectDataType("Data Type incorreto: " + e);
        }
    }

    public List<LogisticsOperator> getAll() throws MyQueryException, MyEntityNotFoundException {
        try {
            List<LogisticsOperator> resultList = entityManager.createNamedQuery("getAllLogisticsOperators", LogisticsOperator.class).getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No LogisticsOperators found");
            }

            return resultList;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "getAll(): " + e);
        }
    }

    public LogisticsOperator findLogisticOperator(String username) throws MyEntityNotFoundException, MyQueryException {
        try {
            LogisticsOperator logisticsOperator = entityManager.find(LogisticsOperator.class, username);
            if (logisticsOperator == null) {
                throw new MyEntityNotFoundException("Logistic Operator with username: " + username + " not found");
            }
            //Hibernate.initialize(logisticsOperator.getOrders());
            return logisticsOperator;
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "findLogisticOperator(): " + e);
        }
    }

    public LogisticsOperator update(String username, String password, String name, String email, String company)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyIncorrectDataType, MyQueryException {

        try {

            LogisticsOperator logisticsOperator = findLogisticOperator(username);
            try {
                entityManager.lock(logisticsOperator, LockModeType.OPTIMISTIC);
                logisticsOperator.setPassword(hasher.hash(password));
                logisticsOperator.setName(name);
                logisticsOperator.setEmail(email);
                logisticsOperator.setCompany(company);

            } catch (ConstraintViolationException e) {
                throw new MyIncorrectDataType("Data Type incorreto: " + e);
            }
            return logisticsOperator;
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "update(): " + e);
        }
    }

    public void removeLogisticsOperator(String username) throws MyEntityNotFoundException, MyQueryException {
        try {
            LogisticsOperator logisticsOperator = findLogisticOperator(username);
            entityManager.remove(logisticsOperator);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "removeLogisticsOperator(): " + e);
        }
    }
}