package upgrades.decorators;

import entities.Hero;

public class DamageBoostDecorator extends HeroDecorator {
    private final double factor;

    public DamageBoostDecorator(Hero hero, double factor) {
        super(hero);
        this.factor = factor;
    }

    @Override
    public double getDamage() {
        return hero.getDamage() * factor;
    }
}
