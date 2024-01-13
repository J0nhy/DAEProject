package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;

import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private CustomerBean customerBean;

    @EJB
    private PackageBean packageBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");
    @PostConstruct
    public void populateDB() {

        System.out.println("Hello Java EE!");

        try{
            customerBean.create("Customer1", "123", "Customer1", "Customer1@mail.pt", 123456789, 123456789, "Customer1 address");
            customerBean.create("Customer2", "123", "Customer2", "Customer2@mail.pt", 123456789, 123456789, "Customer2 address");
            customerBean.create("Customer3", "123", "Customer3", "Customer3@mail.pt", 123456789, 123456789, "Customer3 address");
            customerBean.create("Customer4", "123", "Customer4", "Customer4@mail.pt", 123456789, 123456789, "Customer4 address");

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

    }
}