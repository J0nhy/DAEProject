package pt.ipleiria.estg.dei.ei.dae.packages.dtos;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Value;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.util.List;
import java.util.stream.Collectors;

public class ValueDTO {
    private Long id;
    private String sensorType;
    private String value;
    private String dataType;
    private int maxValue;
    private int minValue;
    private long timestamp;
    private Package packageRef;
    
    public ValueDTO(Long id, String sensorType, String value, String dataType, int maxValue, int minValue, long timestamp, Package packageRef) {
        this.id = id;
        this.sensorType = sensorType;
        this.value = value;
        this.dataType = dataType;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.timestamp = timestamp;
        this.packageRef = packageRef;
    }

    public ValueDTO() {
    }
    
    public Long getId() {
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

    public void setId(Long id) {
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

    public static ValueDTO from(Value value_) {
        return new ValueDTO(
                value_.getId(),
                value_.getSensorType(),
                value_.getValue(),
                value_.getDataType(),
                value_.getMaxValue(),
                value_.getMinValue(),
                value_.getTimestamp(),
                value_.getPackageRef()
        );
    }

    public static List<ValueDTO> from(List<Value> values) {
        return values.stream().map(ValueDTO::from).collect(Collectors.toList());
    }
}