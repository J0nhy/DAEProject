package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.LogisticsOperator;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private CustomerBean customerBean;

    @EJB
    private PackageBean packageBean;

    @EJB
    private ManufacturerBean manufacturerBean;
    @EJB
    private LogisticsOperatorBean logisticsOperatorBean;

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;


    private List<Product> products;

    private Product product;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");
    @PostConstruct
    public void populateDB() throws Exception {

        System.out.println("Hello Java EE!");

        try{
            customerBean.create("Customer1", "123", "Customer1", "Customer1@mail.pt", 123456789, 123456789, "Customer1 address");
            customerBean.create("Customer2", "123", "Customer2", "Customer2@mail.pt", 123456789, 123456789, "Customer2 address");
            customerBean.create("Customer3", "123", "Customer3", "Customer3@mail.pt", 123456789, 123456789, "Customer3 address");
            customerBean.create("Customer4", "123", "Customer4", "Customer4@mail.pt", 123456789, 123456789, "Customer4 address");

            customerBean.delete("Customer4");

            System.out.println("Customer Created");
        }catch (Exception e){
            logger.severe(e.getMessage());
        }

        try {
            packageBean.create(1L, PackageType.Primary, "Cardboard", "123456789");
            packageBean.create(2L, PackageType.Secondary, "Plastic", "123456789");
            packageBean.create(3L, PackageType.Tertiary, "Wood", "123456789");

            System.out.println("Package Created");
        }catch (Exception e){
            logger.severe(e.getMessage());
        }

        //Create Manufacturer
        try {
            manufacturerBean.create("Manufacturer1", "123", "Manufacturer1", "Manufacturer1@mail.pt",
                    123456789, 999999999, "Manufacturer1 adress");


        }catch (Exception e){
            logger.severe(e.getMessage());
        }
    //
        //Create logisticsOperator
        try {
            logisticsOperatorBean.create("logistics1", "123", "logi1", "logi1@mail.pt",
                    "FEDEX");


        }catch (Exception e){
            logger.severe(e.getMessage());
        }


        productBean.create(1L,"Canivete","Canivete Suiço", "Armas","China", "1", "d", "10", "2");
        productBean.create(2L,"Canivete2","Canivete Suiço2", "Armas2","China2", "12", "d2", "102", "22");


        //products.add(productBean.find(1L));
       // products.add(productBean.find(2L));


        //CRIACAO TESTE ORDERS
        orderBean.create(1L,"Em Processamento",customerBean.findCustomer("Customer1"));

    }
}