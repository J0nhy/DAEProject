package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.PackageProductDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.PackageProductBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageProduct;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("/packageProducts")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PackageProductService {

    @EJB
    private PackageProductBean packageProductBean;

    @GET
    @Path("/")
    public List<PackageProductDTO> getAllPackages() {
        return new PackageProductDTO().toDTOs(packageProductBean.all());
    }


    @GET
    @Path("/{id}")
    public Response getPackageById(@PathParam("id") long id) throws MyEntityNotFoundException {
        PackageProduct packageProduct = packageProductBean.find(id);
        if (packageProduct != null) {
            return Response.ok(new PackageProductDTO().toDTO(packageProduct)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PACKAGE")
                .build();
    }

    // FILTER BY PACKAGE TYPE
    @GET
    @Path("/type/{type}")
    public List<PackageProductDTO> getPackagesByType(@PathParam("type") String type) {
        return new PackageProductDTO().toDTOs(packageProductBean.getPackagesByType(type));
    }
    @POST
    @Path("/")
    public Response createNewPackage(PackageProductDTO packageProductDTO) {
        packageProductBean.create(packageProductDTO);
        return Response.ok().build();
    }




}
