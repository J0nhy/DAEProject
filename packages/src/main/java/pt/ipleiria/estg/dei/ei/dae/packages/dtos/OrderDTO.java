package pt.ipleiria.estg.dei.ei.dae.packages.dtos;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO implements Serializable {
    private long id;
    private StatusMessage status;
    private String customerUsername;
    private String logisticsOperatorsUsername;
    private List<PackageDTO> packages;
    private List<ProductDTO> products;

    public OrderDTO(long id, StatusMessage status, String logisticsOperatorsUsername, List<PackageDTO> packages, List<ProductDTO> products, String customerUsername) {
        this.id = id;
        this.status = status;
        this.logisticsOperatorsUsername = logisticsOperatorsUsername;
        this.packages = packages;
        this.products = products;
        this.customerUsername = customerUsername;
    }

    public OrderDTO(long id, StatusMessage status, List<PackageDTO> packages, List<ProductDTO> products) { //construtor sem logistics operator
        this.id = id;
        this.status = status;
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

    public StatusMessage getStatus() {
        return status;
    }

    public String getLogisticsOperatorsUsername() {
        return logisticsOperatorsUsername;
    }

    public List<PackageDTO> getPackages() {
        return packages;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setStatus(StatusMessage status) {
        this.status = status;
    }

    public void setLogisticsOperatorsUsername(String logisticsOperators) {
        this.logisticsOperatorsUsername = logisticsOperators;
    }

    public void setPackages(List<PackageDTO> packages) {
        this.packages = packages;
    }

    public void setProduct(List<ProductDTO> products) {
        this.products = products;
    }

}