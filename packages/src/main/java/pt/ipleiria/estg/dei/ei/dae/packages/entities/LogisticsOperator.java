package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.Entity;

@Entity
public class LogisticsOperator extends User{

    public LogisticsOperator(){}

    public LogisticsOperator(String username, String password, String name, String email){
        super(username, password, name, email);
    }

}
