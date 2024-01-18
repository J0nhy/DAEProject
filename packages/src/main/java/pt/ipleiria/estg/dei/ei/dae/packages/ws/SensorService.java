package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.ejb.EJB;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SensorService {

    @EJB
    private SensorBean sensorBean;

    @EJB
    private PackageBean packageBean;

    @Context
    private SecurityContext securityContext;

    private SensorDTO toDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSensorType(),
                sensor.getValue(),
                sensor.getDataType(),
                sensor.getPackageRef()
        );
    }

    public List<SensorDTO> toDTOs(List<Sensor> sensors) {
        return sensors.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<SensorDTO> getAllSensors() {
        return toDTOs(sensorBean.all());
    }

    @GET
    @Path("{id}")
    public Response getSensorDetails(@PathParam("id") long id) throws Exception {
        return Response.status(Response.Status.OK).entity(toDTO(sensorBean.find(id))).build();
    }

    @POST
    @Path("/")
    public Response createNewSensor(SensorDTO sensorDTO) throws Exception {
        sensorBean.create(
                sensorDTO.getSensorType(),
                sensorDTO.getValue(),
                sensorDTO.getDataType(),
                sensorDTO.getPackageRef()
        );
        Sensor sensor = sensorBean.find(sensorDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(sensor)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateSensor(@PathParam("id") long id, SensorDTO sensorDTO) throws Exception {
        Sensor sensor = sensorBean.find(id);

        sensorBean.update(
                id,
                sensorDTO.getSensorType() != null ? sensorDTO.getSensorType() : sensor.getSensorType(),
                sensorDTO.getValue() != null ? sensorDTO.getValue() : sensor.getValue(),
                sensorDTO.getDataType() != null ? sensorDTO.getDataType() : sensor.getDataType(),
                sensorDTO.getPackageRef() != null ? sensorDTO.getPackageRef() : sensor.getPackageRef()
        );
        sensor = sensorBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(sensor)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") long id) throws Exception {
        sensorBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }
}