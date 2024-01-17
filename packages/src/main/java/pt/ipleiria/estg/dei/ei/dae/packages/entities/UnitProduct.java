package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "unit_products")
@NamedQuery(name = "getAllUnitProducts", query = "SELECT up FROM UnitProduct up ORDER BY up.product.name")
@NamedQuery(name = "getUnitProductByProductId", query = "SELECT up FROM UnitProduct up WHERE up.product.id = :productId")
@NamedQuery(name = "getUnitProductAvailableByProductId", query = "SELECT up FROM UnitProduct up WHERE up.product.id = :productId AND up.available = :available")
public class UnitProduct  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    long id;

    @NotNull
    UUID serialNumber;

    @NotNull
    boolean available;

    @ManyToOne
    private Product product;

    @OneToOne
    private PackageSensor packageSensor;

    public UnitProduct() {
    }

    public UnitProduct(Product product,  UUID serialNumber, boolean available) {
        this.product = product;
        this.serialNumber = serialNumber;
        this.available = available;
    }

    public UnitProduct(Product product,  UUID serialNumber, boolean available, PackageSensor packageSensor) {
        this.product = product;
        this.serialNumber = serialNumber;
        this.available = available;
        this.packageSensor = packageSensor;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UUID getSerialNumber() {
        return serialNumber;
    }

    public long getId() {
        return id;
    }

    public void setAvailable(boolean b) {
        this.available = b;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setSerialNumber(UUID serialNumber) {
        this.serialNumber = serialNumber;
    }

    public PackageSensor getPackageSensor() {

        return packageSensor;
    }
    public void setPackageSensor(PackageSensor packageSensor) {
        this.packageSensor = packageSensor;
    }


    public void addSensor(Sensor sensor) {
        if (packageSensor == null) {
            packageSensor = new PackageSensor();
        }
        packageSensor.addSensor(sensor);
    }
}
