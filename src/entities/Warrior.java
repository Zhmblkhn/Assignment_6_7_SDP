package entities;

import combat.MeleeAttack;

public class Warrior extends Hero {
    public Warrior() {
        super("Warrior", 130, 18, new MeleeAttack());
        this.speed = 3.2;
    }
}
