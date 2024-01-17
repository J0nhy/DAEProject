package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.util.List;

@Stateless
public class PackageSensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PackageSensor> all() {
        return entityManager.createNamedQuery("getAllPackageSensors", PackageSensor.class).getResultList();
    }

    public void create(long packageId, long unitProductId){
        Package aPackage = entityManager.find(Package.class, packageId);

        if(aPackage == null) throw new IllegalArgumentException("Package with id " + packageId + " not found in database");

        UnitProduct unitProduct = entityManager.find(UnitProduct.class, unitProductId);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        PackageSensor packageSensor = new PackageSensor(aPackage, unitProduct);

        entityManager.persist(packageSensor);
    }

    public void addUnitProductToPackage(long packageSensorId, long unitProductId){
        PackageSensor packageSensor = entityManager.find(PackageSensor.class, packageSensorId);

        if(packageSensor == null) throw new IllegalArgumentException("PackageSensor with id " + packageSensorId + " not found in database");

        UnitProduct unitProduct = entityManager.find(UnitProduct.class, unitProductId);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        //packageSensor.getUnitProducts().add(unitProduct);

        entityManager.merge(packageSensor);

    }

    public PackageSensor find(int i) {
        return entityManager.find(PackageSensor.class, i);
    }

    public void addSensorToPackage(long packageSensorId, long sensorId) {

        PackageSensor packageSensor = entityManager.find(PackageSensor.class, packageSensorId);
        if(packageSensor == null) throw new IllegalArgumentException("PackageSensor with id " + packageSensorId + " not found in database");

        Sensor sensor = entityManager.find(Sensor.class, sensorId);
        if(sensor == null) throw new IllegalArgumentException("PackageSensor with id " + sensorId + " not found in database");

        SensorValue sensorValue = new SensorValue(sensor,packageSensor);

        SensorValue sensorValue1 = entityManager.find(SensorValue.class, sensorValue.getId());

        if(sensorValue1 != null) throw new IllegalArgumentException("SensorValue not found in database");

        packageSensor.addSensorValue(sensorValue);

        entityManager.persist(sensorValue);
    }

    public PackageSensor findUnitProduct(Long unitProductId) {
        PackageSensor packageSensor = entityManager.createNamedQuery("getPackageSensorByUnitProductId", PackageSensor.class)
                .setParameter("unitProductId", unitProductId)
                .getSingleResult();

        return packageSensor;
    }
}
