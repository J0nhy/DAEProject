package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;


@Entity
@Table(name = "sensors")
@NamedQuery(name = "getAllSensors", query = "SELECT s FROM Sensor s ORDER BY s.id")
@NamedQuery(name = "getSensorById", query = "SELECT s FROM Sensor s WHERE s.id = :id")
@NamedQuery(name = "getSensorsByPackage", query = "SELECT s FROM Sensor s WHERE s.type = :package")
@NamedQuery(name = "getSensorsBySource", query = "SELECT s FROM Sensor s WHERE s.source = :source")
@NamedQuery(name = "getSensorsNotAttribute", query = "SELECT s FROM Sensor s WHERE s.id NOT IN (SELECT s.id FROM Sensor s JOIN s.packagging p WHERE p.id = :productId)")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    private long id;
    @NotNull
    private String source;  // tipo de embalagem (Produto/Encomenda) é preciso?
    @NotNull
    private String type;    // enum / tabela tipo de Sensor (Temperatura, Humidade, Pressão, integridade, localização)
    @NotNull
    private String unit;    // enum / tabela unidade de medida (ºC, %, Pa, m/s, m, etc)
    @NotNull
    private String max;     // valor máximo aceitável
    @NotNull
    private String min;     // valor mínimo aceitável

    @ManyToOne
    @JoinTable(
            name = "sensors_package",
            joinColumns = @JoinColumn(name = "sensor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "package_id", referencedColumnName = "id")
    )
    private Package packagging;

    public Sensor() {
    }

    public Sensor(String source, String type, String unit, String max, String min) {
        this.source = source;
        this.type = type;
        this.unit = unit;
        this.max = max;
        this.min = min;
    }

    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public Package getPackagging() {
        return packagging;
    }

    public void setPackaging(Package packagging) {
        this.packagging = packagging;
    }

}
