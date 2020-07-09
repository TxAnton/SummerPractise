package Model.States;

import Model.*;
import java.util.LinkedList;

public class AlgorithmState implements State{
    private final Model model;
    private final Visualisator view;
    private LinkedList<Memento> stepsView;
    private int indexStep = -1;

    public AlgorithmState(Model model, Visualisator view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void nextStep() {
        if (indexStep != -1) {
            indexStep++;
            model.Next();
        }
            //stepsView.set(indexStep++, model.Next());
    }

    @Override
    public void backStep() {
        if (indexStep != 0) {
            --indexStep;
            model.Prev();
        }
            //stepsView.set(--indexStep, model.Prev());
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
        indexStep = 0;
    }

    @Override
    public void resetAlgorithm() {
        model.Reset();
        indexStep = 0;
        //stepsView.clear();
    }

    @Override
    public String getStatus(){
        return "Выполнение алгоритма поиска пути.";
    }

    @Override
    public void mousePressed(int posX, int posY) {

    }
}
