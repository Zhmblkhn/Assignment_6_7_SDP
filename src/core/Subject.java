package core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Subject {
    private final List<Consumer<String>> observers = new ArrayList<>();

    public void addObserver(Consumer<String> observer) {
        observers.add(observer);
    }

    protected void notifyObservers(String event) {
        for (Consumer<String> o : observers) {
            o.accept(event);
        }
    }

    public void addObserver(core.Observer o) {
        addObserver((Consumer<String>) o::update);
    }
}
