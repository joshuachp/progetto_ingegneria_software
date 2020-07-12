package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.client.models.Product;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageProductController {

    public TextField fieldName;
    public TextField fieldBrand;

    public TextField fieldPackage;
    public TextField fieldPrice;
    public TextField fieldQuantity;
    public Label resultLabel;
    public ImageView thumbnail;
    public TextField fieldCharacteristics;
    public TextField fieldSection;

    private Product product;
    private Stage stage;

    public static void showView(Stage parentStage, @Nullable Product product, boolean modify) {
        //TODO: field characteristics and section
        FXMLLoader loader =
                new FXMLLoader(ManageProductController.class.getResource("/views/manage-product.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ManageProductController gestioneProdottiController = loader.getController();
        Stage stage = new Stage();
        assert root != null;
        Scene scene = new Scene(root);

        stage.setScene(scene);
        if (modify) {
            assert product != null;
            stage.setTitle("Modifica prodotto " + product.getId());
        } else {
            stage.setTitle("Crea nuovo prodotto ");
        }

        gestioneProdottiController.setStage(stage);
        gestioneProdottiController.setProduct(product);
        gestioneProdottiController.setModify(modify);
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    private void setModify(boolean modify) {
        if (modify) {
            assert product != null;
            this.fieldBrand.setText(product.getBrand());
            this.fieldName.setText(product.getName());
            this.fieldPackage.setText(product.getPackageSize().toString());
            this.fieldPrice.setText(String.format("%.2f", product.getPrice()));
            this.fieldQuantity.setText(product.getAvailability().toString());
            this.fieldCharacteristics.setText(product.getCharacteristics());
            this.fieldSection.setText(product.getSection());
        }

    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleConfirmAction() {
        if (!(fieldBrand.getText().equals("") || thumbnail.getImage().getUrl().equals("") ||
                fieldPackage.getText().equals("") || fieldName.getText().equals("") ||
                fieldPrice.getText().equals("") || fieldQuantity.getText().equals("") ||
                fieldCharacteristics.getText().equals("") || fieldSection.getText().equals(""))) {

            Product product = new Product(fieldName.getText(),
                    fieldBrand.getText(), Integer.parseInt(fieldPackage.getText()),
                    Float.parseFloat(fieldPrice.getText().replace(",", ".")),
                    thumbnail.getImage().getUrl(), Integer.parseInt(fieldQuantity.getText()),
                    fieldCharacteristics.getText(), fieldSection.getText());
            Session session = Session.getInstance();
            List<Product> products = new ArrayList<>();
            products.add(product);
            System.out.println(product);
            try {
                Utils.createProduct(session.getUser().getSession(), products);
            } catch (Exception e) {
                e.printStackTrace();
                //TODO: Error Alert
            }
            stage.close();
        } else {
            resultLabel.setText("Si prega di riempire tutti i campi.");
        }

    }

    @FXML
    public void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Carica immagine");
        File file = fileChooser.showOpenDialog(stage);
        Session session = Session.getInstance();
        try {
            String imageLink = Utils.uploadProductImage(session.getUser().getSession(), file);
            Image image = new Image(imageLink);
            this.thumbnail.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
