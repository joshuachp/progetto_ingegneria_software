package org.example.client.tasks;

import javafx.concurrent.Task;
import javafx.scene.Node;
import org.example.client.components.OrderProductFactory;
import org.example.client.models.OrderItem;
import org.example.client.utils.Utils;

import java.util.List;

public class TaskOrderHistoryItem extends Task<List<Node>> {

    private final String session;
    private final Integer orderId;

    public TaskOrderHistoryItem(String session, Integer orderId) {
        this.session = session;
        this.orderId = orderId;
    }

    @Override
    protected List<Node> call() throws Exception {
        List<OrderItem> list = Utils.getOrderItems(this.session, this.orderId);
        return new OrderProductFactory().getOrderProductList(list);
    }

}
