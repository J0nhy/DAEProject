package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageOrder;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;

import java.io.Serializable;
import java.util.List;

public class PackageOrderDTO implements Serializable {

    private Long id;
    private PackageType packageType;
    private String packageMaterial;

    public PackageOrderDTO() {

    }

    public PackageOrderDTO(Long id, PackageType packageType, String packageMaterial) {
        this.id = id;
        this.packageType = packageType;
        this.packageMaterial = packageMaterial;
    }

    public PackageOrderDTO(PackageType packageType, String packageMaterial) {
        this.packageType = packageType;
        this.packageMaterial = packageMaterial;
    }

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public String getPackageMaterial() {
        return packageMaterial;
    }

    public void setPackageMaterial(String packageMaterial) {
        this.packageMaterial = packageMaterial;
    }

    public static List<PackageOrderDTO> toDTOs(List<PackageOrder> packageOrder) {
        return  packageOrder.stream().map(PackageOrderDTO::toDTO).collect(java.util.stream.Collectors.toList());
    }

    public static PackageOrderDTO toDTO(PackageOrder packageOrder) {
        return new PackageOrderDTO(
                packageOrder.getId(),
                packageOrder.getPackageType(),
                packageOrder.getPackageMaterial()
        );
    }
}
