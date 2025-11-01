package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Enemy {
    protected String name;
    protected double hp;
    protected double maxHp;
    protected double damage;
    protected double x, y;
    protected double size = 34;
    protected double speed = 1000;

    private double attackCooldown = 0;
    private final double attackInterval = 1.0;

    public Enemy(String name, double maxHp, double damage, double x, double y) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }

    public void updateTowards(Hero hero, double dt) {
        if (!isAlive()) return;
        double dx = hero.getX() - x;
        double dy = hero.getY() - y;
        double dist = Math.hypot(dx, dy);
        double factor = 5.0;
        if (dist > 1) {
            x += dx / dist * speed * dt * factor;
            y += dy / dist * speed * dt * factor;
        }
        if (attackCooldown > 0) attackCooldown -= dt;
    }

    public boolean tryHit() {
        if (attackCooldown <= 0) {
            attackCooldown = attackInterval;
            return true;
        }
        return false;
    }

    public void takeDamage(double d) {
        hp -= d;
        if (hp < 0) hp = 0;
    }

    public boolean isAlive() { return hp > 0; }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.CRIMSON);
        gc.fillOval(x - size/2.0, y - size/2.0, size, size);
        gc.setFill(Color.GRAY);
        gc.fillRect(x - 25, y - size/2.0 - 12, 50, 6);
        gc.setFill(Color.LIME);
        double pct = hp / maxHp;
        gc.fillRect(x - 25, y - size/2.0 - 12, 50 * pct, 6);
        gc.setFill(Color.WHITE);
        gc.fillText(name, x - 20, y - size/2.0 - 18);
    }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getSize() { return size; }
    public double getDamage() { return damage; }
    public String getName() { return name; }

    public void rangedAttack(Hero hero) {
        if (!isAlive()) return;
        double distance = Math.hypot(hero.getX() - x, hero.getY() - y);
        if (distance <= 300) {
            hero.takeDamage(damage * 1.2);
            System.out.println(name + " breathes fire on " + hero.getName() + " for " + (int)(damage*1.2));
        }
    }
}
