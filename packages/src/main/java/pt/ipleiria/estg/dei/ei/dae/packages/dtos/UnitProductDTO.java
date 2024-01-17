package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageSensor;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.UnitProduct;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class UnitProductDTO implements Serializable {
    long id;

    private UUID serialNumber;

    private ProductDTO product;

    private boolean available;

    private PackageSensorDTO packageSensorDTO;

    public UnitProductDTO() {

    }

    public UnitProductDTO(long id, UUID productDTO, boolean available, ProductDTO dto, PackageSensorDTO packageSensorDTO) {
        this.id = id;
        this.serialNumber = productDTO;
        this.available = available;
        this.product = dto;
        this.packageSensorDTO = packageSensorDTO;
    }

    public UnitProductDTO(long id, UUID productDTO, boolean available, ProductDTO dto) {
        this.id = id;
        this.serialNumber = productDTO;
        this.available = available;
        this.product = dto;
        this.packageSensorDTO = null;
    }

    public UnitProductDTO(long id, UUID serialNumber, boolean available, PackageSensorDTO packageSensorDTO ) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.available = available;
        this.packageSensorDTO = packageSensorDTO;
        this.product = null;
    }

    public UnitProductDTO(long id, UUID serialNumber, boolean available) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.available = available;
        this.product = null;
        this.packageSensorDTO = null;
    }

    public static List<UnitProduct> unitProducts(List<UnitProduct> unitProducts) {
        return unitProducts;
    }

    // getters and setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public UUID getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(UUID serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ProductDTO getProductDTO() {
        return product;
    }
    public void setProductDTO(ProductDTO productDTO) {
        this.product = productDTO;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public PackageSensorDTO getPackageSensorDTO() {
        return packageSensorDTO;
    }

    public void setPackageSensorDTO(PackageSensorDTO packageSensorDTO) {
        this.packageSensorDTO = packageSensorDTO;
    }

}
