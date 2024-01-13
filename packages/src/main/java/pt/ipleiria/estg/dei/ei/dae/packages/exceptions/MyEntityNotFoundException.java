package pt.ipleiria.estg.dei.ei.dae.packages.exceptions;

public class MyEntityNotFoundException extends Exception{

    private String message;

    public MyEntityNotFoundException(String message) {
        super(message);
    }
}
