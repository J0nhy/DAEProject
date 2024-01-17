package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.ManufacturerDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
//@Authenticated
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public List<OrderDTO> getOrdersWithOrderItems() {
        List<Order> orders = orderBean.getAll();
        return toDTOs(orders);
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        Order order = orderBean.getOrderProducts(orderId);
        if (order != null) {
            var orderToGet = orderDto(order);
            return Response.ok(orderToGet).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }
    @GET
    @Path("{id}/items")
    public Response getProductsByOrder(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        Order order = orderBean.getOrderProducts(orderId);
        if(order != null) {
            var orderItems = ordersItemDTO(order.getOrderItems());
            return Response.ok(orderItems).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }


    @POST
    @Path("/")
    public Response createNewOrder(String username, String status, String orders)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        long id = orderBean.create(
                username,
                orders);

        Order order = orderBean.findOrFail(id);
        if (order == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateOrder(@PathParam("id") Long id, OrderDTO orderDTO)
            throws MyEntityNotFoundException {
        orderBean.update(
                id,
                orderDTO.getStatus(),
                orderDTO.getCustomerName(),
                orderDTO.getLogisticsOperatorName(),
                orderDTO.getPackageId()
        );
        Order order = orderBean.findOrFail(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEndConsumer(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        orderBean.delete(orderId);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}/package/{packageId}")
    public Response updateOrderPackage(@PathParam("id") Long id, @PathParam("packageId") Long packageId)
            throws MyEntityNotFoundException {
        orderBean.updateOrderPackage(id, packageId);
        Order order = orderBean.findOrFail(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }
    private OrderDTO orderDto(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getCustomer().getName(),
                order.getLogisticsOperators() != null ? order.getLogisticsOperators().getName() : null,
                ordersItemDTO(order.getOrderItems())
        );
    }

    private List<OrderItemDTO> ordersItemDTO(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::orderItemDTO).collect(Collectors.toList());
    }

    private OrderItemDTO orderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getQuantity(),
                unitProductDTO(orderItem.getUnitProduct())
        );
    }

    private UnitProductDTO unitProductDTO(UnitProduct unitProduct) {
        return new UnitProductDTO(
                unitProduct.getId(),
                unitProduct.getSerialNumber(),
                unitProduct.getAvailable(),
                productDTO(unitProduct.getProduct() == null ? new Product() : unitProduct.getProduct()),
                packageSensorToDTO(unitProduct.getPackageSensor() == null ?  new PackageSensor() : unitProduct.getPackageSensor())
        );
    }

    private ProductDTO productDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername()
        );
    }

    private PackageSensorDTO packageSensorToDTO(PackageSensor packageSensor) {
        return new PackageSensorDTO(
                packageSensor.getId(),
                sensorValueDTOs(packageSensor.getSensorValues() == null ? null : packageSensor.getSensorValues()),
                packageDTO(packageSensor.getPackagging() == null ? new Package() : packageSensor.getPackagging())
        );
    }

    private List<SensorValueDTO> sensorValueDTOs(List<SensorValue> sensorValues) {
        return sensorValues.stream().map(this::sensorValueDTO).collect(Collectors.toList());
    }

    private SensorValueDTO sensorValueDTO(SensorValue sensorValue) {
        return new SensorValueDTO(
                sensorValue.getId(),
                SensorDTO.toDTO(sensorValue.getSensor()),
                sensorValue.getValue()
        );
    }

    private PackageDTO packageDTO(Package aPackage) {
        return new PackageDTO(
                aPackage.getId(),
                aPackage.getPackageType(),
                aPackage.getPackageMaterial()
        );
    }

    // -----------------------------------------------------
    private List<OrderDTO> toDTOs(List<Order> orders) {
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private OrderDTO toDTO(Order order) {
        String logisticsOperatorName = order.getLogisticsOperators() != null ? order.getLogisticsOperators().getName() : null;
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getCustomer().getName(),
                logisticsOperatorName,
                ordersItemDTO(order.getOrderItems())
        );
    }

    private ManufacturerDTO manufacturerDTO(Manufacturer manufacturer) {
        return new ManufacturerDTO(
                manufacturer.getUsername(),
                manufacturer.getPassword(),
                manufacturer.getName(),
                manufacturer.getEmail(),
                manufacturer.getNif(),
                manufacturer.getPhone(),
                manufacturer.getAddress()
        );
    }


}
