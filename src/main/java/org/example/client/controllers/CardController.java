package org.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.client.models.Product;

import java.io.IOException;


public class CardController {

    public ImageView thumbnail;
    public Text title;
    public Text price;
    public Spinner<Integer> quantity;
    public ImageView addCart;

    public static VBox generateCard(Product product) {
        FXMLLoader loader = new FXMLLoader(CardController.class.getResource("/views/card.fxml"));
        VBox card = null;
        try {
            card = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CardController cardController = loader.getController();
        cardController.price.setText(String.format("\u20ac %.2f", product.getPrice()));
        cardController.title.setText(product.getName());
        cardController.title.maxWidth(50);
        if (product.getImage() != null) {
            Image image = new Image(product.getImage());
            cardController.thumbnail.setImage(image);
        }
        /*cardController.thumbnail.setFitWidth(100);
        cardController.thumbnail.setPreserveRatio(true);
        cardController.thumbnail.setCache(true);
        Rectangle2D viewportRect = new Rectangle2D(0, 0, 1000, 1000);
        cardController.thumbnail.setViewport(viewportRect);*/
        SpinnerValueFactory<Integer> spinnerValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99);
        cardController.quantity.setValueFactory(spinnerValue);

        return card;
    }

    public void addToCartHandler(ActionEvent actionEvent) {
        // TODO: aggiunta prodotto carrello in tot quantità
    }
}
