package entities;

import combat.MagicAttack;

public class Mage extends Hero {
    public Mage() {
        super("Mage", 85, 20, new MagicAttack());
        this.speed = 3.4;
    }
}
