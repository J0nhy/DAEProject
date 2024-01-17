package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "packages")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(name = "getPackageByType", query = "SELECT p FROM Package p WHERE p.packageType = :packageType"),
        @NamedQuery(name = "getAllPackages", query = "SELECT p FROM Package p ORDER BY p.id")
})
public class Package extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    private Long id;
    @NotNull
    private PackageType packageType;  // [1º,2º,3º(Produto) ou encomenda / transporte]
    @NotNull
    private String packageMaterial;

    @OneToMany(mappedBy = "aPackage")
    private List<PackageSensor> packageSensors;

    public Package() {

    }
    public Package(PackageType packageType, String packageMaterial) {
        this.packageType = packageType;
        this.packageMaterial = packageMaterial;
    }

    public Long getId() {
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

}
