package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllLogisticsOperators",
                query = "SELECT m FROM Manufacturer m ORDER BY m.name" //JPQL
        )
})
public class LogisticsOperator extends User implements Serializable {

    @NotNull
    private String company;
    public LogisticsOperator(){}

    public LogisticsOperator(String username, String password, String name, String email,String company){
        super(username, password, name, email);
        this.company=company;
    }

    public void setCompany(String company) {
        this.company=company;
    }
    public String getCompany() {
        return company;
    }


}
