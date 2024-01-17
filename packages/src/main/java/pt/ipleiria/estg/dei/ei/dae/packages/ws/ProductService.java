package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ProductService {

    @EJB
    private ProductBean productBean;

    // get all products
    @GET
    @Path("/")
    public List<ProductDTO> getAllProducts() {
        return ProductDTO.toDTOs(productBean.all());
    }


    // get product by id
    @GET
    @Path("{id}")
    public Response getProductDetails(@PathParam("id") Long id) throws MyEntityNotFoundException {
        Product product = productBean.findWithPackage(id);
        if (product != null) {
            return Response.ok(ProductDTO.toDTO(product)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PRODUCT")
                .build();
    }

    // get all sensors not attribute from a product
    @GET
    @Path("{id}/sensorsNotAttribute")
    public Response getProductSensorsNotAttribute(@PathParam("id") Long id) throws MyEntityNotFoundException {
        List<Sensor> sensors = productBean.getAllSensorsNotAttribute(id);
        if (sensors.size() >= 0) {
            return Response.ok(SensorDTO.toDTOs(sensors)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_SENSORS")
                .build();
    }

    // get all unit products from a product
    @GET
    @Path("{id}/unitProducts")
    public Response getProductUnitProducts(@PathParam("id") Long id) throws MyEntityNotFoundException {
        List< UnitProduct> unitProducts = productBean.getAllUnitProducts(id);
        if (unitProducts.size() > 0) {
            return Response.ok(unitProductDTOs(unitProducts)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PRODUCT")
                .build();
    }

    @PUT
    @Path("{id}/addSensor/{sensorId}")
    public Response addSensorToUnitProduct(@PathParam("id") Long productId, @PathParam("sensorId") Long sensorId) {
        List<UnitProduct> unitProducts = productBean.addSensorToProduct(productId, sensorId);
        if (!unitProducts.isEmpty() ) {
            return Response.ok(unitProductDTOs(unitProducts)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_UNIT_PRODUCT")
                .build();
    }

    private List<UnitProductDTO> unitProductDTOs(List<UnitProduct> unitProducts) {
        return unitProducts.stream().map(this::unitProductDTO).collect(java.util.stream.Collectors.toList());
    }

    private UnitProductDTO unitProductDTO(UnitProduct unitProduct) {
        return new UnitProductDTO(
                unitProduct.getId(),
                unitProduct.getSerialNumber(),
                unitProduct.getAvailable(),
                productDTO(unitProduct.getProduct() == null ? null : unitProduct.getProduct()),
                packageSensorToDTO(unitProduct.getPackageSensor() == null ? null : unitProduct.getPackageSensor())
        );
    }

    private ProductDTO productDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername()
        );
    }

    private PackageSensorDTO packageSensorToDTO(PackageSensor packageSensor) {
        if (packageSensor != null)
            return new PackageSensorDTO(
                    packageSensor.getId(),
                    sensorValueDTOs(packageSensor.getSensorValues() == null ? null : packageSensor.getSensorValues()),
                    packageDTO(packageSensor.getPackagging() == null ? null : packageSensor.getPackagging()));
        else
            return new PackageSensorDTO();
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

    // create new product
    @POST
    @Path("/")
    public Response createNewProduct(ProductDTO productDTO) throws
            MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        long id  = productBean.create(
                productDTO.getName(),
                productDTO.getStock(),
                productDTO.getImage(),
                productDTO.getManufacturerUsername(),
                productDTO.getPackageProductId()
        );
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(ProductDTO.toDTO(product)).build();
    }
    // update product
    @PUT
    @Path("{id}")
    public Response updateProduct(@PathParam("id") Long id, ProductDTO productDTO)
            throws MyEntityNotFoundException {
        productBean.update(
                id,
                productDTO.getName(),
                productDTO.getStock(),
                productDTO.getManufacturerUsername(),
                productDTO.getImage()
        );
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(ProductDTO.toDTO(product)).build();
    }

    // SETT PACKAGING
    @PUT
    @Path("{id}/package/{packageId}")
    public Response setPackaging(@PathParam("id") Long id, @PathParam("packageId") Long packageId)
            throws MyEntityNotFoundException {
        productBean.setPackaging(id, packageId);
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(ProductDTO.toDTO(product)).build();
    }

    // AUXILIARY FUNCTIONS

    // delete product
    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") Long id) throws MyEntityNotFoundException{
        productBean.delete(id);
        return Response.ok().build();
    }





}
