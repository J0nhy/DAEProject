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

    public void create(SensorType sensorType, String value, String dataType, int maxValue, int minValue, long timestamp, Package packageRef) {
        Sensor sensor_ = new Sensor( sensorType, value, dataType, maxValue, minValue, timestamp, packageRef);
        entityManager.persist(sensor_);
    }

    public List<Sensor> all() {
        return entityManager.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }

    public Sensor find(Long SensorId) throws Exception {

        Sensor sensor_ = entityManager.find(Sensor.class, SensorId);
        if (sensor_ == null) {
            throw new Exception("Sensor '" + SensorId + "' not found");
        }
        return entityManager.find(Sensor.class, SensorId);
    }

    public void update(long id, SensorType sensorType, String value, String dataType, int maxValue, int minValue, long timestamp, Package packageRef) throws Exception {
        
        Sensor sensor_ = find(id);

        try {
            entityManager.lock(sensor_, LockModeType.OPTIMISTIC);

            sensor_.setSensorType(sensorType);
            sensor_.setValue(value);
            sensor_.setDataType(dataType);
            sensor_.setMaxValue(maxValue);
            sensor_.setMinValue(minValue);
            sensor_.setTimestamp(timestamp);
            sensor_.setPackageRef(packageRef);
            
            entityManager.merge(sensor_);
            
        } catch (ConstraintViolationException e) {
            throw new Exception(e);
        }
    }

    public void remove(Long id) throws Exception {
        Sensor sensor = find(id);

        if (sensor != null) {
            entityManager.remove(sensor);
        }
    }
}