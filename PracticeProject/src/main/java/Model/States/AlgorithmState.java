package Model.States;

import Model.*;
import java.util.LinkedList;

public class AlgorithmState implements State{
    private final Model model;
    private final Visualisator view;

    public AlgorithmState(Model model, Visualisator view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void nextStep() {
        model.Next();
    }

    @Override
    public void backStep() {
        model.Prev();
    }

    @Override
    public void Init(int width, int height){
        model.createGrid(width, height);
    }

    @Override
    public void startAlgorithm(){
        try {
            model.Execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetAlgorithm() {
        model.Reset();
    }

    @Override
    public void saveGrid(){
        model.saveGraph("save.txt");
    }

    @Override
    public void loadGrid(){
        model.loadGraph("save.txt");
    }

    @Override
    public String getStatus(){
        return "Выполнение алгоритма поиска пути.";
    }

    @Override
    public void mousePressed(int posX, int posY) { }
}
