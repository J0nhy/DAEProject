package pt.ipleiria.estg.dei.ei.dae.packages.dtos;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private String status;
    private String customerName;
    private String logisticsOperatorName;
    private List<OrderItemDTO> OrderItems;
    private long packageId;


    public OrderDTO(){
        this.OrderItems = new ArrayList<>();
    }
    public OrderDTO(Long id, String status, String customerName, String logisticsOperatorName, long packageId,
                    List<OrderItemDTO> orderItems) {
        this.id = id;
        this.status = status;
        this.customerName = customerName;
        this.logisticsOperatorName = logisticsOperatorName;
        this.packageId = packageId;
        this.OrderItems = orderItems;
    }

    public OrderDTO(Long id, String status, String username, String username1, Long id1) {
        this.id = id;
        this.status = status;
        this.customerName = username;
        this.logisticsOperatorName = username1;
        this.packageId = id1;
    }

    public OrderDTO(Long id, String status, String username, String username1, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.status = status;
        this.customerName = username;
        this.logisticsOperatorName = username1;
        this.OrderItems = orderItems;
    }

    public OrderDTO(Long id, String status, List<OrderItemDTO> orderItemDTOS) {
        this.id = id;
        this.status = status;
        this.OrderItems = orderItemDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public String getLogisticsOperatorName() {
        return logisticsOperatorName;
    }

    public void setLogisticsOperatorName(String logisticsOperatorName) {
        this.logisticsOperatorName = logisticsOperatorName;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        OrderItems = orderItems;
    }
}
