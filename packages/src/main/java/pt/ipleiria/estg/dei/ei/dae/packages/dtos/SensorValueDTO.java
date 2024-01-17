package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;

import java.io.Serializable;

public class SensorValueDTO implements Serializable {

    private long id;

    private String value;

    private SensorDTO sensorDTO;

    private long packageSensorId;

    public SensorValueDTO() {

    }

    public SensorValueDTO(long id, SensorDTO sensorDTO, String value) {
        this.id = id;
        this.sensorDTO = sensorDTO;
        this.value = value;

    }


    //region getters/setters
    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public SensorDTO getSensorDTO() {
        return sensorDTO;
    }

    public long getPackageSensorId() {
        return packageSensorId;
    }

    //endregion
}
