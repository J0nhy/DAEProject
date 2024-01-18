package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PackageDTO implements Serializable{
    private long id;
    private PackageType PackageType;
    private String packageMaterial;
    private Order order;
    private List<Sensor> sensors;
    private List<Product> products;

    public PackageDTO( PackageType PackageType, String packageMaterial, Order order) {
        this.PackageType = PackageType;
        this.packageMaterial = packageMaterial;
        this.order = order;
        this.products = new ArrayList<>();
        this.sensors =  new ArrayList<>();
    }

    public PackageDTO() {
        this.products = new ArrayList<>();
        this.sensors =  new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PackageType getPackageType() {
        return PackageType;
    }

    public String getPackageMaterial() {
        return packageMaterial;
    }

    public Order getOrder() {
        return order;
    }

    public void setPackageType(PackageType PackageType) {
        this.PackageType = PackageType;
    }

    public void setPackageMaterial(String packageMaterial) {
        this.packageMaterial = packageMaterial;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}