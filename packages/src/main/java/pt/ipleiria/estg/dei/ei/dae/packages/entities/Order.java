package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.internal.util.logging.Log;

@Entity
@Table(name = "orders")
@NamedQuery(name = "getAllOrders", query = "select o from Order o order by o.id")
@NamedQuery(name = "getAllOrdersByCustomer", query = "select o from Order o WHERE o.customer = :customer order by o.id"
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String status;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String customerName;

    @ManyToOne
    @JoinColumn(name = "logisticsOperator_id")
    private LogisticsOperator logisticsOperators;

    @OneToMany // uma encomenda pode ter várias embalagens
    private List<Package> packages;

    @OneToMany // uma encomenda pode ter vários produtos
    private List<Product> products;



    public Order(String status, Customer customer, LogisticsOperator logisticsOperator) {
        this.status = status;
        this.customer = customer;
        this.logisticsOperators = logisticsOperator;
        this.packages = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public Order() {
        this.packages = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCustomerName(){
        return customer.getName();
    }

    public Long getPackageId(){
        return 1L;
    }

    public LogisticsOperator getLogisticsOperators() {
        return logisticsOperators;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setLogisticsOperators(LogisticsOperator logisticsOperators) {
        this.logisticsOperators = logisticsOperators;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProduct(List<Product> products) {
        this.products = products;
    }

    public void addPackage(Package p) {
        if (this.packages == null) {
            this.packages = new ArrayList<>();
        }
        this.packages.add(p);
    }

    public void addProduct(Product p) {
        if (this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.add(p);
    }

    public void removePackage(Package p) {
        if (this.packages == null) {
            this.packages = new ArrayList<>();
        }
        this.packages.remove(p);
    }

    public void removeProduct(Product p) {
        if (this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.remove(p);
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
