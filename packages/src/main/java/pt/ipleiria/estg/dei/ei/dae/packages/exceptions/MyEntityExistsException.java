package pt.ipleiria.estg.dei.ei.dae.packages.exceptions;

public class MyEntityExistsException extends Exception{

    // para quando o user a ser criado já existe
    private String message;

    public MyEntityExistsException(String message) {
        super(message);
    }
}
