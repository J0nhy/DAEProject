package pt.ipleiria.estg.dei.ei.dae.packages.exceptions;

public class MyEntityExistsException extends Exception{
    private String message;

    public MyEntityExistsException(String message) {
        super(message);
    }
}
