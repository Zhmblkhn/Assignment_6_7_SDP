package entities;

import combat.AttackStrategy;
import core.Subject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Hero extends Subject {
    protected String name;
    protected double maxHp;
    protected double hp;
    protected double damage;
    protected double speed = 3.5;
    public AttackStrategy strategy;
    protected double x = 100, y = 100;
    protected double size = 36;

    public Hero(String name, double maxHp, double damage, AttackStrategy strategy) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.damage = damage;
        this.strategy = strategy;
    }

    public void moveBy(double dx, double dy) {
        x = clamp(x + dx, size/2.0, 800 - size/2.0);
        y = clamp(y + dy, size/2.0, 600 - size/2.0);
    }

    public void attack(Enemy enemy) {
        if (!isAlive() || enemy == null || !enemy.isAlive()) return;
        double dealt = strategy.attack(damage);
        enemy.takeDamage(dealt);
        notifyObservers(name + " hits " + enemy.getName() + " for " + (int)dealt);
    }

    public void takeDamage(double amount) {
        hp -= amount;
        if (hp < 0) hp = 0;
        notifyObservers(name + " took " + (int)amount + " dmg");
    }

    public boolean isAlive() { return hp > 0; }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.web("#3db27f"));
        gc.fillOval(x - size/2.0, y - size/2.0, size, size);
        gc.setFill(Color.GRAY);
        gc.fillRect(x - 25, y - size/2.0 - 12, 50, 6);
        gc.setFill(Color.LIME);
        double pct = hp / maxHp;
        gc.fillRect(x - 25, y - size/2.0 - 12, 50 * pct, 6);
    }

    private static double clamp(double v, double a, double b) { return Math.max(a, Math.min(b, v)); }

    public String getName() { return name; }
    public double getHp() { return hp; }
    public double getMaxHp() { return maxHp; }
    public double getDamage() { return damage; }
    public double getSpeed() { return speed; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getSize() { return size; }

    public void addSpeed(double s) { speed += s; notifyObservers(name + " speed +" + s); }
    public void addMaxHp(double v) { maxHp += v; hp += v; notifyObservers(name + " maxHP +" + (int)v); }
    public void multiplyDamage(double factor) { damage *= factor; notifyObservers(name + " damage x" + factor); }

    public void setStrategy(AttackStrategy strategy) { this.strategy = strategy; }

    public void setPosition(double x, double y) { this.x = x; this.y = y; }
}
