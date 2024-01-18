package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.ejb.EJB;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    @Context
    private SecurityContext securityContext;

    private OrderDTO toDTO(Order order) { // get com listas
        List<PackageDTO> packages = toDTOsPackagesNoSensor(order.getPackages());
        List<ProductDTO> products = toDTOsProducts(order.getProducts());
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getLogisticsOperators(),
                packages,
                products,
                order.getCustomerUsername()
        );

    }
    public List<OrderDTO> toDTOs(List<Order> orders) { // conversao dos DTOs
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private OrderDTO toDTONoPackagesandProducts(Order order) { // get sem as listas
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getLogisticsOperators(),
                null,
                null,
                order.getCustomerUsername()
        );

    }
    public List<OrderDTO> toDTOsNoPackageandProducts(List<Order> orders) { // conversao dos DTOs
        return orders.stream().map(this::toDTONoPackagesandProducts).collect(Collectors.toList());
    }

    private PackageDTO toDTO(Package pack) {
        List<SensorDTO> sensors = toDTOsSensors(pack.getSensors());
        return new PackageDTO(
                pack.getId(),
                pack.getPackageType(),
                pack.getPackageMaterial(),
                pack.getOrderId(),
                sensors
        );
    }

    public List<PackageDTO> toDTOsPackages (List<Package> packages) {
        return packages.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private PackageDTO toDTONoSensor(Package pack) {
        return new PackageDTO(
                pack.getId(),
                pack.getPackageType(),
                pack.getPackageMaterial(),
                pack.getOrderId(),
                null
        );
    }

    public List<PackageDTO> toDTOsPackagesNoSensor (List<Package> packages) {
        return packages.stream().map(this::toDTONoSensor).collect(Collectors.toList());
    }

    private SensorDTO toDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSensorType(),
                sensor.getValue(),
                sensor.getDataType(),
                0
        );
    }

    public List<SensorDTO> toDTOsSensors (List<Sensor> sensors) {
        return sensors.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductCategory(),
                product.getProductManufacturer(),
                product.getProductBrand(),
                product.getProductImage(),
                product.getProductPrice(),
                product.getProductWeight()
        );
    }
    public List<ProductDTO> toDTOsProducts (List<Product> products) {
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }
    /*
    public CustomerDTO toDTONoOrders(Customer customer){
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

    public List<CustomerDTO> toDTOsNoOrders(List<Customer> customers) {
        return customers.stream().map(this::toDTONoOrders).collect(Collectors.toList());
    }
    */
    @GET
    @Path("/")
    public List<OrderDTO> getAllOrders() {
        return toDTOsNoPackageandProducts(orderBean.all());
    }

    @GET
    @Path("{id}")
    public Response getOrderDetails(@PathParam("id") long id) throws Exception {
        return Response.status(Response.Status.OK).entity(toDTO(orderBean.find(id))).build();
    }

    @GET
    @Path("/customer/{customer}")
    public List<OrderDTO> getAllOrdersByCustomer(@PathParam("customer") long customer) {
        return toDTOs(orderBean.allByCustomer(customer));
    }
    /*
    @POST
    @Path("/")
    public Response createNewOrder(OrderDTO orderDTO) throws Exception {
        List<Product> products = new ArrayList<>();
        List<ProductDTO> productDTOS = orderDTO.getProducts();
        for (ProductDTO productDTO : productDTOS) {
            products.add(productBean.find(productDTO.getId()));
        }
        orderBean.create(
                orderDTO.getStatus(),
                orderDTO.getCustomer(),
                products

        );
        Order order = orderBean.find(orderDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateOrder(@PathParam("id") long id, OrderDTO orderDTO) throws Exception {
        Order order = orderBean.find(id);
        Customer customer = customerBean

        orderBean.update(
                id,
                orderDTO.getStatus() != null ? orderDTO.getStatus() : order.getStatus(),
                orderDTO.getCustomer() != null ? orderDTO.getCustomer() : order.getCustomer(),
                orderDTO.getLogisticsOperators() != null ? orderDTO.getLogisticsOperators() : order.getLogisticsOperators()
        );

        order = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }
    */
    @PUT
    @Path("{id}/{status}")
    public Response updateOrderStatus(@PathParam("id") long id, @PathParam("status") String status) throws Exception {
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
    public Response addPackageToOrder(@PathParam("id") long id, @PathParam("package") long packageId) throws Exception {
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
    public Response addProductToOrder(@PathParam("id") long id, @PathParam("product") long productId) throws Exception {
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
    public Response removePackageFromOrder(@PathParam("id") long id, @PathParam("package") long packageId) throws Exception {
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
    public Response removeProductFromOrder(@PathParam("id") long id, @PathParam("product") long productId) throws Exception {
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
    public Response deleteOrder(@PathParam("id") long id) throws Exception {
        orderBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }
}