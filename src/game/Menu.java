package game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.GameWindow;

public class Menu {
    public static void show(Stage stage) {
        Label title = new Label("Hero Battle Game");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: white;");

        Button warriorBtn = new Button("Warrior");
        Button archerBtn = new Button("Archer");
        Button mageBtn = new Button("Mage");

        warriorBtn.setOnAction(e -> startGame(stage, "warrior"));
        archerBtn.setOnAction(e -> startGame(stage, "archer"));
        mageBtn.setOnAction(e -> startGame(stage, "mage"));

        VBox root = new VBox(15, title, warriorBtn, archerBtn, mageBtn);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(#222233, #111122); -fx-padding: 40;");

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private static void startGame(Stage stage, String heroType) {
        GameWindow window = new GameWindow(stage, heroType);
        window.show();
    }
}
