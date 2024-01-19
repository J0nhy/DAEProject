package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "logisticsOperators", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Order> orders;

    public LogisticsOperator(){
        this.orders = new ArrayList<>();
    }

    public LogisticsOperator(String username, String password, String name, String email,String company){
        super(username, password, name, email);
        this.company=company;
        this.orders = new ArrayList<>();
    }

    public void setCompany(String company) {
        this.company=company;
    }
    public String getCompany() {
        return company;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
