package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.client.models.Product;

import java.io.IOException;

public class ProductController {

    @FXML
    public Text nameText;
    @FXML
    public Text brandText;
    @FXML
    public Text characteristicsText;
    @FXML
    public Text packageSizeText;
    @FXML
    public Text priceText;
    @FXML
    public ImageView imageView;

    // Instance of Product or a reference to a product in the session cart products
    private Product product;

    /**
     * Create a Modal view of the product information, it will make the parent stage wait.
     *
     * @param patentStage The parent stage owning this view
     * @param product     The product to view
     */
    public static void showView(Stage patentStage, Product product) {
        FXMLLoader loader = new FXMLLoader(ProductController.class.getResource("/views/product.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;

        ProductController productController = loader.getController();
        productController.setProduct(product);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initOwner(patentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product.getImage() != null)
            this.imageView.setImage(new Image(product.getImage()));
        this.nameText.setText(product.getName());
        this.brandText.setText(product.getBrand());
        this.characteristicsText.setText(product.getCharacteristics());
        this.packageSizeText.setText(product.getPackageSize().toString());
        this.priceText.setText(String.format("%.2f", product.getPrice()));
    }
}
