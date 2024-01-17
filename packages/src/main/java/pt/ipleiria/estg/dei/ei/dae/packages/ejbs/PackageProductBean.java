package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.PackageProductDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageProduct;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;


import java.util.List;

@Stateless
public class PackageProductBean {

    @PersistenceContext
    private EntityManager entityManager;
    public List<PackageProduct> all() {
        return entityManager.createNamedQuery("getAllProductPackages", PackageProduct.class).getResultList();
    }

    public PackageProduct find(long id) {
        return entityManager.find(PackageProduct.class, id);
    }

    public void create(PackageProductDTO packageProductDTO) {

        var packageProduct = new PackageProduct(packageProductDTO.getPackageType(),
                packageProductDTO.getPackageMaterial());
        entityManager.persist(packageProduct);
    }

    public List<PackageProduct> getPackagesByType(String type) {
        return entityManager.createNamedQuery("getPackagesByType", PackageProduct.class).setParameter("type", PackageType.valueOf(type)).getResultList();
    }
}
