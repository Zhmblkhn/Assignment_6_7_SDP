package upgrades;

import entities.Hero;

public abstract class Upgrade {
    protected String name;

    public Upgrade(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void apply(Hero hero);
}
