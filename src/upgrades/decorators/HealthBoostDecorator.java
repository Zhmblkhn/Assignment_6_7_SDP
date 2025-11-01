package upgrades.decorators;

import entities.Hero;

public class HealthBoostDecorator extends HeroDecorator {
    private final double extraHp;

    public HealthBoostDecorator(Hero hero, double extraHp) {
        super(hero);
        this.extraHp = extraHp;
        hero.addMaxHp(extraHp);
        hero.takeDamage(-extraHp);
    }
}
