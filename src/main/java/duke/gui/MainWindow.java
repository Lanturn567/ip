package duke.gui;

import duke.util.Chatbot;
import duke.util.Ui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class MainWindow {
    private static final String BOT_NAME = "Lanturn";

    private final Image userImage = new Image(
            this.getClass().getResourceAsStream("/images/furina.jpeg"), 50, 50, false, true);
    private final Image dukeImage = new Image(
            this.getClass().getResourceAsStream("/images/sui.jpg"), 50, 50, false, true);

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Label titleLabel;
    @FXML
    private HBox inputBar;
    @FXML
    private VBox mainLayout;

    private Chatbot lanturn = new Chatbot(BOT_NAME);

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // Add welcome message
        DialogBox welcomeBox = DialogBox.getDukeDialog(Ui.showWelcome(BOT_NAME), dukeImage);
        dialogContainer.getChildren().add(welcomeBox);

        // Auto-scroll to bottom when new messages are added
        dialogContainer.heightProperty().addListener(
                (observable) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText().trim();
        if (userText.isEmpty()) {
            return; // Don't process empty messages
        }

        String dukeText = lanturn.getResponse(userText);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getDukeDialog(dukeText, dukeImage)
        );
        userInput.clear();
    }

    /**
     * Handles button hover effect
     */
    @FXML
    private void handleButtonHover() {
        sendButton.setStyle(
                "-fx-background-color: #b388eb;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 18;" +
                        "-fx-padding: 6 14;" +
                        "-fx-cursor: hand;"
        );
    }

    /**
     * Handles button exit effect
     */
    @FXML
    private void handleButtonExit() {
        sendButton.setStyle(
                "-fx-background-color: #cba4f0;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 18;" +
                        "-fx-padding: 6 14;" +
                        "-fx-cursor: hand;"
        );
    }

    /**
     * Gets the main layout for this controller
     */
    public VBox getMainLayout() {
        return mainLayout;
    }
}