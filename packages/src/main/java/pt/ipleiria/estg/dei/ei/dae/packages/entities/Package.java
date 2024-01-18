package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packages")
@NamedQuery(name = "getAllPackages", query = "select p from Package p order by p.packageType")
public class Package implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // package id

    @NotNull
    private PackageType packageType; // primary, secondary, tertiary

    @NotNull
    private String packageMaterial; // material of the package

    @OneToMany(mappedBy = "packageRef", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Sensor> sensors; // values watched by sensors

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // referencia da encomenda


    public Package( PackageType packageType, String packageMaterial) {
        this.packageType = packageType;
        this.packageMaterial = packageMaterial;
        this.sensors =  new ArrayList<>();
    }

    public Package() {
        this.sensors =  new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public String getPackageMaterial() {
        return packageMaterial;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public void setPackageMaterial(String packageMaterial) {
        this.packageMaterial = packageMaterial;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public long getOrderId() {
        return order.getId();
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", packageType=" + packageType +
                ", packageMaterial='" + packageMaterial + '\'' +
                ", values=" + sensors +
                '}';
    }

    public void removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
    }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
    }

    public void removeOrder(Order order) {
        this.order = null;
    }
}