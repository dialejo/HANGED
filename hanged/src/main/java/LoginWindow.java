import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class LoginWindow {
    private Stage primaryStage;
    private Map<String, Integer> playerScores;

    public LoginWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.playerScores = new HashMap<>();
    }

    public void show() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        Label titleLabel = new Label("Hanged Game");
        titleLabel.setFont(Font.font(20));
        titleLabel.setTextAlignment(TextAlignment.CENTER);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (authenticateUser(username, password)) {
                int score = playerScores.getOrDefault(username, 0);
                HangmanGame game = new HangmanGame(username, score);
                game.start(primaryStage);
            } else {
                showError("Invalid credentials. Please try again.");
            }
        });

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (registerUser(username, password)) {
                showInfo("User registered successfully. You can now login.");
            } else {
                showError("Username already exists. Please choose a different username.");
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, registerButton);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setTitle("Hanged Game - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showError(String message) {
        // Show error message to the user
        System.out.println("Error: " + message);
    }

    private void showInfo(String message) {
        // Show information message to the user
        System.out.println("Info: " + message);
    }

    private boolean authenticateUser(String username, String password) {
        // Implement your authentication logic here
        // Return true if the credentials are valid, false otherwise
        return playerScores.containsKey(username) && playerScores.get(username).equals(password.hashCode());
    }

    private boolean registerUser(String username, String password) {
        // Implement your user registration logic here
        // Return true if the user is registered successfully, false otherwise

        // Check if the username already exists
        if (playerScores.containsKey(username)) {
            return false;
        }

        // Add the new user with the hashed password as the score
        playerScores.put(username, password.hashCode());
        return true;
    }
}