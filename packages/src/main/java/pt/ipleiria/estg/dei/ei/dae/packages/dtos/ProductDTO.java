package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.Product;

import java.io.Serializable;
import java.util.List;

public class ProductDTO implements Serializable {
    private long id;
    private String name;
    private int stock;
    private String image;
    private String manufacturerUsername;
    private long packageProductId;

    private String packagingMaterial;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, int stock, String image, String manufacturerUsername) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.image = image;
        this.manufacturerUsername = manufacturerUsername;
    }

    public ProductDTO(String name, int stock, String image, String username) {
        this.name = name;
        this.stock = stock;
        this.image = image;
        this.manufacturerUsername = username;
    }

    public ProductDTO(String name, int stock, String image, String username, long packageProductId) {
        this.name = name;
        this.stock = stock;
        this.image = image;
        this.manufacturerUsername = username;
        this.packageProductId = packageProductId;
    }
    public ProductDTO(long id, String name, int stock, String image, String username, long packageProductId) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.image = image;
        this.manufacturerUsername = username;
        this.packageProductId = packageProductId;
    }

    public ProductDTO(long id, String name, int stock, String image, String username, long packageProductId, String packagingMaterial) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.image = image;
        this.manufacturerUsername = username;
        this.packageProductId = packageProductId;
        this.packagingMaterial = packagingMaterial;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getManufacturerUsername() {
        return manufacturerUsername;
    }

    public void setManufacturerUsername(String manufacturerUsername) {
        this.manufacturerUsername = manufacturerUsername;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getPackageProductId() {
        return packageProductId;
    }

    public void setPackageProductId(int packageProductId) {
        this.packageProductId = packageProductId;
    }

    public String getPackagingMaterial() {
        return packagingMaterial;
    }

    public void setPackagingMaterial(String packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
    }

    public static List<ProductDTO> toDTOs(List<Product> all) {
        return all.stream().map(ProductDTO::toDTO).collect(java.util.stream.Collectors.toList());
    }
    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername(),
                product.getUnitProducts().get(0).getPackageSensor() == null ? 0 :
                        product.getUnitProducts().get(0).getPackageSensor().getPackagging().getId(),
                product.getUnitProducts().get(0).getPackageSensor() == null ? null :
                        product.getUnitProducts().get(0).getPackageSensor().getPackagging().getPackageMaterial()
        );
    }

}
