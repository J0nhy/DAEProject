package pt.ipleiria.estg.dei.ei.dae.packages.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.LogisticsOperator;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private CustomerBean customerBean;

    @EJB
    private ProductPackageBean productPackageBean;

    @EJB
    private ManufacturerBean manufacturerBean;
    @EJB
    private LogisticsOperatorBean logisticsOperatorBean;

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private OrderItemBean orderItemBean;

    @EJB
    private PackageSensorBean packageSensorBean;

    @EJB
    private SensorBean sensorBean;

    @EJB
    private UnitProductBean unitProductBean;



    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");
    @PostConstruct
    public void populateDB() throws Exception {

        System.out.println("Hello Java EE!");

        //CREATE CUSTOMERS
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
            logisticsOperatorBean.create("logistic2", "123", "logi2", "logi2@mail.pt",
                    "NACEX");
            logisticsOperatorBean.create("logistic3", "123", "logi3", "logi3@mail.pt",
                    "CTT");


        }catch (Exception e){
            logger.severe(e.getMessage());
        }

        //create packages
        try {
            productPackageBean.create(PackageType.Primary, "Wood");
            productPackageBean.create(PackageType.Secondary, "Metal");
            productPackageBean.create(PackageType.Tertiary, "Glass");
           /* productPackageBean.create(PackageType.Primary, "Paper");
            productPackageBean.create(PackageType.Secondary, "Fabric");
            productPackageBean.create(PackageType.Tertiary, "Ceramic");
            productPackageBean.create(PackageType.Primary, "Rubber");
            productPackageBean.create(PackageType.Secondary, "Aluminum");
            productPackageBean.create(PackageType.Tertiary, "Cardboard");
            productPackageBean.create(PackageType.Primary, "Plastic");
            productPackageBean.create(PackageType.Secondary, "Leather");
            productPackageBean.create(PackageType.Tertiary, "Concrete");
            productPackageBean.create(PackageType.Primary, "Foil");
            productPackageBean.create(PackageType.Secondary, "Bamboo");
            productPackageBean.create(PackageType.Tertiary, "Nylon");
*/
            System.out.println("Package created");
        }catch (MyConstraintViolationException e) {
            logger.warning(e.getMessage());
        }

        //Create Products
        try {

            productBean.create("Product1", 10, "Product1 image", "Manufacturer1",
                    1);
            productBean.create("Product2", 10, "Product2 image", "Manufacturer1",
                    0);
            /*productBean.create("Product3", 10, "Product3 image", "Manufacturer1",
                    3);
            productBean.create("Product4", 10, "Product4 image", "Manufacturer1",
                    4);
            productBean.create("Product5", 10, "Product5 image", "Manufacturer1",
                    5);
            productBean.create("Product6", 10, "Product6 image", "Manufacturer1",
                    1);
            productBean.create("Product7", 10, "Product7 image", "Manufacturer1",
                    2);
            productBean.create("Product8", 10, "Product8 image", "Manufacturer1",
                    3);
            productBean.create("Product9", 10, "Product9 image", "Manufacturer1",
                    4);
            productBean.create("Product10", 10, "Product10 image", "Manufacturer1",
                    5);
            productBean.create("Product11", 10, "Product11 image", "Manufacturer1",
                    1);
            productBean.create("Product12", 10, "Product12 image", "Manufacturer1",
                    2);
            productBean.create("Product13", 10, "Product13 image", "Manufacturer1",
                    3);
            productBean.create("Product14", 10, "Product14 image", "Manufacturer1",
                    4);
            productBean.create("Product15", 10, "Product15 image", "Manufacturer1",
                    5);
*/

        }catch (Exception e){
            logger.severe(e.getMessage());
        }


        try {
            String data  = "{\n" +
                    "  \"status\": \"WAITING_PAYMENT\",\n" +
                    "  \"orderItems\": [\n" +
                    "    {\n" +
                    "      \"quantity\": 1,\n" +
                    "      \"productId\": \"1\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            long order = orderBean.create("Customer1", data );

            data  = "{\n" +
                    "  \"status\": \"WAITING_PAYMENT\",\n" +
                    "  \"orderItems\": [\n" +
                    "    {\n" +
                    "      \"quantity\": 4,\n" +
                    "      \"productId\": \"2\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            long order2 = orderBean.create("Customer2", data );


        }catch (Exception e){
            logger.severe(e.getMessage());
        }

        try {
            orderItemBean.create(1, 1, 1);
            orderItemBean.create(2, 2, 4);
            System.out.println("OrderItem created");
        }catch (Exception e){
            logger.warning(e.getMessage());
        }

        try {
            sensorBean.create("Product", "sensor1", "sensor1", "sensor1", "sensor1");
            sensorBean.create("Order", "sensor2", "sensor2", "sensor2", "sensor2");

            System.out.println("Sensor created");
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

        try{
            packageSensorBean.addSensorToPackage(1,1);
            packageSensorBean.addSensorToPackage(2,1);
            System.out.println("SensorValue add");
        }catch (Exception e){
            logger.warning(e.getMessage());
        }
    }
}