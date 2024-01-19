package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

@Path("/packages")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PackageService {

    @EJB
    private PackageBean packageBean;

    @EJB
    private SensorBean sensorBean;

    @Context
    private SecurityContext securityContext;

    private PackageDTO toDTO(Package packageInstance) {
        List<SensorDTO> sensors = toDTOsSensors(packageInstance.getSensors());
        return new PackageDTO(
                packageInstance.getId(),
                packageInstance.getPackageType(),
                packageInstance.getPackageMaterial(),
                packageInstance.getOrderId(),
                sensors
        );
    }

    public List<PackageDTO> toDTOs(List<Package> packages) {
        return packages.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private PackageDTO toDTONOSensors(Package packageInstance) {
        return new PackageDTO(
                packageInstance.getId(),
                packageInstance.getPackageType(),
                packageInstance.getPackageMaterial()
        );
    }

    public List<PackageDTO> toDTOsNoSensors(List<Package> packages) {
        return packages.stream().map(this::toDTONOSensors).collect(Collectors.toList());
    }

    private SensorDTO toDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSensorType(),
                sensor.getValue(),
                sensor.getDataType(),
                0
        );
    }

    public List<SensorDTO> toDTOsSensors (List<Sensor> sensors) {
        return sensors.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<PackageDTO> getAllPackages() {
        return toDTOsNoSensors(packageBean.all());
    }

    @GET
    @Path("/packagesWithOrders")
    public List<PackageDTO> getAllPackagesWithOrders() {
        return toDTOs(packageBean.packagesWithOrders());
    }

    @GET
    @Path("{id}")
    public Response getPackageDetails(@PathParam("id") long id) throws Exception {
        return Response.status(Response.Status.OK).entity(toDTO(packageBean.find(id))).build();
    }

    @POST
    @Path("/")
    public Response createNewPackage(PackageDTO packageDTO) throws Exception {
        packageBean.create(
                packageDTO.getPackageType(),
                packageDTO.getPackageMaterial()
        );
        Package package_ = packageBean.find(packageDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(package_)).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePackage(@PathParam("id") long id, PackageDTO packageDTO) throws Exception {
        Package package_ = packageBean.find(id);

        packageBean.update(
                id,
                packageDTO.getPackageType() != null ? packageDTO.getPackageType() : package_.getPackageType(),
                packageDTO.getPackageMaterial() != null ? packageDTO.getPackageMaterial() : package_.getPackageMaterial()
        );
        package_ = packageBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(package_)).build();
    }

    @PUT
    @Path("{id}/add/{value}")
    public Response addValueToPackage(@PathParam("id") long id, @PathParam("value") long valueId) throws Exception {
        Package package_ = packageBean.find(id);

        packageBean.addValueToPackage(
                id,
                valueId
        );
        package_ = packageBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(package_)).build();
    }

    @PUT
    @Path("{id}/remove/{value}")
    public Response removeValueFromPackage(@PathParam("id") long id, @PathParam("value") long valueId) throws Exception {
        Package package_ = packageBean.find(id);

        packageBean.removeValueFromPackage(
                id,
                valueId
        );
        package_ = packageBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(package_)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePackage(@PathParam("id") long id) throws Exception {
        packageBean.removePackage(id);
        return Response.status(Response.Status.OK).build();
    }
}