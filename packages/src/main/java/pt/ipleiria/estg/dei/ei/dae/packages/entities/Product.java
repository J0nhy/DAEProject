package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Entity
@Table(name = "products")
@NamedQuery(name = "getAllProducts", query = "select p from Product p order by p.productName")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // product id

    @NotNull
    private String productName; // name of the product

    private String productDescription; // description of the product

    @NotNull
    private String productCategory; // category of the product

    private String productManufacturer; // manufacturer of the product

    @NotNull
    private String productBrand; // brand of the product

    private String productImage; // image of the product

    @NotNull
    private double productPrice; // price of the product

    private double productWeight; // shipping weight of the product


    public Product() {
    }

    public Product( String productName, String productDescription, String productCategory, String productManufacturer, String productBrand, String productImage, double productPrice, double productWeight) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productManufacturer = productManufacturer;
        this.productBrand = productBrand;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productWeight = productWeight;
    }

    public long getId() {
        return id;
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

    public double getProductPrice() {
        return productPrice;
    }

    public double getProductWeight() {
        return productWeight;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductWeight(double productWeight) {
        this.productWeight = productWeight;
    }

}
