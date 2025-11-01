package upgrades;

import entities.Hero;

public class SpeedBoost extends Upgrade {

    private final double add;

    public SpeedBoost(double add) {
        super("Speed Boost (+" + add + ")");
        this.add = add;
    }

    @Override
    public void apply(Hero hero) {
        hero.addSpeed(add);
    }

    @Override
    public String toString() { return name; }
}
