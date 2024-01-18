package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.jboss.resteasy.spi.touri.MappedBy;

import java.io.Serializable;


@Entity
@Table(name = "sensors")
//@NamedQuery(name = "getAllSensors", query = "select s from Sensor s order by s.id")
@NamedQueries({
        @NamedQuery(
                name = "getAllSensors",
                query = "SELECT s FROM Sensor s ORDER BY s.packageRef.packageMaterial, s.sensorType" //JPQL
        )
})
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


    public Sensor( SensorType sensorType, String value, String dataType, Package packageRef) {
        this.sensorType = sensorType;
        this.value = value;
        this.dataType = dataType;
        this.packageRef = packageRef;
    }

    public Sensor() {
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