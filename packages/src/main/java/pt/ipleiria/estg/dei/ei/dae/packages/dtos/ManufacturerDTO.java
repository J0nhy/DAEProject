package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import java.io.Serializable;

public class ManufacturerDTO implements Serializable {
    private String username;
    private String password;
    private String email;
    private String name;
    private int nif;
    private int phone;
    private String address;


    public ManufacturerDTO(){
    }

    public ManufacturerDTO(String username, String password,String email, String name, int nif, int phone, String address){
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nif = nif;
        this.phone = phone;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}