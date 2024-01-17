package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


public class SensorDTO implements Serializable {

    private long id;

    private String source;

    private String type;

    private String value;

    private String unit;

    private String max;

    private String min;

    public SensorDTO() {
    }

    public SensorDTO(long id, String type, String source) {
        this.id = id;
        this.type = type;
        this.source = source;
    }

    public SensorDTO(long id, String source, String type,  String unit, String max, String min) {
        this.id = id;
        this.source = source;
        this.type = type;
        this.unit = unit;
        this.max = max;
        this.min = min;
    }

    public SensorDTO(Long id, String source, String type, String unit, String max, String min) {
        this.id = id;
        this.source = source;
        this.type = type;
        this.unit = unit;
        this.max = max;
        this.min = min;
    }
    public SensorDTO(String source, String type, String unit, String max, String min) {
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

    public static  List<SensorDTO> toDTOs(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::toDTO).collect(java.util.stream.Collectors.toList());
    }
    public static SensorDTO toDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSource(),
                sensor.getType(),
                sensor.getUnit(),
                sensor.getMax(),
                sensor.getMin()
        );
    }
}
