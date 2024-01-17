package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageProduct;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;

import java.io.Serializable;
import java.util.List;

public class PackageProductDTO implements Serializable {

    private Long id;
    private PackageType packageType;
    private String packageMaterial;

    public PackageProductDTO() {

    }

    public PackageProductDTO(PackageType packageType, String packageMaterial) {
        this.packageType = packageType;
        this.packageMaterial = packageMaterial;
    }

    public PackageProductDTO(long id, PackageType packageType, String packageMaterial) {
        this.id = id;
        this.packageType = packageType;
        this.packageMaterial = packageMaterial;
    }

    public List<PackageProductDTO> toDTOs(List<PackageProduct> packageProducts) {
        return packageProducts.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    public PackageProductDTO toDTO(PackageProduct packageProduct) {
        return new PackageProductDTO(
                packageProduct.getId(),
                packageProduct.getPackageType(),
                packageProduct.getPackageMaterial()
        );

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

    public void setPackagingType(PackageType packageType) {
        this.packageType = packageType;
    }

    public String getPackageMaterial() {
        return packageMaterial;
    }

    public void setPackageMaterial(String packageMaterial) {
        this.packageMaterial = packageMaterial;
    }



}
