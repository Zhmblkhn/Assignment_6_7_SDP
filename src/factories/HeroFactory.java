package factories;

import entities.*;

public class HeroFactory {
    public static Hero createHero(String type) {
        return switch (type.toLowerCase()) {
            case "archer" -> new Archer();
            case "mage" -> new Mage();
            default -> new Warrior();
        };
    }
}
