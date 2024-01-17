package pt.ipleiria.estg.dei.ei.dae.packages.dtos;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.util.List;
import java.util.stream.Collectors;

public class PackageDTO implements java.io.Serializable {
    private Long id;
    private PackageType packageType;
    private String packageMaterial;

    public PackageDTO() {

    }

    public PackageDTO(long id, PackageType packageType, String packageMaterial) {
        this.id = id;
        this.packageType = packageType;
        this.packageMaterial = packageMaterial;
    }

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

    public void setPackageaterial(String packageMaterial) {
        this.packageMaterial = packageMaterial;
    }


    public static PackageDTO from(Package package_) {
        return new PackageDTO(
        );
    }

    public static List<PackageDTO> from(List<Package> packages) {
        return packages.stream().map(PackageDTO::from).collect(Collectors.toList());
    }
}
