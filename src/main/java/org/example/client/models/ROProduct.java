package org.example.client.models;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Read only product class. This class doesn't implement any setter methods.
 */
public class ROProduct extends AbstractProduct {

    public ROProduct(Integer id, String name, String brand, Integer packageSize, Float price, String image,
                     Integer availability, String characteristics, String section) {
        super(id, name, brand, packageSize, price, image, availability, characteristics, section);
    }

    public ROProduct(@NotNull JSONObject json) {
        super(json);
    }

}
