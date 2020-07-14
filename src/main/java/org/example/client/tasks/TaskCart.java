package org.example.client.tasks;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.client.controllers.CartItemController;
import org.example.client.utils.Session;

import java.util.List;
import java.util.stream.Collectors;

public class TaskCart extends Task<List<Node>> {

    private final Stage stage;

    public TaskCart(Stage stage) {
        this.stage = stage;
    }

    @Override
    protected List<Node> call() {
        Session session = Session.getInstance();
        return session.getMapProducts().values().stream()
                .map(product -> CartItemController.createView(this.stage, product))
                .collect(Collectors.toList());
    }

}
