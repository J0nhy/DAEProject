package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import javax.sound.midi.Sequence;
import java.util.List;

@Entity
@NamedQuery(name = "getAllProducts", query = "SELECT p FROM Product p ORDER BY p.name")
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    // for save a base64 image
    @Column(length=10485760)
    private String image;
    @NotNull
    private int stock;
    @NotNull
    @ManyToOne
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<UnitProduct> unitProducts;

    public Product() {
    }

    public Product(String name, int stock, String image, Manufacturer manufacturer) {
        this.name = name;
        this.stock = stock;
        this.manufacturer = manufacturer;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<UnitProduct> getUnitProducts() {
        return unitProducts;
    }

    public void setUnitProducts(List<UnitProduct> unitProducts) {
        this.unitProducts = unitProducts;
    }


}
