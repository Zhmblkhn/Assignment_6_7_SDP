package combat;

public class MeleeAttack implements AttackStrategy {
    @Override
    public double attack(double baseDamage) { return baseDamage; }
}
