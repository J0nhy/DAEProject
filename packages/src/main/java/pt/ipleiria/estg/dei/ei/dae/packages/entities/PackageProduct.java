package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
        @jakarta.persistence.NamedQuery(name = "getAllProductPackages", query = "SELECT pp FROM PackageProduct pp ORDER BY pp.id")
})
@NamedQuery(name = "getPackagesByType", query = "SELECT pp FROM PackageProduct pp WHERE pp.packageType = :type")
public class PackageProduct extends Package {

    public PackageProduct() {
    }

    public PackageProduct(PackageType packageType, String packageMaterial) {
        super(packageType, packageMaterial);
    }
}
