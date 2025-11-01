package combat;

public class RangedAttack implements AttackStrategy {
    @Override
    public double attack(double baseDamage) { return baseDamage * 0.9; }
}
