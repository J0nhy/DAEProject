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
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyQueryException;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Authenticated;

@Path("/packages")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
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
                packageInstance.getPackageMaterial(),
                packageInstance.getOrderId()
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
    @RolesAllowed({"Manufacturer"})
    public List<PackageDTO> getAllPackages() throws MyEntityNotFoundException, MyQueryException {
        return toDTOsNoSensors(packageBean.all());
    }

    @GET
    @Path("/packagesWithOrders")
    @RolesAllowed({"Manufacturer"})
    public List<PackageDTO> getAllPackagesWithOrders() throws MyEntityNotFoundException, MyQueryException {
        return toDTOsNoSensors(packageBean.packagesWithOrders());
    }

    @GET
    @Path("/packagesWithoutOrders")
    @RolesAllowed({"Manufacturer"})
    public List<PackageDTO> getAllPackagesWithoutOrders() throws MyEntityNotFoundException, MyQueryException {
        return toDTOsNoSensors(packageBean.packagesWithoutOrders());
    }

    @GET
    @Path("{id}")
    public Response getPackageDetails(@PathParam("id") long id) throws Exception {
        return Response.status(Response.Status.OK).entity(toDTO(packageBean.find(id))).build();
    }

    /*
    Não se cria packages através front end
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

    Não alteramos os pacotes associados a uma encomenda
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


    //Não eliminamos pacotes
    @DELETE
    @Path("{id}")
    public Response deletePackage(@PathParam("id") long id) throws Exception {
        packageBean.removePackage(id);
        return Response.status(Response.Status.OK).build();
    }
    */

}