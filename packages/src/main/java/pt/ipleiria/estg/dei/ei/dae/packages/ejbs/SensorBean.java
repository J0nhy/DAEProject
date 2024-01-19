package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.SensorType;

import java.util.List;

@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    public Sensor create(SensorType sensorType, String value, String dataType) {
        Sensor sensor_ = new Sensor( sensorType, value, dataType);
        entityManager.persist(sensor_);
        return sensor_;
    }

    public List<Sensor> all() {
        return entityManager.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }

    public List<Sensor> allByPackage(long id) {
        return entityManager
                .createNamedQuery("getAllSensorsByPackage", Sensor.class)
                .setParameter("id", id)  // Assuming "logisticsOperatorId" is the parameter name in the named query
                .getResultList();
    }

    public Sensor find(long SensorId) throws Exception {

        Sensor sensor_ = entityManager.find(Sensor.class, SensorId);
        if (sensor_ == null) {
            throw new Exception("Sensor '" + SensorId + "' not found");
        }
        return entityManager.find(Sensor.class, SensorId);
    }

    public void update(long id, SensorType sensorType, String value, String dataType) throws Exception {
        
        Sensor sensor_ = find(id);

        try {
            entityManager.lock(sensor_, LockModeType.OPTIMISTIC);

            sensor_.setSensorType(sensorType);
            sensor_.setValue(value);
            sensor_.setDataType(dataType);

            entityManager.merge(sensor_);
            
        } catch (ConstraintViolationException e) {
            throw new Exception(e);
        }
    }

    public void remove(long id) throws Exception {
        Sensor sensor = find(id);

        if (sensor != null) {
            entityManager.remove(sensor);
        }
    }
}