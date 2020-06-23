package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    public GridPane gridpane;
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
        catalogoController.listCategory.setEditable(true);
        for(Category x : Category.values()){
            categoryList.add(x.toString());
        }


        // Test
        ObservableList<Prodotto> products =  FXCollections.observableArrayList(
                new Prodotto(1, "Pasta n10", "Barilla", 1,
                        1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1, "Pasta", "Alimneti" ),
                new Prodotto(2, "Formaggio grattuggiato", "Parmiggiano Reggiano", 1,
                        3.50, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/934922.jpg", 1, "Formaggi", "Alimneti" ));

        int i = 0;
        for( Prodotto x : products){
            catalogoController.cardGenerator(products.get(i), i+1);
            i++;
        }

        catalogoController.gridpane.setGridLinesVisible(true);

        catalogoController.listCategory.setItems(categoryList);
        catalogoController.setStage(stage);
    }

    // Card builder
    public void cardGenerator(@NotNull Prodotto product, int i)  {

        CardController cardController = new CardController();
        VBox card = null;
        try {
            card = cardController.generateCard(product);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gridpane.add(card, i % 3, i / 3);

        /*Image image;
        if (product.getImage() != null)
            image = new Image(product.getImage());
        else
            image = new Image("https://bitsofco.de/content/images/2018/12/Screenshot-2018-12-16-at-21.06.29.png");

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(100);

        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        Rectangle2D viewportRect = new Rectangle2D(0, 0, 1000, 1000);
        imageView.setViewport(viewportRect);

        Text text = new Text(product.getName());

        Text price = new Text(product.getPrice());
        price.setFill(Paint.valueOf("red"));

        Spinner<Integer> spinner = new Spinner<>();
        spinner.setEditable(true);
        spinner.setMaxWidth(50);

        Image cart = new Image("/image/shopping-cart.png");
        ImageView cartImage = new ImageView(cart);
        Rectangle2D viewportRectCart = new Rectangle2D(0, 0, 1000, 1000);
        cartImage.setViewport(viewportRect);
        cartImage.setFitWidth(20);
        cartImage.setFitHeight(20);
        Button button = new Button("", cartImage);
        button.setAlignment(Pos.CENTER);
        button.setPadding(new Insets(0, 10, 0, 10));

        Tooltip tooltip = new Tooltip("Aggiungi al carrello");
        button.setTooltip(tooltip);
        HBox addToCart = new HBox(spinner, button);

        VBox card = new VBox(imageView, text, price, addToCart);
        card.setAlignment(Pos.CENTER);
        gridpane.add(card, i % 3, i / 3);*/


    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }

    public void handleBackAction(ActionEvent actionEvent) {
    }
}
