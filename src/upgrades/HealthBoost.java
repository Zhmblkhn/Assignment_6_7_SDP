package upgrades;

import entities.Hero;

public class HealthBoost extends Upgrade {

    private final double add;

    public HealthBoost(double add) {
        super("Health Boost (+" + (int)add + ")");
        this.add = add;
    }

    @Override
    public void apply(Hero hero) {
        hero.addMaxHp(add);
    }

    @Override
    public String toString() { return name; }
}
