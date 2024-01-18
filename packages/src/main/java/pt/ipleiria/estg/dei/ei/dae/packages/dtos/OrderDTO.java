package pt.ipleiria.estg.dei.ei.dae.packages.dtos;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO implements Serializable {
    private long id;
    private String status;
    private Customer customer;

    private String customerName;
    private LogisticsOperator logisticsOperators;
    private List<Package> packages;
    private List<Product> products;

    private Long packageID;

    public OrderDTO(String status, String customer, LogisticsOperator logisticsOperators, Long packageId) {
        this.status = status;
        this.customerName = customer;
        this.logisticsOperators = logisticsOperators;
        this.packageID = packageId;
    }

    public OrderDTO(String status, String customer, LogisticsOperator logisticsOperators) {
        this.status = status;
        this.customerName = customer;
        this.logisticsOperators = logisticsOperators;
    }

    public OrderDTO() {
        this.packages = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
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

    public void setProduct(List<Product> products) {
        this.products = products;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /*
    @Override
    public String toString() {
        return "OrderDTO{" +
                ", status='" + status + '\'' +
                ", customer=" + customer +
                ", logisticsOperators=" + logisticsOperators +
                '}';
    }*/
}