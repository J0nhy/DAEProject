package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.CustomerDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.LogisticsOperatorDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.ManufacturerDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.CustomerBean;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.LogisticsOperatorBean;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.ManufacturerBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.LogisticsOperator;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.*;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;


@Path("/logisticsoperators")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LogisticsOperatorService {
    @EJB
    private LogisticsOperatorBean logisticsOperatorBean;

    @Context
    private SecurityContext securityContext;

    private LogisticsOperatorDTO toDTO(LogisticsOperator logisticsOperator){
        return new LogisticsOperatorDTO(
                logisticsOperator.getUsername(),
                logisticsOperator.getPassword(),
                logisticsOperator.getEmail(),
                logisticsOperator.getName(),
                logisticsOperator.getCompany()
        );
    }

    private List<LogisticsOperatorDTO> toDTOs(List<LogisticsOperator> logisticsOperators){
        return logisticsOperators.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @Authenticated
    @RolesAllowed({"Manufacturer"})
    public List<LogisticsOperatorDTO> getAllLogisticsOperators() throws MyEntityNotFoundException, MyQueryException {
        return toDTOs(logisticsOperatorBean.getAll());
    }

    @GET
    @Path("{username}")
    @Authenticated
    @RolesAllowed({"Manufacturer", "LogisticsOperator"})
    public Response getLogisticsOperatorDetails(@PathParam("username") String username) throws MyEntityNotFoundException, MyQueryException {
        /*var principal = securityContext.getUserPrincipal();

        if(!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        */
        return Response.status(Response.Status.OK).entity(toDTO(logisticsOperatorBean.findLogisticOperator(username))).build();
    }

    @POST
    @Path("/")
    public Response createNewLogisticsOperator(LogisticsOperatorDTO logisticsOperatorDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException, MyIncorrectDataType, MyQueryException {
            logisticsOperatorBean.create(
                    logisticsOperatorDTO.getUsername(),
                    logisticsOperatorDTO.getPassword(),
                    logisticsOperatorDTO.getName(),
                    logisticsOperatorDTO.getEmail(),
                    logisticsOperatorDTO.getCompany()
            );
            LogisticsOperator logistics = logisticsOperatorBean.findLogisticOperator(logisticsOperatorDTO.getUsername());
            return Response.status(Response.Status.CREATED).entity(toDTO(logistics)).build();
    }

    @PUT
    @Path("{username}")
    @Authenticated
    @RolesAllowed({"LogisticsOperator"})
    public Response updateLogisticsOperator(@PathParam("username") String username, LogisticsOperatorDTO logisticsOperatorDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyIncorrectDataType, MyQueryException {
        LogisticsOperator logistics = logisticsOperatorBean.findLogisticOperator(username);

        logistics = logisticsOperatorBean.update(
                username,
                logisticsOperatorDTO.getPassword() != null ? logisticsOperatorDTO.getPassword() : logistics.getPassword(),
                logisticsOperatorDTO.getName() != null ? logisticsOperatorDTO.getName() : logistics.getName(),
                logisticsOperatorDTO.getEmail() != null ? logisticsOperatorDTO.getEmail() : logistics.getEmail(),
                logisticsOperatorDTO.getCompany() != null ? logisticsOperatorDTO.getCompany() : logistics.getCompany()
        );
        return Response.status(Response.Status.OK).entity(toDTO(logistics)).build();
    }

    @DELETE
    @Path("{username}")
    @Authenticated
    @RolesAllowed({"LogisticsOperator", "Manufacturer"})
    public Response deleteLogisticsOperator(@PathParam("username") String username) throws MyEntityNotFoundException, MyQueryException {
        logisticsOperatorBean.removeLogisticsOperator(username);
        return Response.status(Response.Status.OK).build();
    }




}