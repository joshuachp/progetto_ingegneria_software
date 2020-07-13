package org.example.client.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
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
    private boolean modify;
    private ObservableList<Product> products;

    public static void showView(Stage parentStage, @Nullable Product product, boolean modify,
                                ObservableList<Product> products) {

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
        gestioneProdottiController.setProducts(products);
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
            Image image = new Image(product.getImage());
            this.thumbnail.setImage(image);

        }
        this.modify = modify;

    }

    private void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }


    public boolean errorMessage(String message, @Nullable TextField field) {
        resultLabel.setText(message);
        resultLabel.setTextFill(Paint.valueOf("red"));
        if (field != null)
            field.setStyle("-fx-border-color: red");
        return true;
    }

    private void resetErrorMessage() {
        fieldName.setStyle(null);
        fieldBrand.setStyle(null);
        fieldPrice.setStyle(null);
        fieldQuantity.setStyle(null);
        fieldCharacteristics.setStyle(null);
        fieldPackage.setStyle(null);
        fieldSection.setStyle(null);
    }

    @FXML
    public void handleConfirmAction() {
        boolean error = false;
        resetErrorMessage();

        if (fieldName.getText().equals(""))
            error = errorMessage("Il campo Nome è vuoto.", fieldName);

        if (fieldBrand.getText().equals(""))
            error = errorMessage("Il campo Brand è vuoto.", fieldBrand);

        if (fieldPrice.getText().equals(""))
            error = errorMessage("Il campo Prezzo è vuoto.", fieldPrice);
        else {
            try {
                Double.valueOf(fieldPrice.getText().replace(",", "."));
            } catch (NumberFormatException e) {
                error = errorMessage("Inserire il prezzo in modo corretto (es. 12,99).", fieldPrice);
            }
        }

        if (fieldQuantity.getText().equals(""))
            error = errorMessage("Il campo Prezzo è vuoto.", fieldQuantity);
        else {
            try {
                Integer.valueOf(fieldQuantity.getText());
            } catch (NumberFormatException e) {
                error = errorMessage("La quantità non è corretta.", fieldQuantity);
            }
        }

        if (fieldPackage.getText().equals(""))
            error = errorMessage("Il campo Package è vuoto.", fieldPackage);
        else {
            try {
                Integer.valueOf(fieldPackage.getText());
            } catch (NumberFormatException e) {
                error = errorMessage("La quantità della confezione singola non è corretta.", fieldPackage);
            }
        }

        if (fieldCharacteristics.getText().equals(""))
            error = errorMessage("Il campo Caratteristiche è vuoto.", fieldCharacteristics);

        if (thumbnail.getImage().getUrl().equals(""))
            error = true;

        if (fieldSection.getText().equals(""))
            error = errorMessage("Il campo Sezione è vuoto.", fieldSection);

        if (!error) {
            Product product = new Product(null, fieldName.getText(), fieldBrand.getText(),
                    Integer.parseInt(fieldPackage.getText()),
                    Float.parseFloat(fieldPrice.getText().replace(",", ".")), thumbnail.getImage().getUrl(),
                    Integer.parseInt(fieldQuantity.getText()), fieldCharacteristics.getText(), fieldSection.getText());
            Session session = Session.getInstance();
            if (!this.modify) {
                List<Product> products = new ArrayList<>();
                products.add(product);
                System.out.println(product);
                try {
                    Utils.createProduct(session.getUser().getSession(), products);
                } catch (Exception e) {
                    e.printStackTrace();
                    //TODO: Error Alert
                }
            } else {
                try {
                    Utils.updateProduct(session.getUser().getSession(), this.product.getId(), product);
                } catch (Exception e) {
                    e.printStackTrace();
                    //TODO: Error Alert
                }
            }
            try {
                ProductListController.refresh(products);
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
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
