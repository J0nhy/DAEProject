package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.CustomerDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.CustomerBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Customer;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyIncorrectDataType;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/customers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class CustomerService {
    @EJB
    private CustomerBean customerBean;

    @Context
    private SecurityContext securityContext;

    private CustomerDTO toDTO(Customer customer){
        List<OrderDTO> orders = toDTOsOrder(customer.getOrders());
        return new CustomerDTO(
                customer.getUsername(),
                customer.getPassword(),
                customer.getEmail(),
                customer.getName(),
                customer.getNif(),
                customer.getPhone(),
                customer.getAddress(),
                orders

        );
    }

    private List<CustomerDTO> toDTOs(List<Customer> customers){
        return customers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CustomerDTO toDTOWithoutOrders(Customer customer){
        return new CustomerDTO(
                customer.getUsername(),
                customer.getPassword(),
                customer.getEmail(),
                customer.getName(),
                customer.getNif(),
                customer.getPhone(),
                customer.getAddress(),
                null

        );
    }

    private List<CustomerDTO> toDTOsWithoutOrders(List<Customer> customers){
        return customers.stream().map(this::toDTOWithoutOrders).collect(Collectors.toList());
    }

    private OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                null,
                null,
                null,
                null
        );

    }
    public List<OrderDTO> toDTOsOrder(List<Order> orders) {
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    @RolesAllowed({"Manufacturer"})
    public List<CustomerDTO> getAllCustomers() {
        return toDTOsWithoutOrders(customerBean.getAll());
    }


    @GET
    @Path("{username}")
    @RolesAllowed({"Customer"})
    public Response getCustomerDetails(@PathParam("username") String username) throws MyEntityNotFoundException{
        return Response.status(Response.Status.OK).entity(toDTO(customerBean.findCustomer(username))).build();
    }

    /*
    @POST
    @Path("/")
    public Response createNewCustomer(CustomerDTO customerDTO) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException, MyIncorrectDataType {
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
    */


    @PUT
    @Path("{username}")
    @RolesAllowed({"Customer"})
    public Response updateCustomer(@PathParam("username") String username, CustomerDTO customerDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyIncorrectDataType {
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
    @RolesAllowed({"Customer", "Manufacturer"})
    public Response deleteCustomer(@PathParam("username") String username) throws MyEntityNotFoundException{
        customerBean.delete(username);
        return Response.status(Response.Status.OK).build();
    }

}