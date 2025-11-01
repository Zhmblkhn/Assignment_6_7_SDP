package upgrades.decorators;

import entities.Enemy;
import entities.Hero;
import javafx.scene.canvas.GraphicsContext;

public abstract class HeroDecorator extends Hero {
    protected Hero hero;

    public HeroDecorator(Hero hero) {
        super(hero.getName(), hero.getMaxHp(), hero.getDamage(), hero.strategy);
        this.hero = hero;
    }

    @Override
    public double getX() { return hero.getX(); }

    @Override
    public double getY() { return hero.getY(); }

    @Override
    public double getSize() { return hero.getSize(); }

    @Override
    public double getSpeed() { return hero.getSpeed(); }

    @Override
    public double getDamage() { return hero.getDamage(); }

    @Override
    public double getHp() { return hero.getHp(); }

    @Override
    public double getMaxHp() { return hero.getMaxHp(); }

    @Override
    public void moveBy(double dx, double dy) { hero.moveBy(dx, dy); }

    @Override
    public void attack(Enemy enemy) { hero.attack(enemy); }

    @Override
    public void takeDamage(double amount) { hero.takeDamage(amount); }

    @Override
    public boolean isAlive() { return hero.isAlive(); }

    @Override
    public void render(GraphicsContext gc) { hero.render(gc); }

    @Override
    public void setStrategy(combat.AttackStrategy strategy) { hero.setStrategy(strategy); }

    @Override
    public void setPosition(double x, double y) { hero.setPosition(x, y); }
}
