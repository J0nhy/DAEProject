package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packages")
@NamedQueries({
        @NamedQuery(name = "getAllPackages", query = "select p from Package p order by p.id"),
        @NamedQuery(name = "getPackagesWithOrders", query = "select p from Package p where p.order.id IS NOT NULL order by p.id"),
        @NamedQuery(name = "getPackagesWithoutOrders", query = "select p from Package p where p.order.id IS NULL order by p.id")
})
@SQLDelete(sql="UPDATE packages SET deleted = TRUE WHERE id = ? AND version = ? ")
@Where(clause = "deleted IS FALSE")
public class Package extends Versionable implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // package id

    @NotNull
    private PackageType packageType; // primary, secondary, tertiary

    @NotNull
    private PackageMaterials packageMaterial; // material of the package

    @OneToMany(mappedBy = "packageRef", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Sensor> sensors; // values watched by sensors

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // referencia da encomenda

    private boolean deleted;


    public Package( PackageType packageType, PackageMaterials packageMaterial) {
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

    public PackageMaterials getPackageMaterial() {
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

    public void setPackageMaterial(PackageMaterials packageMaterial) {
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
        if (order == null)
            return -1;
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