package entities;

import combat.RangedAttack;

public class Archer extends Hero {
    public Archer() {
        super("Archer", 95, 12, new RangedAttack());
        this.speed = 3.8;
    }
}
