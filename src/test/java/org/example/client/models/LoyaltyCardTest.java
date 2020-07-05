package org.example.client.models;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoyaltyCardTest {

    @Test
    void constructorJSON() {
        AtomicReference<LoyaltyCard> atomicReference = new AtomicReference<>();
        JSONObject json = new JSONObject().put("card_number", 1234).put("emission_date", 0).put("points", 500);

        assertDoesNotThrow(() -> atomicReference.set(new LoyaltyCard(json)));

        LoyaltyCard card = atomicReference.get();
        assertEquals(1234, card.getCardNumber());
        assertEquals(new Date(0), card.getEmissionDate());
        assertEquals(500, card.getPoints());
    }

}