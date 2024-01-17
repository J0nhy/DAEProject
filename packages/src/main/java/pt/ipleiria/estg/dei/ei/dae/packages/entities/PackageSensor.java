package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "package_sensors")
@NamedQueries({
        @NamedQuery(
                name = "getAllPackageSensors",
                query = "SELECT c FROM PackageSensor c ORDER BY c.id, c.aPackage.id"),
        @NamedQuery(name = "getPackageSensorByUnitProductId",
                query = "SELECT c FROM PackageSensor c WHERE c.unitProduct.id = :unitProductId"),
})

public class PackageSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    long id;

    @OneToMany
    private List<SensorValue> sensorValues;

    @ManyToOne
    private Package aPackage;

    @OneToOne
    private UnitProduct unitProduct;

    @OneToMany(mappedBy = "packageSensor")
    private List<Order> orders;

    public PackageSensor() {
        this.sensorValues = new ArrayList<>();

    }

    public PackageSensor(Package aPackage, UnitProduct unitProducts) {
        this.aPackage = aPackage;
        this.unitProduct = unitProducts;
        this.sensorValues = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Package getPackagging() {
        return aPackage;
    }

    public void setPackagging(Package aPackage) {
        this.aPackage = aPackage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public UnitProduct getUnitProduct() {
        return unitProduct;
    }

    public void setUnitProduct(UnitProduct unitProduct) {
        this.unitProduct = unitProduct;
    }

    public List<SensorValue> getSensorValues(){
        return sensorValues;
    }

    public void addSensorValue(SensorValue sensorValue) {
        sensorValue.setPackageSensor(this);
        this.sensorValues.add(sensorValue);
    }

    public void addSensor(Sensor sensor) {
        SensorValue sensorValue = new SensorValue(sensor, this);
        sensorValue.setPackageSensor(this);
        this.sensorValues.add(sensorValue);
    }
}
