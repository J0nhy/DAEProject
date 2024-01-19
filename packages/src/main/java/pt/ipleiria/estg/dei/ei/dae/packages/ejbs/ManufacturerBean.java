package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyIncorrectDataType;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Hasher;

import java.util.List;

@Stateless
public class ManufacturerBean {
    @PersistenceContext
    private EntityManager entityManager;

    private Hasher hasher = new Hasher();

    public void create (String username, String password, String name, String email, int nif, int phone, String  address)
            throws MyEntityExistsException, MyConstraintViolationException, MyIncorrectDataType {
        Manufacturer manufacturer = entityManager.find(Manufacturer.class, username);
        if ( manufacturer != null){
            if (!manufacturer.isDeleted())
                throw new MyEntityExistsException("Manufacturer with username: " + username + " already exists");
            throw new MyEntityExistsException("Account with username: " + username + " was deleted.");
        }
        try{
            manufacturer = new Manufacturer(username, hasher.hash(password), name, email, nif, phone, address);
            entityManager.persist(manufacturer);
        }catch (ConstraintViolationException e){
            throw new MyIncorrectDataType("Data Type incorreto: " + e);
        }
    }

    public List<Manufacturer> getAll() {
        return entityManager.createNamedQuery("getAllManufacturers", Manufacturer.class).getResultList();
    }

    public Manufacturer findManufacturer(String username) throws MyEntityNotFoundException {
        Manufacturer manufacturer = entityManager.find(Manufacturer.class, username);
        if (manufacturer == null) {
            throw new MyEntityNotFoundException("Manufacturer with username: " + username + " not found");
        }
        return manufacturer;
    }

    public Manufacturer update(String username, String password, String name, String email, int nif, int phone, String address)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyIncorrectDataType {

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
    }

    public void removeManufacturer(String username) throws MyEntityNotFoundException {
        Manufacturer manufacturer = findManufacturer(username);
        entityManager.remove(manufacturer);
    }
}