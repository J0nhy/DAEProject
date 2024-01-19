package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PackageDTO implements Serializable{
    private long id;
    private PackageType PackageType;
    private PackageMaterials packageMaterial;
    private long orderId;
    private List<SensorDTO> sensors;

    public PackageDTO(long id, PackageType PackageType, PackageMaterials packageMaterial, long orderId, List<SensorDTO> sensors) {
        this.id = id;
        this.PackageType = PackageType;
        this.packageMaterial = packageMaterial;
        this.orderId = orderId;
        this.sensors = sensors;
    }

    public PackageDTO() {
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

    public PackageMaterials getPackageMaterial() {
        return packageMaterial;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setPackageType(PackageType PackageType) {
        this.PackageType = PackageType;
    }

    public void setPackageMaterial(PackageMaterials packageMaterial) {
        this.packageMaterial = packageMaterial;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<SensorDTO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }

}