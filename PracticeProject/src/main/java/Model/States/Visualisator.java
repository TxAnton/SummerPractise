package Model.States;

import Model.Memento;
import Model.TheContentsOfTheCell;

import java.util.LinkedList;

public interface Visualisator {
    void sendMemento(Memento memento);
    void sendPath(LinkedList<TheContentsOfTheCell> path);
}
