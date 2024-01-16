package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.ejb.EJB;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @Context
    private SecurityContext securityContext;

    private OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getCustomer(),
                order.getLogisticsOperators(),
                order.getPackages(),
                order.getProducts()
        );
    }

    public List<OrderDTO> toDTOs(List<Order> orders) {
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<OrderDTO> getAllOrders() {
        return toDTOs(orderBean.all());
    }
    @GET
    @Path("/customer/{customer}")
    public List<OrderDTO> getAllOrdersByCustomer(@PathParam("customer") Long customer) {
        return toDTOs(orderBean.allByCustomer(customer));
    }

    @GET
    @Path("{id}")
    public Response getOrderDetails(@PathParam("id") Long id) throws Exception {
        return Response.status(Response.Status.OK).entity(toDTO(orderBean.find(id))).build();
    }

    @POST
    @Path("/")
    public Response createNewOrder(OrderDTO orderDTO) throws Exception {
        orderBean.create(
                orderDTO.getId(),
                orderDTO.getStatus(),
                orderDTO.getCustomer(),
                orderDTO.getLogisticsOperators(),
                orderDTO.getPackages(),
                orderDTO.getProducts()
        );
        Order order = orderBean.find(orderDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateOrder(@PathParam("id") Long id, OrderDTO orderDTO) throws Exception {
        Order order = orderBean.find(id);

        orderBean.update(
                id,
                orderDTO.getStatus() != null ? orderDTO.getStatus() : order.getStatus(),
                orderDTO.getCustomer() != null ? orderDTO.getCustomer() : order.getCustomer(),
                orderDTO.getLogisticsOperators() != null ? orderDTO.getLogisticsOperators() : order.getLogisticsOperators()
        );

        order = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}/{status}")
    public Response updateOrderStatus(@PathParam("id") Long id, @PathParam("status") String status) throws Exception {
        Order order = orderBean.find(id);

        orderBean.updateStatus(
                id,
                status
        );

        order = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}/addpackage/{package}")
    public Response addPackageToOrder(@PathParam("id") Long id, @PathParam("package") Long packageId) throws Exception {
        Order order = orderBean.find(id);

        orderBean.addPackageToOrder(
                id,
                packageId
        );
        order = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}/addproduct/{product}")
    public Response addProductToOrder(@PathParam("id") Long id, @PathParam("product") Long productId) throws Exception {
        Order order = orderBean.find(id);

        orderBean.addProductToOrder(
                id,
                productId
        );
        order = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}/removepackage/{package}")
    public Response removePackageFromOrder(@PathParam("id") Long id, @PathParam("package") Long packageId) throws Exception {
        Order order = orderBean.find(id);

        orderBean.removePackageFromOrder(
                id,
                packageId
        );
        order = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}/removeproduct/{product}")
    public Response removeProductFromOrder(@PathParam("id") Long id, @PathParam("product") Long productId) throws Exception {
        Order order = orderBean.find(id);

        orderBean.removeProductFromOrder(
                id,
                productId
        );
        order = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") Long id) throws Exception {
        orderBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }
}