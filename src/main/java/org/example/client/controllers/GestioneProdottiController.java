package org.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.client.models.Product;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GestioneProdottiController {

    public TextField fieldName;
    public TextField fieldBrand;

    public TextField fieldPackage;
    public TextField fieldPrice;
    public TextField fieldQuantity;
    public Label resultLabel;
    public ImageView thumbnail;
    public TextField fieldCharateristics;
    public TextField fieldSection;
    Product product;
    private Stage stage;
    private boolean modify;

    public static void showView(Stage stage, @Nullable Product product, boolean modify) throws IOException {

        //TODO: field charateristics and section
        FXMLLoader loader = new FXMLLoader(GestioneProdottiController.class.getResource("/views/gestione-prodotti" +
                ".fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GestioneProdottiController gestioneProdottiController = loader.getController();

        assert root != null;
        Scene scene = new Scene(root);

        stage.setScene(scene);
        if(modify) {
            assert product != null;
            stage.setTitle("Modifica prodotto " + product.getId());
        } else {
            stage.setTitle("Crea nuovo prodotto ");
        }

        gestioneProdottiController.product = product;
        gestioneProdottiController.setStage(stage);
        gestioneProdottiController.setModify(modify);

        stage.show();
    }

    private void setModify(boolean modify){
        if (modify) {
            assert product != null;
            this.fieldBrand.setText(product.getBrand());
            this.fieldName.setText(product.getName());
            this.fieldPackage.setText(product.getPackageSize().toString());
            this.fieldPrice.setText(String.format("%f.2", product.getPrice()));
            this.fieldQuantity.setText(product.getAvailability().toString());
            this.fieldCharateristics.setText(product.getCharacteristics());
            this.fieldSection.setText(product.getSection());
        }
        this.modify = modify;
    }

    private void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    public void handleConfirmAction(ActionEvent actionEvent) {
        if (!(fieldBrand.getText().equals("") || thumbnail.getImage().getUrl().equals("") ||
        fieldPackage.getText().equals("") || fieldName.getText().equals("") ||
        fieldPrice.getText().equals("")|| fieldQuantity.getText().equals("") ||
        fieldCharateristics.getText().equals("")   || fieldSection.getText().equals(""))) {
            product.setCharacteristics(fieldCharateristics.getText());
            product.setSection(fieldSection.getText());
            product.setBrand(fieldBrand.getText());
            product.setImage(thumbnail.getImage().getUrl());
            product.setAvailability(Integer.parseInt(fieldQuantity.getText()));
            product.setPrice(Float.valueOf(fieldPrice.getText()));
            product.setName(fieldName.getText());
            product.setPackageSize(Integer.parseInt(fieldPackage.getText()));
            stage.close();
        } else {
            resultLabel.setText("Si prega di riempire tutti i campi.");
        }

        // TODO: POST product to server
        System.out.println(product);

    }

    @FXML
    public void handleUploadImage(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Carica immagine");
        File file = fileChooser.showOpenDialog(stage);
        System.out.println(file.getPath());
        product.setImage(file.getPath());
        FileInputStream inputStream = new FileInputStream(product.getImage());
        Image image = new Image(inputStream);
        thumbnail.setImage(image);

    }
}
