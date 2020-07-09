package org.example.client.models;

import org.example.client.models.enums.OrderSate;
import org.example.client.models.enums.Payment;
import org.json.JSONObject;

import java.util.Date;


public class Order {

    private final Integer id;
    private final Float total;
    private final Payment payment;
    private final Date deliveryStart;
    private final Date deliveryEnd;
    private final OrderSate state;


    public Order(Integer id, Float total, Payment payment, Date deliveryStart, Date deliveryEnd, OrderSate state) {
        this.id = id;
        this.total = total;
        this.payment = payment;
        this.deliveryStart = deliveryStart;
        this.deliveryEnd = deliveryEnd;
        this.state = state;
    }

    public Order(JSONObject json) {
        this(json.getInt("id"),
                json.getFloat("total"),
                Payment.values()[json.getInt("payment")],
                new java.util.Date(json.getLong("deliveryStart")),
                new java.util.Date(json.getLong("deliveryEnd")),
                OrderSate.values()[json.getInt("state")]);
    }
}
