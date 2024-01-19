package pt.ipleiria.estg.dei.ei.dae.packages.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllManufacturers",
                query = "SELECT m FROM Manufacturer m ORDER BY m.username" //JPQL
        )
})
@SQLDelete(sql="UPDATE users SET deleted = TRUE WHERE username = ? AND version = ? AND dtype = 'Manufacturer'")
@Where(clause = "deleted IS FALSE")
public class Manufacturer extends User implements Serializable {
    private int nif;
    private int phone;
    @NotNull
    private String address;
    @NotNull
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Order> orders;

    public Manufacturer(){
        this.orders = new ArrayList<>();
    }

    public Manufacturer(String username, String password, String name, String email, int nif, int phone, String address) {
        super(username, password, name, email);
        this.nif = nif;
        this.phone = phone;
        this.address = address;
        this.orders = new ArrayList<>();
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}