package combat;

public class MagicAttack implements AttackStrategy {
    @Override
    public double attack(double baseDamage) { return baseDamage * 1.3; }
}
