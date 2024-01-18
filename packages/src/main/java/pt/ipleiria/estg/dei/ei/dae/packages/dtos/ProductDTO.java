package pt.ipleiria.estg.dei.ei.dae.packages.dtos;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.SensorType;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private long id;
    private String productName; // name of the product
    private String productDescription; // description of the product
    private String productCategory; // category of the product
    private String productManufacturer; // manufacturer of the product
    private String productBrand; // brand of the product
    private String productImage; // image of the product
    private String productPrice; // price of the product
    private String productWeight; // shipping weight of the product


    public ProductDTO(long id, String productName, String productDescription, String productCategory, String productManufacturer, String productBrand, String productImage, String productPrice, String productWeight) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productManufacturer = productManufacturer;
        this.productBrand = productBrand;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productWeight = productWeight;
    }

    public ProductDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductManufacturer() {
        return productManufacturer;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductManufacturer(String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    @Override
public String toString() {
        return "ProductDTO{" +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productManufacturer='" + productManufacturer + '\'' +
                ", productBrand='" + productBrand + '\'' +
                ", productImage='" + productImage + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productWeight='" + productWeight + '\'' +
                '}';
    }
}