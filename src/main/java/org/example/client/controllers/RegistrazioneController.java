package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.example.client.models.Cliente;
import org.example.client.models.Pagamento;
import org.example.client.models.StatoSpesa;
import org.example.client.utils.Utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class RegistrazioneController {

    @FXML
    private static ComboBox<String> cbxPagamento;
    @FXML
    private PasswordField PasswordRepeat;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField Email;
    @FXML
    private TextField Name;
    @FXML
    private TextField Surname;
    @FXML
    private TextField Address;
    @FXML
    private TextField CAP;
    @FXML
    private TextField City;
    @FXML
    private TextField Phone;
    @FXML
    private TextField Fidelity;
    @FXML
    private Label resultLabel;

    private Stage stage;

    public static void showView(Stage stage) {
        ObservableList<String> options = FXCollections.observableArrayList(
                        "Option1",
                        "Option2",
                        "Option3"
                        /*Pagamento.CARTADICREDITO.toString(),
                        Pagamento.PAYPAL.toString(),
                        Pagamento.CONTANTI.toString()*/
                );
        RegistrazioneController registrazioneController = new RegistrazioneController();
        cbxPagamento = new ComboBox<String>();

        cbxPagamento.getItems().addAll("1", "2", "3");
        //cbxPagamento.setValue("CARTADICREDITO");

        FXMLLoader loader = new FXMLLoader(SceltaModalitaController.class.getResource("/views/registrazione.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Registrazione");

        stage.show();
        RegistrazioneController controller = loader.getController();
        controller.setStage(stage);

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleBackAction(ActionEvent actionEvent) {
        AutenticazioneController.showView(this.stage);
    }

    public boolean handlePasswordField(ActionEvent actionEvent) {
        this.PasswordVerify();
        return true;
    }

    public boolean handlePasswordVerifyField(ActionEvent actionEvent) {
        return this.PasswordVerifyEquals();
    }

    public boolean PasswordVerify(){
        // TODO: verifica lunghezza + strip
        return false;
    }

    public boolean PasswordVerifyEquals(){
        // TODO: verifica uguaglianza tra password e passwordrepeat +strip
        return true;
    }

    public boolean handleCardField(ActionEvent actionEvent) {
        return true;
    }

    public boolean handlePhoneField(ActionEvent actionEvent) {
        return true;
    }

    private boolean capVerify(){
        // TODO: Verifica con \d5 della lunghezza del CAP + strip
        return false;
    }

    private boolean phoneVerify(){
        // TODO: Verifica con \d5 della lunghezza del CAP + strip
        return false;
    }

    public boolean handleCapField(ActionEvent actionEvent) {
        return capVerify();
    }

    private boolean errorMessage(String message, TextField field){
        resultLabel.setText(message);
        resultLabel.setTextFill(Paint.valueOf("red"));
        field.setStyle("-fx-border-color: red");
        return true;
    }

    private void resetErrorMessage(){
        Name.setStyle(null);
        Surname.setStyle(null);
        Phone.setStyle(null);
        Password.setStyle(null);
        PasswordRepeat.setStyle(null);
        CAP.setStyle(null);
        City.setStyle(null);
        Address.setStyle(null);
    }

    public void handleConfirmAction(ActionEvent actionEvent) {
        boolean error = false;
        resetErrorMessage();
        // Verifica lunghezza password
        if(Password.getText() != null){
            if (!PasswordVerify())
                error = errorMessage("La lunghezza della password deve essere di almeno 8 caratteri.",Password);
        } else
            error = errorMessage("Il campo password è vuoto.",Password);

        if (Name.getText() == null)
            error = errorMessage("Il campo Nome è vuoto.",Name);

        if (Surname.getText() == null)
            error = errorMessage("Il campo Cognome è vuoto.",Surname);

        // Verifica uguaglianza password
        if (PasswordRepeat.getText() != null) {
            if (!PasswordVerify())
                error = errorMessage("Le password devono coincidere.", PasswordRepeat);
        } else
            error = errorMessage("Il campo Ripeti password è vuoto.", PasswordRepeat);

        // Verifica CAP
        if (CAP.getText() != null) {
            if (!capVerify())
                error = errorMessage("Il CAP deve essere di 5 cifre.", CAP);
        } else
            error = errorMessage("Il campo CAP è vuoto.", CAP);

        if (City.getText() == null)
            error = errorMessage("Il campo Città è vuoto.",City);

        if (Phone.getText() != null){
            if(!phoneVerify())
                error = errorMessage("Il numero di telefono dato non è valido.",Phone);
        } else
            error = errorMessage("Il campo Telefono è vuoto.",Phone);

        if(error)
            return;

        Utils.RegisterClient(Email.getText(), PasswordRepeat.getText(), Name.getText(), Surname.getText(),
                Address.getText(), Integer.valueOf(CAP.getText()), City.getText(), Phone.getText(), 1);

    }
}
