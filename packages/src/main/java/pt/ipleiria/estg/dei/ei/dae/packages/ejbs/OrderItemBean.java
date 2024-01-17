package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.OrderItem;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

@Stateless
public class OrderItemBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    @EJB
    private UnitProductBean unitProductBean;

    public long create(long order, long unitProduct, int quantity) throws MyEntityNotFoundException {

        if (orderBean.find(order) == null) throw new IllegalArgumentException("Order with id " + order + " not found");
        if (unitProductBean.find(unitProduct) == null) throw new IllegalArgumentException("UnitProduct with id " + unitProduct + " not found");

        try {
            OrderItem orderItem = new OrderItem(
                    unitProductBean.find(unitProduct),
                    quantity,
                    orderBean.find(order)
            );
            entityManager.persist(orderItem);

            if (orderItem == null) throw new IllegalArgumentException("OrderItem with id " + order + " not found");
            return orderItem.getId();
        } catch (Exception e) {
            throw new MyEntityNotFoundException("OrderItem with id " + order + " not found");

        }


    }
}
