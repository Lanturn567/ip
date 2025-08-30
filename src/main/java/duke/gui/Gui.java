package duke.gui;

import duke.util.Chatbot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {
    private static final String BOT_NAME = "Lanturn";
    private static final double WINDOW_WIDTH = 400.0;
    private static final double WINDOW_HEIGHT = 600.0;

    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/view/main-window.fxml"));

            // Load the FXML and get the controller
            MainWindow mainWindow = new MainWindow();
            fxmlLoader.setController(mainWindow);

            // Load the scene
            Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);

            // Configure the stage
            stage.setTitle("Lanturn");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Fallback to programmatic UI if FXML fails
            fallbackToProgrammaticUI(stage);
        }
    }

    /**
     * Fallback method to create UI programmatically if FXML loading fails
     */
    private void fallbackToProgrammaticUI(Stage stage) {
        // You can keep your original programmatic UI creation here as backup
        // This would be similar to your original start() method
        System.err.println("FXML loading failed, falling back to programmatic UI");

        // Create a simple backup UI
        try {
            MainWindow mainWindow = new MainWindow();
            Scene scene = new Scene(mainWindow.getMainLayout(), WINDOW_WIDTH, WINDOW_HEIGHT);
            stage.setScene(scene);
            stage.show();
            mainWindow.initialize(); // Manually call initialize for fallback
        } catch (Exception e) {
            System.err.println("Fallback UI also failed: " + e.getMessage());
        }
    }
}