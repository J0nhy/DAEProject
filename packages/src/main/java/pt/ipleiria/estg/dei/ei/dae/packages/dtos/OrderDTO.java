package pt.ipleiria.estg.dei.ei.dae.packages.dtos;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.io.Serializable;
import java.util.List;

public class OrderDTO implements Serializable {
    private Long id;
    private String status;
    private Customer customer;
    private LogisticsOperator logisticsOperators;
    private List<Package> packages;
    private List<Product> products;

    public OrderDTO(Long id, String status, Customer customer, LogisticsOperator logisticsOperators, List<Package> packages, List<Product> products) {
        this.id = id;
        this.status = status;
        this.customer = customer;
        this.logisticsOperators = logisticsOperators;
        this.packages = packages;
        this.products = products;
    }

    public OrderDTO() {
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

    public List<Product> getProducts() {
        return products;
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

    public void setProduct(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", customer=" + customer +
                ", logisticsOperators=" + logisticsOperators +
                '}';
    }
}