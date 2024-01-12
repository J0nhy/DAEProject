package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.CustomerDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.CustomerBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;

import java.util.List;
import java.util.stream.Collectors;

@Path("/customers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CustomerService extends Customer {
    @EJB
    private CustomerBean customerBean;

    private CustomerDTO toDTO(Customer customer){
        return new CustomerDTO(
                customer.getUsername(),
                customer.getPassword(),
                customer.getEmail(),
                customer.getName(),
                customer.getNif(),
                customer.getPhone(),
                customer.getAddress()
        );
    }

    private List<CustomerDTO> toDTOs(List<Customer> customers){
        return customers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public List<CustomerDTO> getAllCustomers() {
        return toDTOs(customerBean.getAll());
    }

}