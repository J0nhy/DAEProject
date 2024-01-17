package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import pt.ipleiria.estg.dei.ei.dae.packages.entities.UnitProduct;

import java.util.List;

public class OrderItemDTO {

    private Long id;
    private Integer quantity;

    private UnitProductDTO unitProductDTO;

    public OrderItemDTO(long id, int quantity, UnitProductDTO unitProductDTO) {
        this.id = id;
        this.unitProductDTO = unitProductDTO;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UnitProductDTO getUnitProductDTO() {
        return unitProductDTO;
    }

    public void setUnitProductDTO(UnitProductDTO unitProductDTO) {
        this.unitProductDTO = unitProductDTO;
    }
}
