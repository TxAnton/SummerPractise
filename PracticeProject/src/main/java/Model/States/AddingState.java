package Model.States;

import Model.Model;

public class AddingState implements State{
    private final Model model;
    private final Visualisator view;
    private final String type;

    public AddingState(Model model, Visualisator view, String type) {
        this.model = model;
        this.view = view;
        this.type = type;
    }

    @Override
    public void Init(int width, int height){}

    @Override
    public void nextStep() {}

    @Override
    public void backStep(){}

    @Override
    public void startAlgorithm(){}

    @Override
    public void resetAlgorithm(){}

    @Override
    public void saveGrid(){ model.saveGrid("save.txt"); }

    @Override
    public void loadGrid() { model.saveGrid("save.txt"); }

    @Override
    public void mousePressed(int posX, int posY) {
        model.SetObject(posX,posY,type);
    }
}
