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

    public void create( PackageType packageType, String packageMaterial){
            Package package_ = new Package(packageType, packageMaterial);
            entityManager.persist(package_);
            entityManager.flush();
    }

    public List<Package> all() {
        return entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();
    }

    public Package find(Long packageId) throws MyEntityNotFoundException {
        Package package_ = entityManager.find(Package.class, packageId);
        if (package_ == null) {
            throw new MyEntityNotFoundException("Package '" + packageId + "' not found");
        }
        return entityManager.find(Package.class, packageId);
    }

    public void update(Long id, PackageType type, String material) throws Exception {
        Package package_ = find(id);

        entityManager.lock(package_, LockModeType.OPTIMISTIC);

        // orderRef nao faz sentido passar, para isso cancela-se a encomenda e faz-se uma nova
        package_.setPackageType(type);
        package_.setPackageMaterial(material);
    }

    public void removePackage(Long id) throws Exception {
        Package package_ = find(id);
        entityManager.remove(package_);
    }

    public void addValueToPackage(Long packageId, Long valueId) throws Exception {
        Package package_ = find(packageId);
        SensorBean valueBean = new SensorBean();

        Sensor value = valueBean.find(valueId);

        if (value == null) {
            throw new Exception("Sensor '" + valueId + "' not found");
        }

        package_.addValue(value);
        entityManager.merge(package_);
    }

    public void removeValueFromPackage(Long packageId, Long valueId) throws Exception {
        Package package_ = find(packageId);
        SensorBean valueBean = new SensorBean();

        Sensor value = valueBean.find(valueId);

        if (value == null) {
            throw new Exception("Sensor '" + valueId + "' not found");
        }
        package_.removeValue(value);
        entityManager.merge(package_);
    }
}