package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.client.models.Category;
import org.example.client.models.Prodotto;

import java.io.IOException;


public class CardController {
    public static VBox staticCard;
    public ImageView thumbnail;
    public Text title;
    public Text price;
    public Spinner quantity;
    public ImageView addCart;

    public VBox generateCard(Prodotto product) throws IOException {
        VBox card = FXMLLoader.load(getClass().getResource("/views/card.fxml"));
        price.setText(product.getPrice());
        title.setText(product.getName());
        Image image = new Image(product.getImage());
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        Rectangle2D viewportRect = new Rectangle2D(0, 0, 1000, 1000);
        imageView.setViewport(viewportRect);

        return card;
    }
}
