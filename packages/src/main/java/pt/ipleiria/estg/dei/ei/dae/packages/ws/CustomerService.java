package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.CustomerDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.CustomerBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/customers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({}) //TODO: add roles, em principio só os proprios clientes podem aceder aos seus dados
public class CustomerService {
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


    @GET
    @Path("{username}")
    public Response getCustomerDetails(@PathParam("username") String username) throws MyEntityNotFoundException{
        return Response.status(Response.Status.OK).entity(toDTO(customerBean.findCustomer(username))).build();
    }

    @POST
    @Path("/")
    public Response createNewCustomer(CustomerDTO customerDTO) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        customerBean.create(customerDTO.getUsername(),
                customerDTO.getPassword(),
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getNif(),
                customerDTO.getPhone(),
                customerDTO.getAddress()
        );
        Customer customer = customerBean.findCustomer(customerDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(customer)).build();
    }

    @PUT
    @Path("{username}")
    public Response updateCustomer(@PathParam("username") String username, CustomerDTO customerDTO)
        throws MyEntityNotFoundException, MyConstraintViolationException{
        Customer customer = customerBean.findCustomer(username);

        customer = customerBean.update(
                username,
                customerDTO.getPassword() != null ? customerDTO.getPassword() : customer.getPassword(),
                customerDTO.getName() != null ? customerDTO.getName() : customer.getName(),
                customerDTO.getEmail() != null ? customerDTO.getEmail() : customer.getEmail(),
                customerDTO.getNif() != 0 ? customerDTO.getNif() : customer.getNif(),
                customerDTO.getPhone() != 0 ? customerDTO.getPhone() : customer.getPhone(),
                customerDTO.getAddress() != null ? customerDTO.getAddress() : customer.getAddress()
        );
        return Response.status(Response.Status.OK).entity(toDTO(customer)).build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteCustomer(@PathParam("username") String username)throws MyEntityNotFoundException{
        customerBean.removeStudent(username);
        return Response.status(Response.Status.OK).build();
    }

}