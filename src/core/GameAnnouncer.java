package core;

public class GameAnnouncer implements Observer {
    @Override
    public void update(String event) { System.out.println("ANNOUNCER: " + event); }
}
