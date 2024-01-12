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

    @GET
    @Path("/")
    public List<PackageDTO> getAllProducts() {
        // Get the user's role from the security context
        String userRole = getUserRole();

        // Getting the appropriate list of packages based on the role
        List<Package> packages = packageBean.getAllPackagesByRole(userRole);
        return PackageDTO.from(packages);
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long packageId) {
        Package package_ = packageBean.find(packageId);
        if (package_ != null) {
            return Response.ok(PackageDTO.from(package_)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PACKAGE")
                .build();
    }

    @GET
    @Path("{id}/sensors")
    public Response getPackageSensors(@PathParam("id") Long packageId) throws MyEntityNotFoundException {
        Package package_ = packageBean.findOrFail(packageId);
        var sensors = package_.getSensors();

        if (sensors == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PACKAGE_SENSORS").build();
        }

        Hibernate.initialize(sensors);
        return Response.ok(SensorDTO.from(sensors)).build();
    }

    @POST
    @Path("/")
    //@Authenticated
    //@RolesAllowed({"Admin"})
    public Response createNewPackage(PackageDTO packageDTO) throws MyConstraintViolationException {
        packageBean.create(
                packageDTO.getId(),
                packageDTO.getPackagingType(),
                packageDTO.getPackagingMaterial()
        );
        Package newPackage = packageBean.find(packageDTO.getId());
        return Response.status(Response.Status.CREATED).entity(PackageDTO.from(newPackage)).build();
    }

    @PUT
    @Path("{id}")
    //@Authenticated
    //@RolesAllowed({"Admin"})
    public Response updatePackage(@PathParam("id") Long id, PackageDTO packageDTO)
            throws MyEntityNotFoundException {
        packageBean.update(
                packageDTO.getId(),
                packageDTO.getPackagingType(),
                packageDTO.getPackagingMaterial()
        );
        Package package_ = packageBean.find(id);
        return Response.status(Response.Status.OK).entity(PackageDTO.from(package_)).build();
    }

    // Add and Remove a Sensor of a package
    @POST
    @Authenticated
    @RolesAllowed({"Manufacturer", "LogisticsOperator"})
    @Path("{id}/sensor/{sensorId}")
    public Response addSensorToPackage(@PathParam("id") Long id, @PathParam("sensorId") Long sensorId)
            throws MyEntityNotFoundException {

        if (!securityContext.isUserInRole("Manufacturer") || !securityContext.isUserInRole("LogisticsOperator")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        packageBean.addSensorToPackage(id, sensorId);
        return Response.ok().build();
    }

    @DELETE
    @Authenticated
    @RolesAllowed({"Manufacturer", "LogisticsOperator"})
    @Path("{id}/sensor/{sensorId}")
    public Response deleteSensorOfPackage(@PathParam("id") Long id, @PathParam("sensorId") Long sensorId)
            throws MyEntityNotFoundException {

        if (!securityContext.isUserInRole("Manufacturer") || !securityContext.isUserInRole("LogisticsOperator")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        packageBean.removeSensorFromPackage(id, sensorId);
        return Response.ok().build();
    }
    // ------------

    @DELETE
    @Path("{id}")
    //@Authenticated
    //@RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long packagingId) throws MyEntityNotFoundException {
        packageBean.remove(packagingId);
        return Response.ok().build();
    }

    // Helper method to get the user's role from the security context
    private String getUserRole() {
        return securityContext.isUserInRole("LogisticsOperator") ? "LogisticsOperator" :
                securityContext.isUserInRole("Manufacturer") ? "Manufacturer" : "UnknownRole";
    }
}