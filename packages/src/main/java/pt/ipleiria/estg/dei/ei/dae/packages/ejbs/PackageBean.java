package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyIncorrectDataType;

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

    public void create(PackageType packageType, PackageMaterials packageMaterial) throws MyIncorrectDataType {
        Package package_ = new Package(packageType, packageMaterial);
        entityManager.persist(package_);

        if (packageType == PackageType.Primary) {
            Sensor sensor = sensorBean.create(SensorType.LOCALIZACAO, "Armazem Lisboa", "local");
            package_.addSensor(sensor);
            sensor.setPackageRef(package_);

        } else if (packageType == PackageType.Secondary) {
            Sensor sensor = sensorBean.create(SensorType.LOCALIZACAO, "Armazem Lisboa", "local");
            Sensor sensor2 = sensorBean.create(SensorType.TEMPERATURA, "10", "ºC");

            package_.addSensor(sensor);
            package_.addSensor(sensor2);
            sensor.setPackageRef(package_);
            sensor2.setPackageRef(package_);

        } else if (packageType == PackageType.Tertiary) {
            Sensor sensor = sensorBean.create(SensorType.LOCALIZACAO, "Armazem Lisboa", "local");
            Sensor sensor2 = sensorBean.create(SensorType.TEMPERATURA, "10", "ºC");
            Sensor sensor3 = sensorBean.create(SensorType.HUMIDADE, "15", "%");
            Sensor sensor4 = sensorBean.create(SensorType.ABERTO, "NO", "YES/NO");

            package_.addSensor(sensor);
            package_.addSensor(sensor2);
            package_.addSensor(sensor3);
            package_.addSensor(sensor4);
            sensor.setPackageRef(package_);
            sensor2.setPackageRef(package_);
            sensor3.setPackageRef(package_);
            sensor4.setPackageRef(package_);
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

        Hibernate.initialize(package_.getSensors());

        return package_;
    }

    public void update(long id, PackageType type, PackageMaterials material) throws Exception, MyIncorrectDataType {
        Package package_ = find(id);

        try {
            entityManager.lock(package_, LockModeType.OPTIMISTIC);

            // orderRef nao faz sentido passar, para isso cancela-se a encomenda e faz-se uma nova
            package_.setPackageType(type);
            package_.setPackageMaterial(material);

            entityManager.merge(package_);
        } catch (ConstraintViolationException e) {
            throw new MyIncorrectDataType("Data Type incorreto: " + e);
        }
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