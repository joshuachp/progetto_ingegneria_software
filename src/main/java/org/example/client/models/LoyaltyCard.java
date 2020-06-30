package org.example.client.models;

import org.json.JSONObject;

import java.util.Date;

public class LoyaltyCard {

    private final Integer cardNumber;
    private final Date emissionDate;
    private final Integer points;

    public LoyaltyCard(JSONObject json) {
        this.cardNumber = json.getInt("card_number");
        this.emissionDate = new Date(json.getInt("emission_date"));
        this.points = json.getInt("points");
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public Date getEmissionDate() {
        return emissionDate;
    }

    public Integer getPoints() {
        return points;
    }

}
