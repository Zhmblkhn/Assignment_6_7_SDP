package ui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameWindow {
    private final Stage stage;
    private final GameCanvas canvas;

    public GameWindow(Stage stage, String heroType) {
        this.stage = stage;
        this.canvas = new GameCanvas(heroType, stage);

        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Hero Battle Game");
        stage.setScene(scene);
        stage.setResizable(false);

        InputHandler handler = new InputHandler(canvas);
        scene.setOnKeyPressed(handler::onKeyPressed);
        scene.setOnKeyReleased(handler::onKeyReleased);
    }

    public void show() {
        stage.show();
        canvas.startGameLoop();
    }
}
