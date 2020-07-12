package org.example.client.components;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import org.example.client.models.Order;

public class TableCellOrderId extends TableCell<Order, Integer> {

    @Override
    protected void updateItem(Integer item, boolean empty) {
        if (empty) {
            setText("");
        } else {
            setText(item.toString());
            setAlignment(Pos.CENTER);
            setCursor(Cursor.HAND);
            setTextFill(Color.rgb(0, 102, 255));
            setUnderline(true);
        }
        super.updateItem(item, empty);
    }
}
