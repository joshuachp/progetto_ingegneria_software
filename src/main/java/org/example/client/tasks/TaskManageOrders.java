package org.example.client.tasks;

import javafx.concurrent.Task;
import org.example.client.models.Order;
import org.example.client.utils.Utils;

import java.util.List;

public class TaskManageOrders extends Task<List<Order>> {
    private final String session;

    public TaskManageOrders(String session) {
        this.session = session;
    }

    @Override
    protected List<Order> call() {
        try {
            return Utils.getAllOrders(this.session);
        } catch (Exception e) {
            e.printStackTrace();
            failed();
        }
        return null;
    }
}
