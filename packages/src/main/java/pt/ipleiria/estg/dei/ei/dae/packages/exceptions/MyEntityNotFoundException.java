package pt.ipleiria.estg.dei.ei.dae.packages.exceptions;

public class MyEntityNotFoundException extends Exception{
    // para quando o user procurado nao esta na base de dados

    private String message;

    public MyEntityNotFoundException(String message) {
        super(message);
    }
}
