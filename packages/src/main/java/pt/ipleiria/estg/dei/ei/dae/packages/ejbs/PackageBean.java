package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import org.glassfish.jaxb.runtime.v2.runtime.reflect.Lister;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyIncorrectDataType;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyQueryException;

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


    public Package create(PackageType packageType, PackageMaterials packageMaterial) throws MyIncorrectDataType, MyQueryException {
        try {
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

            return package_;
        } catch (ConstraintViolationException | MyEntityNotFoundException e) {
            throw new MyQueryException("Error fetching data in query " + "create(): " + e);
        }

    }

    public List<Package> all() throws MyQueryException, MyEntityNotFoundException {
        try {
            List<Package> resultList = entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No Packages found");
            }

            return resultList;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "all(): " + e);
        }
    }

    public Package find(long packageId) throws MyEntityNotFoundException, MyQueryException {
        try {
            Package package_ = entityManager.find(Package.class, packageId);
            if (package_ == null) {
                throw new MyEntityNotFoundException("Package '" + packageId + "' not found");
            }

            Hibernate.initialize(package_.getSensors());

            return package_;
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Package '" + packageId + "' not found");
        }
    }

    public void update(long id, PackageType type, PackageMaterials material) throws MyEntityNotFoundException, MyQueryException, MyIncorrectDataType {

        try {

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
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "update(): " + e);
        }
    }

    public void removePackage(long id) throws MyEntityNotFoundException, MyQueryException {
        try {
            Package package_ = find(id);
            entityManager.remove(package_);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "removePackage(): " + e);
        }
    }

    public void removeValueFromPackage(long packageId, long valueId) throws MyEntityNotFoundException, MyQueryException{
        try {
            Package package_ = find(packageId);
            SensorBean valueBean = new SensorBean();

            Sensor value = valueBean.find(valueId);

            if (value == null) {
                throw new MyEntityNotFoundException("Sensor '" + valueId + "' not found");
            }
            package_.removeSensor(value);
            entityManager.merge(package_);
        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "removeValueFromPackage(): " + e);
        }
    }

    public List<Package> packagesWithOrders() throws MyQueryException, MyEntityNotFoundException{
        try {
            List<Package> resultList = entityManager.createNamedQuery("getPackagesWithOrders", Package.class).getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No customers found");
            }

            return resultList;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "packagesWithOrders(): " + e);
        }
    }

    public List<Package> packagesWithoutOrders() throws MyQueryException, MyEntityNotFoundException{
        try {
            List<Package> resultList = entityManager.createNamedQuery("getPackagesWithoutOrders", Package.class).getResultList();

            if (resultList.isEmpty()) {
                throw new MyEntityNotFoundException("No customers found");
            }

            return resultList;

        } catch (ConstraintViolationException e) {
            throw new MyQueryException("Error fetching data in query " + "packagesWithoutOrders(): " + e);
        }
    }
}