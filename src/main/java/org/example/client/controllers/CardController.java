package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.client.models.Product;
import org.example.client.utils.Session;

import java.io.IOException;


public class CardController {

    @FXML
    public ImageView thumbnail;
    @FXML
    public Text title;
    @FXML
    public Text price;
    @FXML
    public Spinner<Integer> quantity;
    @FXML
    public ImageView addCart;

    private Product product;


    public static VBox generateCard(Product product) {
        FXMLLoader loader = new FXMLLoader(CardController.class.getResource("/views/card.fxml"));
        VBox card = null;
        try {
            card = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CardController controller = loader.getController();
        // Set product for controller
        controller.setProduct(product);
        return card;
    }

    @FXML
    public void handleAddToCartAction() {
        // TODO: quantity
        Session session = Session.getInstance();
        session.addProduct(this.product);
    }

    public void setProduct(Product product) {
        this.product = product;
        price.setText(String.format("\u20ac %.2f", this.product.getPrice()));
        title.setText(this.product.getName());
        if (this.product.getImage() != null) {
            thumbnail.setImage(new Image(this.product.getImage()));
        }
        SpinnerValueFactory<Integer> spinnerValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                product.getAvailability());
        quantity.setValueFactory(spinnerValue);
    }
}
