package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "getAllPackages", query = "select p from Package p order by p.packageType")
public class Package implements Serializable {

    @Id
    private Long id; // package id

    @NotNull
    private PackageType packageType; // primary, secondary, tertiary

    @NotNull
    private String packageMaterial; // material of the package

    @OneToMany
    private List<Sensor> values; // values watched by sensors

    @NotNull
    @OneToMany
    private List<Product> products; // products inside the package

    @NotNull
    private String orderRef; // referencia da encomenda


    public Package(Long id, PackageType packageType, String packageMaterial, String orderRef) {
        this.id = id;
        this.packageType = packageType;
        this.packageMaterial = packageMaterial;
        this.orderRef = orderRef;
        this.products = new ArrayList<>();
        this.values =  new ArrayList<>();
    }

    public Package() {
        this.values =  new ArrayList<>();
    }

    public Long getId() {
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

    public void setId(Long id) {
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

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
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