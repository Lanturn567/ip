package duke.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    private DialogBox(String message, Image img) {
        // Preconditions
        assert message != null : "Message should not be null";
        assert img != null : "Image should not be null";

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

        // Postconditions
        assert this.getChildren().contains(displayPicture);
        assert this.getChildren().contains(text);
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

        // Check correct order: text first, then displayPicture
        assert db.getChildren().get(0) == db.text : "Text should come before image in user dialog";
        assert db.getChildren().get(1) == db.displayPicture;

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

        // Check correct order: displayPicture first, then text
        assert db.getChildren().get(0) == db.displayPicture : "Image should come before text in Duke dialog";
        assert db.getChildren().get(1) == db.text;

        return db;
    }
}
