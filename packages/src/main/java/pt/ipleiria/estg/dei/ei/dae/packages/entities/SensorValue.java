package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "sensor_values")
public class SensorValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    long id;

    private String value;

    @ManyToOne
    private Sensor sensor;

    @ManyToOne
    private  PackageSensor packageSensor;

    public SensorValue(){

    }

    public SensorValue(Sensor sensor, PackageSensor packageSensor ){
        this.sensor = sensor;
        this.packageSensor =packageSensor;
    }

    public long getId() {
        return id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setPackageSensor(PackageSensor packageSensor){
        this.packageSensor = packageSensor;
    }

    public String getValue() {
        return value;
    }
}
