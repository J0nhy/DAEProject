package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.Entity;

@Entity
public class Manufacturer extends User{

    public Manufacturer(){}

    public Manufacturer(String username, String password, String name, String email){
        super(username, password, name, email);
    }

}
