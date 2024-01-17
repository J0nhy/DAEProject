package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import java.io.Serializable;
import java.util.List;

public class PackageSensorDTO implements Serializable {

    private long id;

    private List<SensorValueDTO> sensorValueDTOS;

    private PackageDTO packageDTO;

    private UnitProductDTO unitProductDTO;

    private String value;

    public PackageSensorDTO() {

    }

    public PackageSensorDTO(Long id, List<SensorValueDTO> sensorValueDTOS, PackageDTO packageDTO) {
        this.id = id;
        this.sensorValueDTOS = sensorValueDTOS;
        this.packageDTO = packageDTO;
    }

    public PackageSensorDTO(Long id, String value, List<SensorValueDTO> sensorValueDTOS) {
        this.id = id;
        this.value =  value;
        this.sensorValueDTOS = sensorValueDTOS;
    }


    // getters and setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public PackageDTO getPackageDTO() {
        return packageDTO;
    }

    public void setPackageDTO(PackageDTO packageDTO) {
        this.packageDTO = packageDTO;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UnitProductDTO getUnitProductDTO() {
        return unitProductDTO;
    }

    public void setUnitProductDTO(UnitProductDTO unitProductDTO) {
        this.unitProductDTO = unitProductDTO;
    }

    public List<SensorValueDTO> getSensorValueDTOS() {
        return sensorValueDTOS;
    }

    public void setSensorValueDTOS(List<SensorDTO> sensorDTOs) {
        this.sensorValueDTOS = sensorValueDTOS;

    }




}
