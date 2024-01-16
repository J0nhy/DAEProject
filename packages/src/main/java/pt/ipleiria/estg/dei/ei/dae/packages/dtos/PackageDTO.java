package pt.ipleiria.estg.dei.ei.dae.packages.dtos;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PackageDTO implements Serializable{
    private Long id;
    private PackageType PackageType;
    private String packageMaterial;
    private String orderRef;
    private List<Sensor> values;
    private List<Product> products;

    public PackageDTO(Long id, PackageType PackageType, String packageMaterial, String orderRef, List<Sensor> values, List<Product> products) {
        this.id = id;
        this.PackageType = PackageType;
        this.packageMaterial = packageMaterial;
        this.orderRef = orderRef;
        this.products = products;
        this.values =  values;
    }

    public PackageDTO() {
        this.products = new ArrayList<>();
        this.values =  new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public PackageType getPackageType() {
        return PackageType;
    }

    public String getPackageMaterial() {
        return packageMaterial;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPackageType(PackageType PackageType) {
        this.PackageType = PackageType;
    }

    public void setPackageMaterial(String packageMaterial) {
        this.packageMaterial = packageMaterial;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public List<Sensor> getValues() {
        return values;
    }

    public void setValues(List<Sensor> values) {
        this.values = values;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}