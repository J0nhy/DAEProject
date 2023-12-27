package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "orders")
@NamedQuery(name = "getAllOrders", query = "select o from Order o order by o.id")
public class Order {

    @Id
    private Long id;

    @NotNull
    private String status;
    
    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @ManyToOne
    private LogisticsOperator logisticsOperators;

    @NotNull
    @OneToMany // uma encomenda pode ter várias embalagens
    private List<Package> packages;

    @NotNull
    @OneToMany // uma encomenda pode ter vários produtos
    private List<Product> products;

    public Order(long id, String status, Customer customer, LogisticsOperator logisticsOperators, List<Package> packages, List<Product> products) {
        this.id = id;
        this.status = status;
        this.customer = customer;
        this.logisticsOperators = logisticsOperators;
        this.packages = packages;
        this.products = products;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LogisticsOperator getLogisticsOperators() {
        return logisticsOperators;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setId(Long id) {
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

}
