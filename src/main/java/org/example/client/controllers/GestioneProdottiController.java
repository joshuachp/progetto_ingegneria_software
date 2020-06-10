package org.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.client.models.Prodotto;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GestioneProdottiController {

    public TextField fieldName;
    public TextField fieldBrand;
    public TextField fieldID;
    public TextField fieldPackage;
    public TextField fieldPrice;
    public TextField fieldQuantity;
    public Label resultLabel;
    public ImageView thumbnail;

    private Stage stage = new Stage();

    Prodotto product;

    public void showView(@Nullable Prodotto product, boolean modify) throws IOException  {

        FXMLLoader loader = new FXMLLoader(GestioneProdottiController.class.getResource("/views/gestione-prodotti.fxml"));
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

        gestioneProdottiController.product = product;

        if (modify){
            assert product != null;
            stage.setTitle("Modifica prodotto " + product.getID());
            gestioneProdottiController.fieldBrand.setText(product.getBrand());
            gestioneProdottiController.fieldName.setText(product.getName());
            gestioneProdottiController.fieldID.setText(product.getID().toString());
            gestioneProdottiController.fieldPackage.setText(product.getPackage_size().toString());
            gestioneProdottiController.fieldPrice.setText(product.getPrice().toString());
            gestioneProdottiController.fieldQuantity.setText(product.getAvailability());
        } else {
            gestioneProdottiController.stage.setTitle("Crea nuovo prodotto");
        }

        stage.show();
    }

    public void handleConfirmAction(ActionEvent actionEvent) {
    }

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
