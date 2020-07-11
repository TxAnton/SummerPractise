package Model.States;

import Model.*;

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
        model.saveGrid("save.txt");
    }

    @Override
    public void loadGrid() {
        model.loadGrid("save.txt");
    }

    @Override
    public void mousePressed(int posX, int posY) { }
}
