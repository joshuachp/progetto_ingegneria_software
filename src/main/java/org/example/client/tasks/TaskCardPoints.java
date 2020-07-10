package org.example.client.tasks;

import javafx.concurrent.Task;
import okhttp3.Response;
import org.example.client.models.Client;
import org.example.client.models.LoyaltyCard;
import org.example.client.utils.Utils;
import org.json.JSONObject;

import java.util.Objects;

public class TaskCardPoints extends Task<LoyaltyCard> {

    private final Client client;

    public TaskCardPoints(Client client) {
        this.client = client;
    }

    @Override
    protected LoyaltyCard call() throws Exception {
        Response response = Utils.getLoyaltyCard(client.getSession(), client.getCardNumber());
        if (response != null && response.body() != null) {
            if (response.code() == 200) {
                // Success return card
                return new LoyaltyCard(new JSONObject(Objects.requireNonNull(response.body()).string()));
            } else {
                // Return error in request
                updateMessage(Objects.requireNonNull(response.body()).string());
                failed();
            }
        } else {
            updateMessage("Error in card information request.");
        }
        return null;
    }

}
