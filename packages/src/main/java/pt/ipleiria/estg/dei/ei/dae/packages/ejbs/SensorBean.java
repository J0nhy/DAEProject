package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.SensorType;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyIncorrectDataType;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyQueryException;

import java.util.List;

@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    public Sensor create(SensorType sensorType, String value, String dataType) throws MyQueryException, MyEntityNotFoundException {
        try {

            Sensor sensor_ = new Sensor(sensorType, value, dataType);
            if (sensor_ == null) {
                throw new MyEntityNotFoundException("No sensors found");
            }
            entityManager.persist(sensor_);
            return sensor_;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Data Type incorreto: " + e);
        }
    }

    public List<Sensor> all() throws MyQueryException {
        try {
            List<Sensor> resultList = entityManager.createNamedQuery("getAllSensors", Sensor.class).getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No sensors found");
            }

            return resultList;

        } catch (ConstraintViolationException | MyEntityNotFoundException e) {
            throw new MyQueryException("Error fetching data in query " + "all(): " + e);
        }
    }

    public List<Sensor> allByPackage(long id) throws MyQueryException, MyEntityNotFoundException {

        try {
            List<Sensor> resultList = entityManager
                    .createNamedQuery("getAllSensorsByPackage", Sensor.class)
                    .setParameter("id", id)  // Assuming "logisticsOperatorId" is the parameter name in the named query
                    .getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No sensors found");
            }

            return resultList;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "allByPackage(): " + e);
        }
    }

    public Sensor find(long SensorId) throws MyEntityNotFoundException, MyQueryException {
        try {
            Sensor sensor_ = entityManager.find(Sensor.class, SensorId);
            if (sensor_ == null) {
                throw new MyEntityNotFoundException("Sensor '" + SensorId + "' not found");
            }
            return entityManager.find(Sensor.class, SensorId);

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "find(): " + e);
        }
    }

    public void update(long id, SensorType sensorType, String value, String dataType) throws MyEntityNotFoundException, MyQueryException, MyIncorrectDataType {

        try {
            Sensor sensor_ = find(id);

            try {
                entityManager.lock(sensor_, LockModeType.OPTIMISTIC);

                sensor_.setSensorType(sensorType);
                sensor_.setValue(value);
                sensor_.setDataType(dataType);

                entityManager.merge(sensor_);

            } catch (ConstraintViolationException e) {
                throw new MyIncorrectDataType("Data Type incorreto: " + e);
            }
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "update(): " + e);
        }
    }

    public void remove(long id) throws MyEntityNotFoundException, MyQueryException {
        try {
            Sensor sensor = find(id);

            if (sensor != null) {
                entityManager.remove(sensor);
            }
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "remove(): " + e);
        }
    }
}