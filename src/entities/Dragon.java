package entities;

public class Dragon extends Enemy {

    private double rangedCooldown = 0;
    private final double rangedInterval = 2.0;

    public Dragon(double x, double y) {
        super("Dragon", 300, 22, x, y);
        this.size = 80;
        this.speed = 45;
    }

    public void updateRangedCooldown(double dt) {
        if (rangedCooldown > 0) rangedCooldown -= dt;
    }

    public void rangedAttack(Hero hero) {
        if (!isAlive() || rangedCooldown > 0) return;

        double distance = Math.hypot(hero.getX() - x, hero.getY() - y);
        if (distance <= 300) {
            hero.takeDamage(damage * 0.8);
            System.out.println(name + " breathes fire on " + hero.getName() + " for " + (int)(damage*0.8));
            rangedCooldown = rangedInterval;
        }
    }
}