package factories;

import entities.*;

public class EnemyFactory {
    public static Enemy createEnemy(String type, double x, double y) {
        return switch (type.toLowerCase()) {
            case "minotaur" -> new Minotaur(x, y);
            case "griffin" -> new Griffin(x, y);
            case "hydra" -> new Hydra(x, y);
            case "dragon" -> new Dragon(x, y);
            case "goblin" -> new Goblin(x, y);
            default -> new Goblin(x, y);
        };
    }
}
