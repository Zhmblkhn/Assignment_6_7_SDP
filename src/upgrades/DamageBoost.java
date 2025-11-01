package upgrades;

import entities.Hero;

public class DamageBoost extends Upgrade {

    private final double factor;

    public DamageBoost(double factor) {
        super("Damage Boost (x" + factor + ")");
        this.factor = factor;
    }

    @Override
    public void apply(Hero hero) {
        hero.multiplyDamage(factor);
    }

    @Override
    public String toString() { return name; }
}
