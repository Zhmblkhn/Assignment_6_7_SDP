package ui;

import combat.MagicAttack;
import combat.MeleeAttack;
import combat.RangedAttack;
import entities.*;
import factories.EnemyFactory;
import factories.HeroFactory;
import javafx.stage.Stage;
import core.GameAnnouncer;
import core.GameLogger;
import core.Observer;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import upgrades.decorators.DamageBoostDecorator;
import upgrades.decorators.HealthBoostDecorator;
import upgrades.decorators.SpeedBoostDecorator;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class GameCanvas extends Canvas {
    private Hero hero;
    private final List<Enemy> enemies = new ArrayList<>();
    private int stageIndex = 1;

    private boolean up, down, left, right;
    private boolean attackPressed = false;
    private double attackCooldown = 0;

    private int attackStrategyIndex = 1;

    private final List<Observer> observers = new ArrayList<>();
    private boolean stopGameLoop = false;
    private Stage stage;

    public GameCanvas(String heroType, Stage stage) {
        super(Constants.WIDTH, Constants.HEIGHT);
        this.stage = stage;
        createHero(heroType);
        spawnLevel(stageIndex);

        observers.add(new GameLogger());
        observers.add(new GameAnnouncer());
        hero.addObserver((Consumer<String>) event -> notifyObservers(event));

        hero.setPosition(Constants.WIDTH / 2.0, Constants.HEIGHT / 2.0);
    }

    private void createHero(String type) {
        hero = HeroFactory.createHero(type);
        if (hero instanceof Warrior) {
            hero.setStrategy(new MeleeAttack());
        }
    }

    public void setDirection(String dir, boolean state) {
        switch (dir) {
            case "UP" -> up = state;
            case "DOWN" -> down = state;
            case "LEFT" -> left = state;
            case "RIGHT" -> right = state;
        }
    }

    public void setAttackPressed(boolean p) { attackPressed = p; }

    public void setAttackStrategyIndex(int idx) {
        if (hero instanceof Warrior) {
            attackStrategyIndex = 1;
            hero.setStrategy(new MeleeAttack());
            return;
        }

        if (idx < 1 || idx > 3) return;
        attackStrategyIndex = idx;

        switch (attackStrategyIndex) {
            case 1 -> hero.setStrategy(new MeleeAttack());
            case 2 -> hero.setStrategy(new RangedAttack());
            case 3 -> hero.setStrategy(new MagicAttack());
        }
    }

    public void startGameLoop() {
        GraphicsContext gc = getGraphicsContext2D();
        long[] last = {System.nanoTime()};
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (stopGameLoop) return;
                double dt = (now - last[0]) / 1e9;
                if (dt > 0.05) dt = 0.05;
                update(dt);
                render(gc);
                last[0] = now;
            }
        }.start();
    }

    private void update(double dt) {
        double dx = 0, dy = 0;
        double sp = hero.getSpeed();
        if (up) dy -= sp;
        if (down) dy += sp;
        if (left) dx -= sp;
        if (right) dx += sp;
        if (dx != 0 || dy != 0) hero.moveBy(dx, dy);

        if (attackCooldown > 0) attackCooldown -= dt;
        if (attackPressed && attackCooldown <= 0) {
            Enemy target = findNearestEnemy();
            if (target != null) {
                hero.attack(target);
                attackCooldown = 0.4;
            }
        }

        for (Enemy e : new ArrayList<>(enemies)) {
            if (!e.isAlive()) continue;
            double dist = Math.hypot(e.getX() - hero.getX(), e.getY() - hero.getY());

            if (dist < (e.getSize() / 2.0 + hero.getSize() / 2.0)) {
                if (e.tryHit()) {
                    hero.takeDamage(e.getDamage());
                    notifyObservers(hero.getName() + " takes " + (int)e.getDamage() + " from " + e.getName());
                }
            }

            e.updateTowards(hero, dt);

            if (e instanceof Dragon dragon) {
                dragon.updateRangedCooldown(dt);
                dragon.rangedAttack(hero);
            }
        }

        if (!hero.isAlive() && !stopGameLoop) {
            stopGameLoop = true;
            Platform.runLater(this::showGameOverScreen);
        }

        enemies.removeIf(e -> !e.isAlive());

        if (enemies.isEmpty() && hero.isAlive()) {
            if (stageIndex >= 5) {
                stopGameLoop = true;
                notifyObservers("VICTORY! You defeated all stages!");
                Platform.runLater(this::showVictoryScreen);
            } else {
                stageIndex++;
                applyRandomUpgrade();
                resetHeroHp();
                spawnLevel(stageIndex);
                notifyObservers("Proceed to stage " + stageIndex);
            }
        }
    }

    private void applyRandomUpgrade() {
        Random rnd = new Random();
        int choice = rnd.nextInt(3);

        switch (choice) {
            case 0 -> hero = new DamageBoostDecorator(hero, 1.2);
            case 1 -> hero = new HealthBoostDecorator(hero, 25);
            case 2 -> hero = new SpeedBoostDecorator(hero, 0.6);
        }

        notifyObservers("Applied upgrade via decorator!");
    }

    private void resetHeroHp() {
        hero.addMaxHp(0);
    }

    private Enemy findNearestEnemy() {
        Enemy best = null;
        double bestD = Double.MAX_VALUE;
        for (Enemy e : enemies) {
            if (!e.isAlive()) continue;
            double d = Math.hypot(e.getX() - hero.getX(), e.getY() - hero.getY());
            if (d < bestD) { best = e; bestD = d; }
        }
        return best;
    }

    private void spawnLevel(int idx) {
        enemies.clear();
        switch (idx) {
            case 1 -> enemies.add(EnemyFactory.createEnemy("goblin", 600, 300));
            case 2 -> enemies.add(EnemyFactory.createEnemy("minotaur", 600, 250));
            case 3 -> {
                enemies.add(EnemyFactory.createEnemy("griffin", 600, 220));
                enemies.add(EnemyFactory.createEnemy("goblin", 650, 340));
            }
            case 4 -> {
                enemies.add(EnemyFactory.createEnemy("hydra", 600, 260));
                enemies.add(EnemyFactory.createEnemy("minotaur", 650, 320));
            }
            case 5 -> {
                enemies.add(EnemyFactory.createEnemy("dragon", 700, 260));
                enemies.add(EnemyFactory.createEnemy("griffin", 650, 330));
            }
            default -> enemies.add(EnemyFactory.createEnemy("goblin", 600, 300));
        }
    }

    private void render(GraphicsContext gc) {
        gc.setFill(Color.web("#1e1e2f"));
        gc.fillRect(0, 0, getWidth(), getHeight());

        gc.setStroke(Color.web("#2a2a3a"));
        for (int x = 0; x < getWidth(); x += 50) gc.strokeLine(x,0,x,getHeight());
        for (int y = 0; y < getHeight(); y += 50) gc.strokeLine(0,y,getWidth(),y);

        hero.render(gc);
        for (Enemy e : enemies) e.render(gc);

        gc.setFill(Color.WHITE);
        gc.fillText("Stage: " + stageIndex + "  Hero: " + hero.getName() + "  HP: " + (int)hero.getHp() + "/" + (int)hero.getMaxHp() + "  DMG: " + (int)hero.getDamage(), 10, 20);
        gc.fillText("Attack: 1-Melee 2-Ranged 3-Magic  Space to attack", 10, 40);
    }

    private void showVictoryScreen() {
        javafx.scene.control.Label msg = new javafx.scene.control.Label("You won!");
        msg.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");

        javafx.scene.control.Button backBtn = new javafx.scene.control.Button("Return to menu");
        backBtn.setOnAction(e -> game.Menu.show(stage));

        javafx.scene.layout.VBox box = new javafx.scene.layout.VBox(15, msg, backBtn);
        box.setAlignment(javafx.geometry.Pos.CENTER);
        box.setStyle("-fx-background-color: #1e1e2f;");

        javafx.scene.Scene scene = new javafx.scene.Scene(box, getWidth(), getHeight());
        stage.setScene(scene);
    }

    private void showGameOverScreen() {
        javafx.scene.control.Label msg = new javafx.scene.control.Label("You lost!");
        msg.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");

        javafx.scene.control.Button backBtn = new javafx.scene.control.Button("Return to menu");
        backBtn.setOnAction(e -> game.Menu.show(stage));

        javafx.scene.layout.VBox box = new javafx.scene.layout.VBox(15, msg, backBtn);
        box.setAlignment(javafx.geometry.Pos.CENTER);
        box.setStyle("-fx-background-color: #1e1e2f;");

        javafx.scene.Scene scene = new javafx.scene.Scene(box, getWidth(), getHeight());
        stage.setScene(scene);
    }

    public void addObserver(Observer o) { observers.add(o); }
    private void notifyObservers(String event) {
        for (Observer o : observers) o.update(event);
    }
}
