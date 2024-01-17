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
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
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
    public List<LogisticsOperatorDTO> getAllLogisticsOperators() {
        return toDTOs(logisticsOperatorBean.getAll());
    }
    



}