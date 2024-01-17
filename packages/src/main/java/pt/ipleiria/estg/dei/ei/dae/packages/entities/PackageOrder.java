package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;

import java.util.List;

@Entity
@NamedQuery(name = "getAllPackageOrders", query = "SELECT pp FROM PackageOrder pp ORDER BY pp.id")
public class PackageOrder extends Package {

    public PackageOrder() {
    }

    public PackageOrder(PackageType packagingType, String packagingMaterial) {
        super(packagingType, packagingMaterial);
    }
}
