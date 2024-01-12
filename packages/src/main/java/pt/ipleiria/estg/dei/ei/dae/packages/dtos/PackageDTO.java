package pt.ipleiria.estg.dei.ei.dae.packages.dtos;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PackageDTO implements Serializable{
    private Long id;
    private PackageType PackageType;
    private String packageMaterial;
    private String orderRef;

    public PackageDTO(Long id, PackageType PackageType, String packageMaterial, String orderRef) {
        this.id = id;
        this.PackageType = PackageType;
        this.packageMaterial = packageMaterial;
        this.orderRef = orderRef;
    }

    public PackageDTO() {
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
}