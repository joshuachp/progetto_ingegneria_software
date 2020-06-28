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
    public TextField searchBar;
    public  ListView<String> listCategory;
    //public GridPane gridpane;
    public FlowPane flowpane;
    private Stage stage;

    // prodotti Test
    ObservableList<Prodotto> products =  FXCollections.observableArrayList(
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1, "Pasta", "Alimneti" ),
            new Prodotto(2, "Formaggio grattuggiato", "Parmiggiano Reggiano", 1,
                    3.50, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/934922.jpg", 1,
                    "Formaggi", "Alimenti" ),
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1, "Pasta", "Alimneti" ),
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Frutta e verdura" ),
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
                    "Pasta", "Alimenti" ));

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

        catalogoController.catalogFactory(catalogoController.products,
                catalogoController.listCategory,
                catalogoController.searchBar);

        /*ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        catalogoController.gridpane.getColumnConstraints().addAll(column1, column1, column1);*/

        catalogoController.listCategory.setItems(categoryList);
        System.out.println(catalogoController.listCategory.getItems().toString());
        catalogoController.setStage(stage);

    }

    // Card builder
    public void catalogFactory(@NotNull ObservableList<Prodotto> products, ListView<String> listCategory, TextField searchBar)  {
        // Creo la lista di figli del flow pane
        ObservableList<Node> list = flowpane.getChildren();
        list.clear();
        Category category =
                Category.fromString(listCategory.getSelectionModel().getSelectedItems().toString());
        String search = searchBar.getText();
        if (category == null)
            category = Category.ALIMENTI;
        System.out.println(category.toString());
        // Genero i figli sulla base della categoria selezionata e del testo di ricerca nel box di ricerca
        VBox card = null;
        for (Prodotto product: products){

            if(category == Category.fromString(product.getSection())) {
                if (product.getCharacteristics().contains(search)) {
                    try {
                        card = CardController.generateCard(product);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    list.add(card);
                    flowpane.setMargin(card, new Insets(5));
                }
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }

    public void handleBackAction(ActionEvent actionEvent) {
    }

    public void searchHandler(ActionEvent actionEvent) {
        catalogFactory(this.products, this.listCategory, this.searchBar);
    }

    public void backhandler(MouseEvent mouseEvent) {
    }

    public void viewCartHandler(MouseEvent mouseEvent) {
    }

    public void changeCategoryHandler(MouseEvent mouseEvent) {
        catalogFactory(this.products, this.listCategory, this.searchBar);
        System.out.println(Category.fromString(listCategory.getSelectionModel().getSelectedItems().toString()));
        System.out.println(listCategory.getItems().toString());
    }
}
