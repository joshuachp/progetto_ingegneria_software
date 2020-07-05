package org.example.client.utils;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Objects;

/**
 * Require an image asynchronously
 */
public class TaskLoadImage extends Task<Image> {

    private final String imageUrl;

    /**
     * @param imageUrl The URL of the image
     */
    public TaskLoadImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    protected Image call() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(this.imageUrl).build();
        Response response = client.newCall(request).execute();
        if (response.code() == 200 && response.body() != null &&
                Objects.requireNonNull(response.body()).contentLength() > 0) {
            return new Image(Objects.requireNonNull(response.body()).byteStream());
        }
        failed();
        return null;
    }
}
