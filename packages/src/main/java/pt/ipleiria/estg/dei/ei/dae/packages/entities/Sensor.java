package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.jboss.resteasy.spi.touri.MappedBy;

import java.io.Serializable;


@Entity
@Table(name = "sensors")
@NamedQuery(name = "getAllSensors", query = "select s from Sensor s order by s.sensorType")
public class Sensor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private SensorType sensorType;  // de Temperatura, Humidade, localização ou se ja foi aberto

    @NotNull
    private String value; // valor devolvido pelo sensor

    @NotNull
    private String dataType;  // unidade em que é medido (ºC, %, cord., true/false)

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package packageRef; // embalagem a que se refere esta leitura


    public Sensor( SensorType sensorType, String value, String dataType) {
        this.sensorType = sensorType;
        this.value = value;
        this.dataType = dataType;
    }

    public Sensor() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public String getValue() {
        return value;
    }

    public String getDataType() {
        return dataType;
    }

    public Package getPackageRef() {
        return packageRef;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setPackageRef(Package packageRef) {
        this.packageRef = packageRef;
    }

    public long getPackageId() {
        return packageRef.getId();
    }

    @Override
    public String toString() {
        return "Values{" +
                "id=" + id +
                ", sensorType='" + sensorType + '\'' +
                ", value='" + value + '\'' +
                ", dataType='" + dataType + '\'' +
                ", packageRef=" + packageRef +
                '}';
    }

}