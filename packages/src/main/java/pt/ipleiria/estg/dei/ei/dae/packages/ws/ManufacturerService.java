package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.CustomerDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.ManufacturerDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.CustomerBean;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.ManufacturerBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/manufacturers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
//@RolesAllowed({}) //TODO: add roles, em principio só os proprios clientes podem aceder aos seus dados
public class ManufacturerService {
    @EJB
    private ManufacturerBean manufacturerBean;

    @Context
    private SecurityContext securityContext;

    private ManufacturerDTO toDTO(Manufacturer manufacturer){
        return new ManufacturerDTO(
                manufacturer.getUsername(),
                manufacturer.getPassword(),
                manufacturer.getEmail(),
                manufacturer.getName(),
                manufacturer.getNif(),
                manufacturer.getPhone(),
                manufacturer.getAddress()
        );
    }

    private List<ManufacturerDTO> toDTOs(List<Manufacturer> manufacturers){
        return manufacturers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public List<ManufacturerDTO> getAllManufacturers() {
        return toDTOs(manufacturerBean.getAll());
    }


    @GET
    @Path("{username}")
    public Response getManufacturerDetails(@PathParam("username") String username) throws MyEntityNotFoundException{
        var principal = securityContext.getUserPrincipal();

        if(!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.status(Response.Status.OK).entity(toDTO(manufacturerBean.findManufacturer(username))).build();
    }

    @POST
    @Path("/")
    public Response createNewManufacturer(ManufacturerDTO manufacturerDTO) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        manufacturerBean.create(manufacturerDTO.getUsername(),
                manufacturerDTO.getPassword(),
                manufacturerDTO.getName(),
                manufacturerDTO.getEmail(),
                manufacturerDTO.getNif(),
                manufacturerDTO.getPhone(),
                manufacturerDTO.getAddress()
        );
        Manufacturer manufacturer = manufacturerBean.findManufacturer(manufacturerDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(manufacturer)).build();
    }

    @PUT
    @Path("{username}")
    public Response updateManufacturer(@PathParam("username") String username, ManufacturerDTO manufacturerDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException{
        Manufacturer manufacturer = manufacturerBean.findManufacturer(username);

        manufacturer = manufacturerBean.update(
                username,
                manufacturerDTO.getPassword() != null ? manufacturerDTO.getPassword() : manufacturer.getPassword(),
                manufacturerDTO.getName() != null ? manufacturerDTO.getName() : manufacturer.getName(),
                manufacturerDTO.getEmail() != null ? manufacturerDTO.getEmail() : manufacturer.getEmail(),
                manufacturerDTO.getNif() != 0 ? manufacturerDTO.getNif() : manufacturer.getNif(),
                manufacturerDTO.getPhone() != 0 ? manufacturerDTO.getPhone() : manufacturer.getPhone(),
                manufacturerDTO.getAddress() != null ? manufacturerDTO.getAddress() : manufacturer.getAddress()
        );
        return Response.status(Response.Status.OK).entity(toDTO(manufacturer)).build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteManufacturer(@PathParam("username") String username)throws MyEntityNotFoundException{
        manufacturerBean.removeManufacturer(username);
        return Response.status(Response.Status.OK).build();
    }

}