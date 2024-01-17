package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageSensor;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.UnitProduct;

import java.util.List;
@Stateless
public class UnitProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    @EJB
    private PackageSensorBean packageSensorBean;

    public List<UnitProduct> all() {
        List<UnitProduct> unitProducts =
                entityManager.createNamedQuery("getAllUnitProducts", UnitProduct.class).getResultList();
        unitProducts.forEach(unitProduct -> {
            Hibernate.initialize(unitProduct.getProduct());
            Hibernate.initialize(unitProduct.getProduct());
            if (unitProduct.getPackageSensor() != null) {
                Hibernate.initialize(unitProduct.getPackageSensor());
                if (unitProduct.getPackageSensor() != null)
                    Hibernate.initialize(unitProduct.getPackageSensor().getSensorValues());
            }
        });
        return unitProducts;
    }

    public UnitProduct getUnitProductByProductId(Long productId) {

        List<UnitProduct> unitProducts = entityManager.createNamedQuery("getUnitProductByProductId", UnitProduct.class)
                .setParameter("productId", productId)
                .getResultList();

        return unitProducts.get(0);
    }

    public UnitProduct find(long id) {
        UnitProduct unitProduct = entityManager.find(UnitProduct.class, id);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + id + " not found in database");

        Hibernate.initialize(unitProduct.getProduct());
        if (unitProduct.getPackageSensor() != null) {
            Hibernate.initialize(unitProduct.getPackageSensor());
            if (unitProduct.getPackageSensor() != null)
                Hibernate.initialize(unitProduct.getPackageSensor().getSensorValues());
        }

        return unitProduct;
    }

    public void update(long unitProductId, long packageSensorId) {
        UnitProduct u =  entityManager.find(UnitProduct.class, unitProductId);

        if(u == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        PackageSensor pk = entityManager.find(PackageSensor.class, packageSensorId);

        if(pk == null) throw new IllegalArgumentException("PackageSensor with id " + packageSensorId + " not found in database");

        u.setPackageSensor(pk);

        entityManager.merge(u);
    }

    public List<Sensor> getAllSensorsNotAttribute(Long unitProductId) {
        UnitProduct unitProduct = entityManager.find(UnitProduct.class, unitProductId);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        Hibernate.initialize(unitProduct.getProduct());
        if (unitProduct.getPackageSensor() != null) {
            Hibernate.initialize(unitProduct.getPackageSensor());
            if (unitProduct.getPackageSensor() != null)
                Hibernate.initialize(unitProduct.getPackageSensor().getSensorValues());
        }

        //get list of sensors source from the product
        String source = "Product";
        List<Sensor> sensors = entityManager.createNamedQuery("getSensorsBySource", Sensor.class)
                .setParameter("source", source)
                .getResultList();

        sensors.removeIf(sensor -> unitProduct.getPackageSensor().getSensorValues().stream().anyMatch(sensorValue -> sensorValue.getSensor().getId() == sensor.getId()));

        return sensors;
    }

    public Sensor findSensor(Long sensorId) {
        Sensor sensor = entityManager.find(Sensor.class, sensorId);

        if(sensor == null) throw new IllegalArgumentException("Sensor with id " + sensorId + " not found in database");

        return sensor;
    }

    public UnitProduct addSensorToUnitProduct(UnitProduct unitProduct, Sensor sensor) {
        unitProduct.addSensor(sensor);
        entityManager.merge(unitProduct);
        return unitProduct;
    }

    public UnitProduct findTheUnitProduct(Long unitProductId) {
        UnitProduct unitProduct = entityManager.find(UnitProduct.class, unitProductId);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        return unitProduct;
    }

    public UnitProduct addSensorToTheUnitProduct(Long unitProductId, Long sensorId) {

        UnitProduct unitProduct = find(unitProductId);
        if (unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");
        System.out.println("UnitProduct: " + unitProduct);

        Sensor sensor = entityManager.find(Sensor.class, sensorId);
        if (sensor == null) throw new IllegalArgumentException("Sensor with id " + sensorId + " not found in database");
        System.out.println("Sensor: " + sensor);

        PackageSensor packageSensor = packageSensorBean.findUnitProduct(unitProductId);
        if (packageSensor == null) throw new IllegalArgumentException("PackageSensor with id " + unitProductId + " not found in database");
        System.out.println("PackageSensor: " + packageSensor);

        packageSensorBean.addSensorToPackage(packageSensor.getId(), sensorId);

        Hibernate.initialize(unitProduct.getProduct());
        if (unitProduct.getPackageSensor() != null) {
            Hibernate.initialize(unitProduct.getPackageSensor());
            if (unitProduct.getPackageSensor() != null)
                Hibernate.initialize(unitProduct.getPackageSensor().getSensorValues());
        }

        return unitProduct;

    }
}
