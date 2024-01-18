package pt.ipleiria.estg.dei.ei.dae.packages.dtos;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.SensorType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO implements Serializable {
    private long id;
    private SensorType sensorType;
    private String value;
    private String dataType;
    private Package packageRef;
    
    public SensorDTO(long id,  SensorType sensorType, String value, String dataType, Package packageRef) {
        this.id = id;
        this.sensorType = sensorType;
        this.value = value;
        this.dataType = dataType;
        this.packageRef = packageRef;
    }

    public SensorDTO() {
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

}