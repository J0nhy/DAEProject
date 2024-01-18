package pt.ipleiria.estg.dei.ei.dae.packages.dtos;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO implements Serializable {
    private long id;
    private String status;
    private String customerUsername;
    private LogisticsOperator logisticsOperators;
    private List<PackageDTO> packages;
    private List<ProductDTO> products;

    public OrderDTO(long id, String status, LogisticsOperator logisticsOperators, List<PackageDTO> packages, List<ProductDTO> products, String customerUsername) {
        this.id = id;
        this.status = status;
        this.logisticsOperators = logisticsOperators;
        this.packages = packages;
        this.products = products;
        this.customerUsername = customerUsername;
    }

    public OrderDTO() {
        this.packages = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public LogisticsOperator getLogisticsOperators() {
        return logisticsOperators;
    }

    public List<PackageDTO> getPackages() {
        return packages;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLogisticsOperators(LogisticsOperator logisticsOperators) {
        this.logisticsOperators = logisticsOperators;
    }

    public void setPackages(List<PackageDTO> packages) {
        this.packages = packages;
    }

    public void setProduct(List<ProductDTO> products) {
        this.products = products;
    }

}