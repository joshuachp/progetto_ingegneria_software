package org.example.client.tasks;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.client.components.OrderProductFactory;
import org.example.client.models.OrderItem;
import org.example.client.utils.Utils;

import java.util.List;

public class TaskOrderHistoryItem extends Task<List<Node>> {

    private final Stage stage;
    private final String session;
    private final Integer orderId;

    public TaskOrderHistoryItem(Stage stage, String session, Integer orderId) {
        this.stage = stage;
        this.session = session;
        this.orderId = orderId;
    }

    @Override
    protected List<Node> call() {
        List<OrderItem> list;
        try {
            list = Utils.getOrderItems(this.session, this.orderId);
        } catch (Exception e) {
            e.printStackTrace();
            failed();
            return null;
        }
        return new OrderProductFactory().getOrderProductList(stage, list);
    }

}
