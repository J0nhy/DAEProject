package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class PackageBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SensorBean valueBean;

    @EJB
    private OrderBean orderBean;

    @EJB
    private SensorBean sensorBean;

    public void create( PackageType packageType, String packageMaterial){
            Package package_ = new Package(packageType, packageMaterial);
            entityManager.persist(package_);

            if (packageType == PackageType.Primary){
                package_.addSensor(sensorBean.create(SensorType.Location, "Armazem Lisboa","local", package_));
            } else if (packageType == PackageType.Secondary){
                package_.addSensor(sensorBean.create(SensorType.Location, "Armazem Lisboa","local", package_));
                package_.addSensor(sensorBean.create(SensorType.Temperature, "10","ºC", package_));
            } else if (packageType == PackageType.Tertiary){
                package_.addSensor(sensorBean.create(SensorType.Location, "Armazem Lisboa","local", package_));
                package_.addSensor(sensorBean.create(SensorType.Temperature, "10","ºC", package_));
                package_.addSensor(sensorBean.create(SensorType.Humidity, "15","%", package_));
                package_.addSensor(sensorBean.create(SensorType.Opened, "NO","YES/NO", package_));
            }
    }

    public List<Package> all() {
        return entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();
    }

    public Package find(long packageId) throws MyEntityNotFoundException {
        Package package_ = entityManager.find(Package.class, packageId);
        if (package_ == null) {
            throw new MyEntityNotFoundException("Package '" + packageId + "' not found");
        }
        return package_;
    }

    public void update(long id, PackageType type, String material) throws Exception {
        Package package_ = find(id);

        entityManager.lock(package_, LockModeType.OPTIMISTIC);

        // orderRef nao faz sentido passar, para isso cancela-se a encomenda e faz-se uma nova
        package_.setPackageType(type);
        package_.setPackageMaterial(material);
    }

    public void removePackage(long id) throws Exception {
        Package package_ = find(id);
        entityManager.remove(package_);
    }

    public void addValueToPackage(long packageId, long valueId) throws Exception {
        Package package_ = find(packageId);
        SensorBean valueBean = new SensorBean();

        Sensor sensor = valueBean.find(valueId);

        if (sensor == null) {
            throw new Exception("Sensor '" + valueId + "' not found");
        }

        package_.addSensor(sensor);
        entityManager.merge(package_);
    }

    public void removeValueFromPackage(long packageId, long valueId) throws Exception {
        Package package_ = find(packageId);
        SensorBean valueBean = new SensorBean();

        Sensor value = valueBean.find(valueId);

        if (value == null) {
            throw new Exception("Sensor '" + valueId + "' not found");
        }
        package_.removeSensor(value);
        entityManager.merge(package_);
    }
}