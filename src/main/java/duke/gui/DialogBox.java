package duke.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    private DialogBox(String message, Image img) {
        text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(250); // limit width for bubble effect

        displayPicture = new ImageView(img);
        displayPicture.setFitWidth(50);
        displayPicture.setFitHeight(50);
        displayPicture.setClip(new Circle(25, 25, 25)); // circular avatar

        this.setSpacing(10);
        this.setPadding(new Insets(5));

        this.getChildren().addAll(displayPicture, text);
    }

    /** User dialog bubble */
    public static DialogBox getUserDialog(String message, Image img) {
        DialogBox db = new DialogBox(message, img);

        db.setAlignment(Pos.TOP_RIGHT);
        db.getChildren().clear(); // reverse order for user
        db.getChildren().addAll(db.text, db.displayPicture);

        db.text.setStyle(
                "-fx-background-color: #ffc0cb;" + // pastel pink
                        "-fx-text-fill: #5e4b8b;" +       // deep purple text
                        "-fx-padding: 8 12;" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 14px;"
        );

        return db;
    }

    /** Bot dialog bubble */
    public static DialogBox getDukeDialog(String message, Image img) {
        DialogBox db = new DialogBox(message, img);

        db.setAlignment(Pos.TOP_LEFT);

        db.text.setStyle(
                "-fx-background-color: #d6b3ff;" + // pastel purple
                        "-fx-text-fill: #2c1a5e;" +       // darker purple text
                        "-fx-padding: 8 12;" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 14px;"
        );

        return db;
    }
}
