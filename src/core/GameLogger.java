package core;

public class GameLogger implements Observer {
    @Override
    public void update(String event) { System.out.println("[LOG] " + event); }
}
