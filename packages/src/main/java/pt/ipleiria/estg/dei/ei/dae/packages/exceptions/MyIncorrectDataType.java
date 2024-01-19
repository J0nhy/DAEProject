package pt.ipleiria.estg.dei.ei.dae.packages.exceptions;

public class MyIncorrectDataType extends Exception{

    private String message;

    public MyIncorrectDataType(String message) {
        super(message);
    }
}
