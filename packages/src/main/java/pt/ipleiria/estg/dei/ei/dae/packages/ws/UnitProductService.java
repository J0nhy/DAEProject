package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.UnitProductBean;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.util.List;
import java.util.stream.Collectors;

@Path("/unitProducts")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class UnitProductService {

    @EJB
    private UnitProductBean unitProductsBean;

    @GET
    @Path("/")
    public List<UnitProductDTO> getAllUnitProducts() {
        return toDTOs(unitProductsBean.all());
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long unitProductId) {
        UnitProduct unitProduct = unitProductsBean.find(unitProductId);
        if (unitProduct != null) {
            var unitProductDTO = toDTO(unitProduct);
            return Response.ok(unitProductDTO).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_UNIT_PRODUCT")
                .build();
    }

    @GET
    @Path("{id}/sensorsNotAttribute")
    public Response getUnitProductSensorsNotAttribute(@PathParam("id") Long unitProductId) {
        List<Sensor> sensors = unitProductsBean.getAllSensorsNotAttribute(unitProductId);
        if (sensors.size() >= 0) {
            return Response.ok(sensorDTOs(sensors)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_SENSORS")
                .build();
    }

    @PUT
    @Path("{id}/addSensor/{sensorId}")
    public Response addSensorToUnitProduct(@PathParam("id") Long unitProductId, @PathParam("sensorId") Long sensorId) {
        UnitProduct unitProduct = unitProductsBean.addSensorToTheUnitProduct(unitProductId, sensorId);
        if (unitProduct != null ) {
            return Response.ok(unitProduct).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_UNIT_PRODUCT")
                .build();
    }

    private List<SensorDTO> sensorDTOs(List<Sensor> sensors) {
        return sensors.stream().map(this::sensorDTO).collect(Collectors.toList());
    }

    private SensorDTO sensorDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSource(),
                sensor.getType(),
                sensor.getUnit(),
                sensor.getMax(),
                sensor.getMin()
        );
    }

    private List<UnitProductDTO> toDTOs(List<UnitProduct> all) {
        return all.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private UnitProductDTO toDTO(UnitProduct unitProduct) {
        return new UnitProductDTO(
                unitProduct.getId(),
                unitProduct.getSerialNumber(),
                unitProduct.getAvailable(),
                productToDTO(unitProduct.getProduct() == null ? new Product() : unitProduct.getProduct()),
                packageSensorToDTO(unitProduct.getPackageSensor() == null ? null : unitProduct.getPackageSensor())
        );
    }

    private PackageSensorDTO packageSensorToDTO(PackageSensor packageSensor) {
        if (packageSensor != null)
            return new PackageSensorDTO(
                    packageSensor.getId(),
                    sensorValueDTOs(packageSensor.getSensorValues() == null ? null : packageSensor.getSensorValues()),
                    packageDTO(packageSensor.getPackagging() == null ? null : packageSensor.getPackagging()));
        else
            return new PackageSensorDTO();
    }

    private List<SensorValueDTO> sensorValueDTOs(List<SensorValue> sensorValues) {
        return sensorValues.stream().map(this::sensorValueDTO).collect(Collectors.toList());
    }

    private SensorValueDTO sensorValueDTO(SensorValue sensorValue) {
        return new SensorValueDTO(
                sensorValue.getId(),
                SensorDTO.toDTO(sensorValue.getSensor()),
                sensorValue.getValue()
        );
    }

    private PackageDTO packageDTO(Package aPackage) {
        return new PackageDTO(
                aPackage.getId(),
                aPackage.getPackageType(),
                aPackage.getPackageMaterial()
        );
    }


    private ProductDTO productToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername()
        );
    }


}