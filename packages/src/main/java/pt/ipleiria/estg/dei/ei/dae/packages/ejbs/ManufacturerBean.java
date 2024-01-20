package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.*;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Hasher;

import java.util.List;

@Stateless
public class ManufacturerBean {
    @PersistenceContext
    private EntityManager entityManager;

    private Hasher hasher = new Hasher();

    public void create(String username, String password, String name, String email, int nif, int phone, String address)
            throws MyEntityExistsException, MyConstraintViolationException, MyIncorrectDataType {
        Manufacturer manufacturer = entityManager.find(Manufacturer.class, username);
        if (manufacturer != null) {
            if (!manufacturer.isDeleted())
                throw new MyEntityExistsException("Manufacturer with username: " + username + " already exists");
            throw new MyEntityExistsException("Account with username: " + username + " was deleted.");
        }
        try {
            manufacturer = new Manufacturer(username, hasher.hash(password), name, email, nif, phone, address);
            entityManager.persist(manufacturer);
        } catch (ConstraintViolationException e) {
            throw new MyIncorrectDataType("Data Type incorreto: " + e);
        }
    }

    public List<Manufacturer> getAll() throws MyQueryException, MyEntityNotFoundException {
        try {
            List<Manufacturer> resultList = entityManager.createNamedQuery("getAllManufacturers", Manufacturer.class).getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No customers found");
            }

            return resultList;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "getAll(): " + e);
        }
    }

    public Manufacturer findManufacturer(String username) throws MyEntityNotFoundException, MyQueryException {
        try {
            Manufacturer manufacturer = entityManager.find(Manufacturer.class, username);
            if (manufacturer == null) {
                throw new MyEntityNotFoundException("Manufacturer with username: " + username + " not found");
            }
            return manufacturer;
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "findManufacturer(): " + e);
        }
    }

    public Manufacturer update(String username, String password, String name, String email, int nif, int phone, String address)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyIncorrectDataType, MyQueryException {

        try {
            Manufacturer manufacturer = findManufacturer(username);
            try {
                entityManager.lock(manufacturer, LockModeType.OPTIMISTIC);

                manufacturer.setPassword(hasher.hash(password));
                manufacturer.setName(name);
                manufacturer.setEmail(email);
                manufacturer.setNif(nif);
                manufacturer.setPhone(phone);
                manufacturer.setAddress(address);

            } catch (ConstraintViolationException e) {
                throw new MyIncorrectDataType("Data Type incorreto: " + e);
            }
            return manufacturer;
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "update(): " + e);
        }
    }

    public void removeManufacturer(String username) throws MyEntityNotFoundException, MyQueryException {
        try {
            Manufacturer manufacturer = findManufacturer(username);
            entityManager.remove(manufacturer);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "removeManufacturer(): " + e);
        }
    }
}