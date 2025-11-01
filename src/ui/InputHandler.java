package ui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {
    private final GameCanvas canvas;

    public InputHandler(GameCanvas canvas) {
        this.canvas = canvas;
    }

    public void onKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        switch (code) {
            case W -> canvas.setDirection("UP", true);
            case S -> canvas.setDirection("DOWN", true);
            case A -> canvas.setDirection("LEFT", true);
            case D -> canvas.setDirection("RIGHT", true);
            case SPACE -> canvas.setAttackPressed(true);
            case DIGIT1 -> canvas.setAttackStrategyIndex(1);
            case DIGIT2 -> canvas.setAttackStrategyIndex(2);
            case DIGIT3 -> canvas.setAttackStrategyIndex(3);
        }
    }

    public void onKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        switch (code) {
            case W -> canvas.setDirection("UP", false);
            case S -> canvas.setDirection("DOWN", false);
            case A -> canvas.setDirection("LEFT", false);
            case D -> canvas.setDirection("RIGHT", false);
            case SPACE -> canvas.setAttackPressed(false);
        }
    }
}
