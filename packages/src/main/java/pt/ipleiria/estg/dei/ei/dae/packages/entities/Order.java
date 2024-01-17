package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
//@NamedQuery(name = "getAllOrders", query = "SELECT o FROM Orderr o ORDER BY o.id")
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.id"
        ),

        @NamedQuery(
                name = "getOrdersByEndConsumer",
                query = "SELECT o FROM Order o WHERE o.customer.username = :endConsumerUsername ORDER BY o.id"
        ),
        // get orders with orderItems
        @NamedQuery(
                name = "getOrdersWithOrderItems",
                query = "SELECT o FROM Order o JOIN FETCH OrderItem orderItem WHERE o.id = orderItem.order.id ORDER BY o.id"
        )
})
public class Order extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    private Long id;
    @NotNull
    private String status;
    @NotNull
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private LogisticsOperator logisticsOperators;

    @ManyToOne
    private PackageSensor packageSensor;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems;

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    public Order(String status, Customer customer) {
        this.status = status;
        this.customer = customer;
        this.orderItems = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer consumer) {
        this.customer = customer;
    }

    public LogisticsOperator getLogisticsOperators() {
        return logisticsOperators;
    }

    public void setLogisticsOperators(LogisticsOperator logisticsOperators) {
        this.logisticsOperators = logisticsOperators;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    public PackageSensor getPackageSensor() {
        return packageSensor;
    }

    public void setPackageSensor(PackageSensor packageSensor) {
        this.packageSensor = packageSensor;
    }
}
