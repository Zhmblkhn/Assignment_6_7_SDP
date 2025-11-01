package upgrades.decorators;

import entities.Hero;

public class SpeedBoostDecorator extends HeroDecorator {
    private final double extraSpeed;

    public SpeedBoostDecorator(Hero hero, double extraSpeed) {
        super(hero);
        this.extraSpeed = extraSpeed;
    }

    @Override
    public double getSpeed() {
        return hero.getSpeed() + extraSpeed;
    }
}
