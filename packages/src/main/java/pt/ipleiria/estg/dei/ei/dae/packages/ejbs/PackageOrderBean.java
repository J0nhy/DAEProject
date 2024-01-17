package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.PackageOrderDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageOrder;

import java.util.List;

@Stateless
public class PackageOrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PackageOrder> all() {
        Query query = entityManager.createNamedQuery("getAllPackageOrders");
        return query.getResultList();
    }

    public long create(PackageOrderDTO packageOrderDTO) {
        PackageOrder packageOrder =
                new PackageOrder(packageOrderDTO.getPackageType(), packageOrderDTO.getPackageMaterial());
        entityManager.persist(packageOrder);

        return packageOrder.getId();
    }

    public PackageOrder find(long id) {
        return entityManager.find(PackageOrder.class, id);
    }

    public PackageOrder update(long id, PackageOrderDTO packageOrderDTO) {
        PackageOrder packageOrder = find(id);
        if (packageOrder == null) throw new IllegalArgumentException("There is no package with that id");

        if (packageOrder != null) {
            packageOrder.setPackageType(packageOrderDTO.getPackageType());
            packageOrder.setPackageMaterial(packageOrderDTO.getPackageMaterial());
            entityManager.merge(packageOrder);
        }
        return packageOrder;
    }

}
