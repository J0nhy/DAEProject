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
    private int maxValue;
    private int minValue;
    private long timestamp;
    private Package packageRef;
    
    public SensorDTO( SensorType sensorType, String value, String dataType, int maxValue, int minValue, long timestamp, Package packageRef) {
        this.sensorType = sensorType;
        this.value = value;
        this.dataType = dataType;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.timestamp = timestamp;
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

    public void setSensorType(SensorType sensorType) {
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

}