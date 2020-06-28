package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.example.client.models.FactoryUtente;
import org.example.client.models.Pagamento;
import org.example.client.models.Utente;
import org.example.client.utils.Utils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegistrazioneController /*implements Initializable*/ {

    @FXML
    public ComboBox<String> cbxPagamento;
    private Integer paymentMethod;
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
    public TextField Phone;
    @FXML
    private TextField Fidelity;
    @FXML
    private Label resultLabel;

    private Stage stage;

    public static void showView(Stage stage) {

        FXMLLoader loader = new FXMLLoader(SceltaModalitaController.class.getResource("/views/registrazione.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;

        // ComboBox
        ObservableList<String> payment = FXCollections.observableArrayList(
                Pagamento.CARTADICREDITO.toString(), Pagamento.CONTANTI.toString(), Pagamento.PAYPAL.toString());

        RegistrazioneController registrazioneController = loader.getController();
        registrazioneController.cbxPagamento.setItems(payment);
        registrazioneController.cbxPagamento.setValue(Pagamento.PAYPAL.toString());

        stage.show();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Registrazione");
        registrazioneController.setStage(stage);

    }

    /*
    **
    * Override of initialize method invoked by load.
    * Implementation of payment enum in a ComboBox.
     */
    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxPagamento.getItems().addAll(payment);
        // TODO: set prefer method
        cbxPagamento.setValue(Pagamento.PAYPAL.toString());
    }*/

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void handlerSetPaymentAction(ActionEvent actionEvent) {
        this.paymentMethod = Pagamento.fromString(cbxPagamento.getValue()).ordinal();;
    }

    public void handleBackAction(ActionEvent actionEvent) {
        AutenticazioneController.showView(this.stage);
    }


    public boolean passwordVerify(String password){
        return (Pattern.matches("[!-}]{8,16}", password.trim()));
    }

    public boolean passwordVerifyEquals(String password, String passwordRepeat){
        return(password.trim().equals(passwordRepeat.trim()));
    }

    public boolean mailVerify(String email) {
        return (Pattern.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", email));
    }

    public boolean capVerify(String cap){
        return(Pattern.matches("\\d{5}", cap));
    }

    public boolean phoneVerify(String phone){
        return(Pattern.matches(
                "^(\\((00|\\+)39\\)|(00|\\+)39)?" +
                        "(38[890]|" +
                        "34[6-90]|" +
                        "36[680]|" +
                        "33[3-90]|" +
                        "32[89])" +
                        "\\d{7}$",
                phone));
    }

    public boolean errorMessage(String message, @Nullable TextField field){
        resultLabel.setText(message);
        resultLabel.setTextFill(Paint.valueOf("red"));
        if(field != null)
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

        if (Name.getText().equals(""))
            error = errorMessage("Il campo Nome è vuoto.",Name);

        if (Surname.getText().equals(""))
            error = errorMessage("Il campo Cognome è vuoto.",Surname);

        if (Address.getText().equals(""))
            error = errorMessage("Il campo Indirizzo è vuoto.",Address);

        // Verifica CAP
        if (!CAP.getText().equals("")) {
            if (!capVerify(CAP.getText()))
                error = errorMessage("Il CAP deve essere di 5 cifre.", CAP);
        } else
            error = errorMessage("Il campo CAP è vuoto.", CAP);

        if (City.getText().equals(""))
            error = errorMessage("Il campo Città è vuoto.",City);

        if (!Phone.getText().equals("")){
            if(!phoneVerify(Phone.getText()))
                error = errorMessage("Il numero di telefono dato non è valido.",Phone);
        } else
            error = errorMessage("Il campo Telefono è vuoto.",Phone);

        if (Email.getText().equals(""))
            if (!mailVerify(Email.getText())){
                error = errorMessage("La mail non è coretta.", Email);
            }
            error = errorMessage("Il campo E-mail è vuoto.",Email);

        // Verifica lunghezza password
        if(!Password.getText().equals("")){
            if (!passwordVerify(Password.getText()))
                error = errorMessage("La lunghezza della password deve essere di almeno 8 caratteri.",Password);
        } else
            error = errorMessage("Il campo password è vuoto.",Password);

        // Verifica uguaglianza password
        if (!PasswordRepeat.getText().equals("")) {
            if (!passwordVerifyEquals(Password.getText(), PasswordRepeat.getText()))
                error = errorMessage("Le password devono coincidere.", PasswordRepeat);
        } else
            error = errorMessage("Il campo Ripeti password è vuoto.", PasswordRepeat);

        if(error)
            return;
        else{
            int statusCode = Utils.registerClient(Email.getText(), PasswordRepeat.getText(), Name.getText(), Surname.getText(),
                Address.getText(), Integer.valueOf(CAP.getText()), City.getText(), Phone.getText(),
                Pagamento.fromString(cbxPagamento.getValue()).ordinal());
           if( statusCode == 200){
               errorMessage("Success" + statusCode, null );
               Utente utente = new FactoryUtente().getUtente(Email.getText(), PasswordRepeat.getText());
               CatalogoController.showView(this.stage);
           } else {
               errorMessage("Error" + statusCode, null );
           }
        }

    }

}
