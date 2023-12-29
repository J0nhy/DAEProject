package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "values")
@NamedQuery(name = "getAllValues", query = "select v from Value v order by v.id")
public class Value {

    @Id
    private long id;


    
    @NotNull
    private String sensorType;  // de Temperatura, Humidade, localização ou se ja foi aberto
  
    @NotNull
    private String value; // valor devolvido pelo sensor

    @NotNull
    private String dataType;  // unidade em que é medido (ºC, %, cord., true/false)

    private int maxValue;     // valor máximo 
    
    private int minValue;     // valor mínimo

    @NotNull
    private long timestamp; // data e hora em que foi lido

    //@OneToOne
    @NotNull
    private Package packageRef; // embalagem a que se refere esta leitura


    public Value(long id, String sensorType, String value, String dataType, int maxValue, int minValue, long timestamp, Package packageRef) {
        this.id = id;
        this.sensorType = sensorType;
        this.value = value;
        this.dataType = dataType;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.timestamp = timestamp;
        this.packageRef = packageRef;
    }

    public Value() {
    }

    public long getId() {
        return id;
    }

    public String getSensorType() {
        return sensorType;
    }

    public String getValue() {
        return value;
    }

    public String getDataType() {
        return dataType;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Package getPackageRef() {
        return packageRef;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", timestamp=" + timestamp +
                ", packageRef=" + packageRef +
                '}';
    }


}