package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.hibernate.type.descriptor.jdbc.ObjectNullResolvingJdbcType;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyIncorrectDataType;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Authenticated;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private CustomerBean customerBean;

    @EJB
    private LogisticsOperatorBean logisticsBean;

    @EJB
    private PackageBean packageBean;

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    private OrderDTO toDTO(Order order) { // get com listas
        List<PackageDTO> packages = toDTOsPackagesNoSensor(order.getPackages());
        List<ProductDTO> products = toDTOsProducts(order.getProducts());
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getLogisticsOperatorsUsername(),
                packages,
                products,
                order.getCustomerUsername()
        );

    }
    public List<OrderDTO> toDTOs(List<Order> orders) { // conversao dos DTOs
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private OrderDTO toDTOCompleteOrder(Order order) {
        List<PackageDTO> packages = toDTOsPackagesNoSensor(order.getPackages());
        return new OrderDTO(
                order.getId(),
                order.getLogisticsOperatorsUsername(),
                packages
        );
    }

    public List<OrderDTO> toDTOsCompleteOrder(List<Order> orders) {
        return orders.stream().map(this::toDTOCompleteOrder).collect(Collectors.toList());
    }

    private OrderDTO toDTONoPackagesandProducts(Order order) { // get sem as listas
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getLogisticsOperatorsUsername(),
                null,
                null,
                order.getCustomerUsername()
        );

    }
    public List<OrderDTO> toDTOsNoPackageandProducts(List<Order> orders) { // conversao dos DTOs
        return orders.stream().map(this::toDTONoPackagesandProducts).collect(Collectors.toList());
    }

    private OrderDTO toDTOCreateOrder(Order order) { // get sem as listas
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getLogisticsOperatorsUsername(),
                null,
                null,
                order.getCustomerUsername()
        );
    }

    public List<OrderDTO> toDTOsCreateOrder(List<Order> orders) { // conversao dos DTOs
        return orders.stream().map(this::toDTOCreateOrder).collect(Collectors.toList());
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

    private OrderDTO toDTONoListsNoLogisticsOperator(Order order) { // get sem as listas e sem operator
        List<PackageDTO> packages = toDTOsPackagesNoSensor(order.getPackages());
        List<ProductDTO> products = toDTOsProducts(order.getProducts());
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                null,
                packages,
                products,
                order.getCustomerUsername()
        );

    }
    public List<OrderDTO> toDTOsNoListsNoLogisticsOperator(List<Order> orders) { // conversao dos DTOs
        return orders.stream().map(this::toDTONoListsNoLogisticsOperator).collect(Collectors.toList());
    }

    private SensorDTO toDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSensorType(),
                sensor.getValue(),
                sensor.getDataType(),
                sensor.getPackageId()
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
    @RolesAllowed({"Manufacturer"})
    public List<OrderDTO> getAllOrders() {
        return toDTOsCreateOrder(orderBean.all());
    }

    @GET
    @Path("{id}")
    public Response getOrderDetails(@PathParam("id") long id) throws Exception {
        Order order = orderBean.find(id);
        var principal = securityContext.getUserPrincipal();
        System.out.println(principal.getName());

        if (userBean.isManufacturer(principal.getName())){
            return Response.status(Response.Status.OK).entity(toDTO(order)).build();
        }

        if(!principal.getName().equals(order.getLogisticsOperatorsUsername()) && !principal.getName().equals(order.getCustomerUsername())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @GET
    @Path("/customer/{username}")
    @RolesAllowed({"Customer", "Manufacturer"})
    public List<OrderDTO> getAllOrdersByCustomer(@PathParam("username") String customer) {
        return toDTOsCreateOrder(orderBean.allByCustomer(customer));
    }

    @GET
    @Path("/logistics-operator/{username}")
    @RolesAllowed({"LogisticsOperator", "Manufacturer"})
    public List<OrderDTO> getAllOrdersByLogisticsOperator(@PathParam("username") String logisticsOperator) {
        return toDTOsNoPackageandProducts(orderBean.allByLogisticsOperator(logisticsOperator));
    }

    @GET
    @Path("{id}/sensors")
    public Response getAllSensorsByOrder(@PathParam("id") long id) throws Exception {
        Order order = orderBean.find(id);
        var principal = securityContext.getUserPrincipal();

        if(principal.getName().equals(order.getLogisticsOperatorsUsername()) || principal.getName().equals(order.getCustomerUsername())) {
            return Response.status(Response.Status.OK).entity(toDTOsSensors(orderBean.getOrdersSensors(id))).build();
        }

        if (userBean.isManufacturer(principal.getName())){
            return Response.status(Response.Status.OK).entity(toDTOsSensors(orderBean.getOrdersSensors(id))).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"Manufacturer"})
    public Response createNewOrder(OrderDTO orderDTO) throws MyEntityNotFoundException {
        try {
            Customer customer = customerBean.findCustomer(orderDTO.getCustomerUsername());
            Order order = orderBean.create(
                    orderDTO.getStatus(),
                    customer
            );

            return Response.status(Response.Status.CREATED).entity(toDTOCreateOrder(order)).build();
        }catch (MyEntityNotFoundException | MyIncorrectDataType e){

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_PRODUCT").build();
        }

    }

    @PUT
    @Path("{id}")
    @RolesAllowed({"Manufacturer", "LogisticsOperator"})
    public Response updateOrder(@PathParam("id") long id, OrderDTO orderDTO) throws Exception {
        Order order = orderBean.find(id);
        if (order.getStatus().equals(StatusMessage.ENTREGUE) || order.getStatus().equals(StatusMessage.CANCELADA)){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Order has already been delivered or canceled").build();
        }
        Customer customer = customerBean.findCustomer(order.getCustomerUsername());
        if (orderDTO.getCustomerUsername() != null)
            customer = customerBean.findCustomer(orderDTO.getCustomerUsername());
        LogisticsOperator logisticsOperator = logisticsBean.findLogisticOperator(order.getLogisticsOperatorsUsername());
        if (orderDTO.getLogisticsOperatorsUsername() != null)
            logisticsOperator = logisticsBean.findLogisticOperator(orderDTO.getLogisticsOperatorsUsername());

        orderBean.update(
                id,
                orderDTO.getStatus() != null ? orderDTO.getStatus() : order.getStatus(),
                customer,
                logisticsOperator
        );
        order = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}/completeOrder")
    @RolesAllowed({"Manufacturer"})
    public Response completeOrder(@PathParam("id") long id, OrderDTO orderDTO) throws Exception {
        try {
            Order order = orderBean.find(id);
            if (!order.getStatus().equals(StatusMessage.PENDENTE)){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Order has already been shipped").build();
            }
            List<PackageDTO> packList = orderDTO.getPackages();
            int quantidade = -1;
            Package pack = null;

            for (PackageDTO p : packList) {
                quantidade = p.getQuantity();
                if (quantidade > 0)
                    for (int i = 0; i < quantidade; i++) {
                        pack = packageBean.create(p.getPackageType(), p.getPackageMaterial());
                        orderBean.addPackageToOrder(id, pack.getId());
                    }
            }
            orderBean.setLogisticsOperator(
                    id,
                    orderDTO.getLogisticsOperatorsUsername()
            );
            orderBean.setStatusEnviado(id);

            return Response.status(Response.Status.OK).entity(toDTO(order)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_COMPLETING_ORDER").build();
        }
    }

    /*
    Estão a ser adicionadas em cima
    @PUT
    @Path("{id}/addPackage/{package}")
    public Response addPackageToOrder(@PathParam("id") long id, @PathParam("package") long packageId) throws Exception {
        orderBean.addPackageToOrder(
                id,
                packageId
        );
        Order order = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    N faz sentido remover packages??
    @PUT
    @Path("{id}/removePackage/{package}")
    public Response removePackageFromOrder(@PathParam("id") long id, @PathParam("package") long packageId) throws Exception {
        Order order = orderBean.find(id);

        orderBean.removePackageFromOrder(
                id,
                packageId
        );
        order = orderBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    // N faz sentido remover produtos
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

    Não faz sentido apagar uma encomenda no nosso contexto
    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") long id) throws Exception {
        orderBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }
     */
}