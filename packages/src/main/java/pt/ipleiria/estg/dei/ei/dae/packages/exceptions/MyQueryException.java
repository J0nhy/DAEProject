package pt.ipleiria.estg.dei.ei.dae.packages.exceptions;

public class MyQueryException extends Exception{

    private String message;

    public MyQueryException(String message) {
        super(message);
    }
}
