package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.*;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
//teste
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

        //CREATE CUSTOMERS
        try {
            customerBean.create("Customer1", "123", "Customer1", "Customer1@mail.pt", 123456789, 123456789, "Customer1 address");
            customerBean.create("Customer2", "123", "Customer2", "Customer2@mail.pt", 123456789, 123456789, "Customer2 address");
            customerBean.create("Customer3", "123", "Customer3", "Customer3@mail.pt", 123456789, 123456789, "Customer3 address");
            customerBean.create("Customer4", "123", "Customer4", "Customer4@mail.pt", 123456789, 123456789, "Customer4 address");

            customerBean.delete("Customer4");

            System.out.println("Customer Created");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        //CREATE PACKAGES
        try {
            packageBean.create(PackageType.Primary, PackageMaterials.MADEIRA);
            packageBean.create(PackageType.Secondary, PackageMaterials.METAL);
            packageBean.create(PackageType.Tertiary, PackageMaterials.VIDRO);
            packageBean.create(PackageType.Primary, PackageMaterials.PAPEL);
            packageBean.create(PackageType.Secondary, PackageMaterials.PLASTICO);
            packageBean.create(PackageType.Tertiary, PackageMaterials.OBSIDIAN);
            packageBean.create(PackageType.Primary, PackageMaterials.MADEIRA);
            packageBean.create(PackageType.Secondary, PackageMaterials.METAL);
            packageBean.create(PackageType.Tertiary, PackageMaterials.VIDRO);
            packageBean.create(PackageType.Primary, PackageMaterials.PAPEL);
            packageBean.create(PackageType.Secondary, PackageMaterials.PLASTICO);
            packageBean.create(PackageType.Tertiary, PackageMaterials.OBSIDIAN);
            packageBean.create(PackageType.Primary, PackageMaterials.MADEIRA);
            packageBean.create(PackageType.Secondary, PackageMaterials.METAL);
            packageBean.create(PackageType.Tertiary, PackageMaterials.VIDRO);

            //Packages não atribuidas a nenhum order
            packageBean.create(PackageType.Primary, PackageMaterials.MADEIRA);
            packageBean.create(PackageType.Secondary, PackageMaterials.METAL);
            packageBean.create(PackageType.Tertiary, PackageMaterials.VIDRO);
            packageBean.create(PackageType.Primary, PackageMaterials.PAPEL);
            packageBean.create(PackageType.Secondary, PackageMaterials.PLASTICO);


            System.out.println("Package Created");
        } catch (Exception e) {
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

        //Create Products
        try {

            productBean.create("Product1", "Product1 description", "Product1 category", "Product1 manufacturer",
                    "Product1 brand", "Product1 image", 1.0, 1.0);
            productBean.create("Product2", "Product2 description", "Product2 category", "Product2 manufacturer",
                    "Product2 brand", "Product2 image", 2.0, 2.0);
            productBean.create("Product3", "Product3 description", "Product3 category", "Product3 manufacturer",
                    "Product3 brand", "Product3 image", 3.0, 3.0);
            productBean.create("Product4", "Product4 description", "Product4 category", "Product4 manufacturer",
                    "Product4 brand", "Product4 image", 4.0, 4.0);
            productBean.create("Product5", "Product5 description", "Product5 category", "Product5 manufacturer",
                    "Product5 brand", "Product5 image", 5.0, 5.0);
            productBean.create("Product6", "Product6 description", "Product6 category", "Product6 manufacturer",
                    "Product6 brand", "Product6 image", 6.0, 6.0);
            productBean.create("Product7", "Product7 description", "Product7 category", "Product7 manufacturer",
                    "Product7 brand", "Product7 image", 7.0, 7.0);
            productBean.create("Product8", "Product8 description", "Product8 category", "Product8 manufacturer",
                    "Product8 brand", "Product8 image", 8.0, 8.0);
            productBean.create("Product9", "Product9 description", "Product9 category", "Product9 manufacturer",
                    "Product9 brand", "Product9 image", 9.0, 9.0);
            productBean.create("Product10", "Product10 description", "Product10 category", "Product10 manufacturer",
                    "Product10 brand", "Product10 image", 10.0, 10.0);
            productBean.create("Product11", "Product11 description", "Product11 category", "Product11 manufacturer",
                    "Product11 brand", "Product11 image", 11.0, 11.0);
            productBean.create("Product12", "Product12 description", "Product12 category", "Product12 manufacturer",
                    "Product12 brand", "Product12 image", 12.0, 12.0);
            productBean.create("Product13", "Product13 description", "Product13 category", "Product13 manufacturer",
                    "Product13 brand", "Product13 image", 13.0, 13.0);
            productBean.create("Product14", "Product14 description", "Product14 category", "Product14 manufacturer",
                    "Product14 brand", "Product14 image", 14.0, 14.0);
            productBean.create("Product15", "Product15 description", "Product15 category", "Product15 manufacturer",
                    "Product15 brand", "Product15 image", 15.0, 15.0);


        }catch (Exception e){
            logger.severe(e.getMessage());
        }


        try {
            List<Package> packages1 = new ArrayList<>();
            List<Package> packages2 = new ArrayList<>();
            List<Package> packages3 = new ArrayList<>();
            List<Package> packages4 = new ArrayList<>();
            List<Package> packages5 = new ArrayList<>();

            packages1.add(packageBean.find((long) 1));
            packages1.add(packageBean.find((long) 2));
            packages1.add(packageBean.find((long) 3));

            packages2.add(packageBean.find((long) 4));
            packages2.add(packageBean.find((long) 5));
            packages2.add(packageBean.find((long) 6));

            packages3.add(packageBean.find((long) 7));
            packages3.add(packageBean.find((long) 8));
            packages3.add(packageBean.find((long) 9));

            packages4.add(packageBean.find((long) 10));
            packages4.add(packageBean.find((long) 11));
            packages4.add(packageBean.find((long) 12));

            packages5.add(packageBean.find((long) 13));
            packages5.add(packageBean.find((long) 14));
            packages5.add(packageBean.find((long) 15));

            Order order1 = orderBean.create(StatusMessage.ENVIADA, customerBean.findCustomer("Customer1"));
            Order order2 = orderBean.create(StatusMessage.PENDENTE, customerBean.findCustomer("Customer2"));
            Order order3 = orderBean.create(StatusMessage.ENTREGUE, customerBean.findCustomer("Customer3"));
            Order order4 = orderBean.create(StatusMessage.PENDENTE, customerBean.findCustomer("Customer1"));
            Order order5 = orderBean.create(StatusMessage.ENVIADA, customerBean.findCustomer("Customer1"));

            orderBean.addPackageToOrder(order1.getId(), 1);
            orderBean.addPackageToOrder(order1.getId(), 2);

            orderBean.addPackageToOrder(order2.getId(), 3);
            orderBean.addPackageToOrder(order2.getId(), 4);
            orderBean.addPackageToOrder(order2.getId(), 5);

            orderBean.addPackageToOrder(order3.getId(), 6);

            orderBean.addPackageToOrder(order4.getId(), 7);
            orderBean.addPackageToOrder(order4.getId(), 8);

            orderBean.addPackageToOrder(order5.getId(), 9);
            orderBean.addPackageToOrder(order5.getId(), 10);
            orderBean.addPackageToOrder(order5.getId(), 11);
            orderBean.addPackageToOrder(order5.getId(), 12);
            orderBean.addPackageToOrder(order5.getId(), 13);
            orderBean.addPackageToOrder(order5.getId(), 14);
            orderBean.addPackageToOrder(order5.getId(), 15);

            orderBean.setLogisticsOperator(order1.getId(), "logistics1");
            orderBean.setLogisticsOperator(order3.getId(), "logistics1");
            orderBean.setLogisticsOperator(order5.getId(), "logistics1");

            orderBean.setLogisticsOperator(order2.getId(), "logistics1");


        }catch (Exception e){
            logger.severe(e.getMessage());
        }
    }
}