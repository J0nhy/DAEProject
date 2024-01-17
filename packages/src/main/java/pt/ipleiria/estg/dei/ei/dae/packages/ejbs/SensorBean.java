package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;

import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public long create(String source, String type, String unit, String max, String min) throws MyConstraintViolationException {
        try {
            Sensor sensor = new Sensor(source, type,  unit, max, min);
            entityManager.persist(sensor);

            return sensor.getId().intValue();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public Sensor find(long id) {
        return entityManager.find(Sensor.class, id);
    }

    public List<Sensor> getAll() {
        return entityManager.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }

    public void delete(long id) {
        var sensor = find(id);
        if (sensor != null) {
            entityManager.remove(sensor);
        }
    }

    public void update(long id, String source, String type, String unit, String max, String min) throws MyConstraintViolationException {
        Sensor sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) throw new IllegalArgumentException("Sensor with id " + id + " not found in database");

        try {
            sensor.setSource(source);
            sensor.setType(type);
            sensor.setUnit(unit);
            sensor.setMax(max);
            sensor.setMin(min);
            entityManager.merge(sensor);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Sensor> getSensorsByPackage(Package aPackage) {
        return entityManager.createNamedQuery("getSensorsByPackage", Sensor.class)
                .setParameter("package", aPackage)
                .getResultList();
    }

    public List<Sensor> getSensorsBySource(String source) {
        return entityManager.createNamedQuery("getSensorsBySource", Sensor.class)
                .setParameter("source", source)
                .getResultList();
    }
}
