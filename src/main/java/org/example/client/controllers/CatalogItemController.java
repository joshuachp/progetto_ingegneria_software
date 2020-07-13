package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.client.components.QuantitySpinnerFactory;
import org.example.client.models.ROProduct;
import org.example.client.tasks.TaskLoadImage;
import org.example.client.utils.Session;

import java.io.IOException;


public class CatalogItemController {

    private final QuantitySpinnerFactory spinnerFactory = new QuantitySpinnerFactory();
    @FXML
    public ImageView thumbnail;
    @FXML
    public Text title;
    @FXML
    public Text price;
    @FXML
    public Spinner<Integer> quantity;
    @FXML
    public ImageView cartImage;
    @FXML
    public Button cartButton;
    private ROProduct product;
    private Stage stage;

    public static VBox createCard(Stage stage, ROProduct product) {
        FXMLLoader loader = new FXMLLoader(CatalogItemController.class.getResource("/views/catalog-item.fxml"));
        VBox card = null;
        try {
            card = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CatalogItemController controller = loader.getController();
        // Set product for controller
        controller.setProduct(product);
        controller.setStage(stage);
        return card;
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleAddToCartAction() {
        if (quantity.getValue() > 0) {
            // The product is a reference to the session product so the quantity doesn't need to be modified
            Session session = Session.getInstance();
            session.addProduct(this.product, quantity.getValue());

            this.quantity = spinnerFactory.getSpinner(this.product, this.quantity);

            if (quantity.getValue() == 0)
                cartButton.setDisable(true);
        }
    }

    public void setProduct(ROProduct product) {
        this.product = product;

        price.setText(String.format("\u20ac %.2f", this.product.getPrice()));
        title.setText(this.product.getName());
        if (this.product.getImage() != null) {
            setImage(this.product.getImage());
        }

        this.quantity = spinnerFactory.getSpinner(this.product, this.quantity);

        if (product.getAvailability() == 0 || quantity.getValue() == 0)
            cartButton.setDisable(true);
    }

    private void setImage(String image) {
        TaskLoadImage task = new TaskLoadImage(image);
        task.setOnSucceeded((event -> {
            Image img = task.getValue();
            this.thumbnail.setImage(img);
        }));
        new Thread(task).start();
    }


    public void handleImageMouseClicked() {
        ProductController.showView(this.stage, product);
    }
}
