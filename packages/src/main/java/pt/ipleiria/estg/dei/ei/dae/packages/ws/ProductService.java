package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ProductService {

    @EJB
    private ProductBean productBean;

    @Context
    private SecurityContext securityContext;

    private ProductDTO toDTO(Product product) {
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

    public List<ProductDTO> toDTOs(List<Product> products) {
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProductDTO> getAllProducts() {
        return toDTOs(productBean.all());
    }

    @GET
    @Path("{id}")
    public Response getProductDetails(@PathParam("id") long id) throws Exception {
        return Response.status(Response.Status.OK).entity(toDTO(productBean.find(id))).build();
    }

    @POST
    @Path("/")
    public Response createNewProduct(ProductDTO productDTO) throws Exception {
        productBean.create(
                productDTO.getProductName(),
                productDTO.getProductDescription(),
                productDTO.getProductCategory(),
                productDTO.getProductManufacturer(),
                productDTO.getProductBrand(),
                productDTO.getProductImage(),
                productDTO.getProductPrice(),
                productDTO.getProductWeight()
        );
        Product product = productBean.find(productDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(product)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateProduct(@PathParam("id") long id, ProductDTO productDTO) throws Exception {
        Product product = productBean.find(id);

        productBean.update(
                id,
                productDTO.getProductName() != null ? productDTO.getProductName() : product.getProductName(),
                productDTO.getProductDescription() != null ? productDTO.getProductDescription() : product.getProductDescription(),
                productDTO.getProductCategory() != null ? productDTO.getProductCategory() : product.getProductCategory(),
                productDTO.getProductManufacturer() != null ? productDTO.getProductManufacturer() : product.getProductManufacturer(),
                productDTO.getProductBrand() != null ? productDTO.getProductBrand() : product.getProductBrand(),
                productDTO.getProductImage() != null ? productDTO.getProductImage() : product.getProductImage(),
                productDTO.getProductPrice() != null ? productDTO.getProductPrice() : product.getProductPrice(),
                productDTO.getProductWeight() != null ? productDTO.getProductWeight() : product.getProductWeight()
        );

        product = productBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(product)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") long id) throws Exception {
        productBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }
}