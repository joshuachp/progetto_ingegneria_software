package org.example.client.models;

import org.example.client.models.enums.OrderSate;
import org.example.client.models.enums.Payment;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Date;


public class Order {

    private final Integer id;
    private final Float total;
    private final Payment payment;
    private final Date deliveryStart;
    private final Date deliveryEnd;
    private final OrderSate state;
    private final String address;

    public Order(Integer id, Float total, Payment payment, Date deliveryStart, Date deliveryEnd, OrderSate state,
                 String address) {
        this.id = id;
        this.total = total;
        this.payment = payment;
        this.deliveryStart = deliveryStart;
        this.deliveryEnd = deliveryEnd;
        this.state = state;
        this.address = address;
    }

    public Order(@NotNull JSONObject json) {
        this(json.getInt("id"),
                json.getFloat("total"),
                Payment.values()[json.getInt("payment")],
                new java.util.Date(json.getLong("deliveryStart")),
                new java.util.Date(json.getLong("deliveryEnd")),
                OrderSate.values()[json.getInt("state")],
                json.getString("address"));
    }

    public Integer getId() {
        return id;
    }

    public Float getTotal() {
        return total;
    }

    public Payment getPayment() {
        return payment;
    }

    public Date getDeliveryStart() {
        return deliveryStart;
    }

    public Date getDeliveryEnd() {
        return deliveryEnd;
    }

    public OrderSate getState() {
        return state;
    }

    public String getAddress() {
        return address;
    }
}
