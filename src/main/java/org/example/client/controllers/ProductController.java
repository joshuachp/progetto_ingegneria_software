package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.client.components.QuantitySpinnerFactory;
import org.example.client.models.Product;
import org.example.client.tasks.TaskLoadImage;
import org.example.client.utils.Session;

import java.io.IOException;

public class ProductController {

    private final QuantitySpinnerFactory spinnerFactory = new QuantitySpinnerFactory();
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
    @FXML
    public Spinner<Integer> quantitySpinner;
    @FXML
    public Button cartButton;

    // Instance of Product or a reference to a product in the session cart products
    private Product product;
    private Stage stage;

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
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        ProductController controller = loader.getController();
        controller.setStage(stage);

        stage.setTitle(product.getName());
        stage.setScene(scene);
        stage.initOwner(patentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);

        controller.setProduct(product);

        stage.showAndWait();
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product.getImage() != null) {
            setImage(product.getImage());
        }
        this.nameText.setText(product.getName());
        this.brandText.setText(product.getBrand());
        this.characteristicsText.setText(product.getCharacteristics());
        this.packageSizeText.setText(product.getPackageSize().toString());
        this.priceText.setText(String.format("%.2f", product.getPrice()));

        this.quantitySpinner = spinnerFactory.getSpinner(this.product, this.quantitySpinner);

        if (product.getAvailability() == 0 || quantitySpinner.getValue() == 0)
            cartButton.setDisable(true);
    }

    private void setImage(String image) {
        TaskLoadImage task = new TaskLoadImage(image);
        task.setOnSucceeded((event -> {
            Image img = task.getValue();
            this.imageView.setImage(img);
        }));
        new Thread(task).start();
    }

    @FXML
    public void handleAddToCartAction() {
        if (quantitySpinner.getValue() > 0) {
            // The product is a reference to the session product so the quantity doesn't need to be modified
            Session session = Session.getInstance();
            session.addProduct(this.product, quantitySpinner.getValue());

            this.quantitySpinner = spinnerFactory.getSpinner(this.product, this.quantitySpinner);

            if (quantitySpinner.getValue() == 0)
                cartButton.setDisable(true);
        }
    }

    public void handleCloseAction() {
        this.stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
