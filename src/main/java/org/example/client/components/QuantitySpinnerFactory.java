package org.example.client.components;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.example.client.models.Product;
import org.jetbrains.annotations.NotNull;

public class QuantitySpinnerFactory {

    public Spinner<Integer> getSpinner(@NotNull Product product, @NotNull Spinner<Integer> quantity) {
        int max = product.getAvailability() - product.getQuantity();
        int min = max == 0 ? 0 : 1;
        SpinnerValueFactory<Integer> spinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max);
        quantity.setValueFactory(spinnerValueFactory);
        if (quantity.getValue() > max)
            spinnerValueFactory.setValue(max);
        return quantity;
    }
}
