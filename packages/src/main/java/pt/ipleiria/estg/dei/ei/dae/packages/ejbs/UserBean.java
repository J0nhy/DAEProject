package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.User;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Hasher;

import java.security.Principal;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public User find(String username) {
        return em.find(User.class, username);
    }
    public User findOrFail(String username) {
        var user = em.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }
    public boolean canLogin(String username, String password) {
        var user = find(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

    public boolean isManufacturer(String username) {
        try {
            Manufacturer user = em.createNamedQuery("isUserManufacturer", Manufacturer.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return user != null;
        } catch (NoResultException e) {
            return false; // No matching result, so the user is not a manufacturer
        }
    }
}