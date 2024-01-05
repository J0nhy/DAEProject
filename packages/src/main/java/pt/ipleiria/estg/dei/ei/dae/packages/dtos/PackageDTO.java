package com.packages.dtos;
import com.packages.entities.Package;
import com.packages.entities.PackageType;

import java.util.List;
import java.util.stream.Collectors;

public class PackageDTO {
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

    public static PackageDTO from(Package package_) {
        return new PackageDTO(
                package_.getId(),
                package_.getPackageType(),
                package_.getPackageMaterial(),
                package_.getOrderRef()
        );
    }

    public static List<PackageDTO> from(List<Package> packages) {
        return packages.stream().map(PackageDTO::from).collect(Collectors.toList());
    }
}