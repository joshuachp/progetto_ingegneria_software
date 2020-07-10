package org.example.client.tasks;

import javafx.concurrent.Task;
import org.example.client.models.Product;
import org.example.client.utils.Utils;

public class TaskOrderProduct extends Task<Product> {

    private final String session;
    private final Integer productId;

    public TaskOrderProduct(String session, Integer productId) {
        this.session = session;
        this.productId = productId;
    }


    @Override
    protected Product call() throws Exception {
        return Utils.getProduct(this.session, this.productId);
    }
}
