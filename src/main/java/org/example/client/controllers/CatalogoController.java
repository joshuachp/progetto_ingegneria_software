package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.client.models.Category;
import org.example.client.models.Prodotto;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CatalogoController {

    public ChoiceBox CbxColumn;
    public TextField Search;
    public ListView<String> listCategory;
    //public GridPane gridpane;
    public FlowPane flowpane;
    private Stage stage;

    public static ObservableList<String> categoryList;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(CatalogoController.class.getResource("/views/catalogo.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Catalogo");
        stage.show();
        CatalogoController catalogoController = loader.getController();
        categoryList = FXCollections.observableArrayList();
        for(Category x : Category.values()){
            categoryList.add(x.toString());
        }

        // prodotti Test
        ObservableList<Prodotto> products =  FXCollections.observableArrayList(
                new Prodotto(1, "Pasta n10", "Barilla", 1,
                        1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1, "Pasta", "Alimneti" ),
                new Prodotto(2, "Formaggio grattuggiato", "Parmiggiano Reggiano", 1,
                        3.50, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/934922.jpg", 1,
                        "Formaggi", "Alimneti" ),
                new Prodotto(1, "Pasta n10", "Barilla", 1,
                        1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1, "Pasta", "Alimneti" ),
                new Prodotto(1, "Pasta n10", "Barilla", 1,
                        1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1, "Pasta", "Alimneti" ),
                new Prodotto(1, "Pasta n10", "Barilla", 1,
                        1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1, "Pasta", "Alimneti" ),
                new Prodotto(1, "Pasta n10", "Barilla", 1,
                        1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1, "Pasta", "Alimneti" ),
                new Prodotto(1, "Pasta n10", "Barilla", 1,
                        1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1, "Pasta", "Alimneti" ),
                new Prodotto(1, "Pasta n10", "Barilla", 1,
                        1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1, "Pasta", "Alimneti" ),
                new Prodotto(1, "Pasta n10", "Barilla", 1,
                        1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                        "Pasta", "Alimneti" ));

        int i = 0;
        for( Prodotto x : products){
            catalogoController.cardGenerator(products.get(i), i);
            i++;
        }

        /*ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        catalogoController.gridpane.getColumnConstraints().addAll(column1, column1, column1);*/

        catalogoController.listCategory.setItems(categoryList);

        catalogoController.setStage(stage);

    }

    // Card builder
    public void cardGenerator(@NotNull Prodotto product, int i)  {

        VBox card = null;
        try {
            card = CardController.generateCard(product);
        } catch (IOException e) {
            e.printStackTrace();
        }

        flowpane.setMargin(card, new Insets(5));
        // gridpane.add(card, i % 3, i / 3);
        ObservableList<Node> list = flowpane.getChildren();
        list.add(card);

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }

    public void handleBackAction(ActionEvent actionEvent) {
    }

    public void searchHandler(ActionEvent actionEvent) {
    }

    public void backhandler(MouseEvent mouseEvent) {
    }

    public void viewCartHandler(MouseEvent mouseEvent) {
    }

    public void changeCategoryHandler(MouseEvent mouseEvent) {
    }
}
