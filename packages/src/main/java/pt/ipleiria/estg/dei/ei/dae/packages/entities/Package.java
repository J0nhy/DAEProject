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

    @OneToMany
    private List<Sensor> values; // values watched by sensors

    @NotNull
    @OneToMany
    private List<Product> products; // products inside the package
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // referencia da encomenda


    public Package( PackageType packageType, String packageMaterial) {
        this.packageType = packageType;
        this.packageMaterial = packageMaterial;
        this.products = new ArrayList<>();
        this.values =  new ArrayList<>();
    }

    public Package() {
        this.products = new ArrayList<>();
        this.values =  new ArrayList<>();
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

    public List<Product> getProducts() {
        return products;
    }

    public List<Sensor> getValues() {
        return values;
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

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setValues(List<Sensor> values) {
        this.values = values;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", packageType=" + packageType +
                ", packageMaterial='" + packageMaterial + '\'' +
                ", product=" + products +
                ", values=" + values +
                '}';
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public void removeValue(Sensor value) {
        this.values.remove(value);
    }

    public void addValue(Sensor value) {
        this.values.add(value);
    }
    
}