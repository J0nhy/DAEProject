package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllLogisticsOperators",
                query = "SELECT m FROM LogisticsOperator m ORDER BY m.name" //JPQL
        )
})
@SQLDelete(sql="UPDATE users SET deleted = TRUE WHERE username = ? AND version = ? AND dtype = 'LogisticsOperator'")
@Where(clause = "deleted IS FALSE")
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
