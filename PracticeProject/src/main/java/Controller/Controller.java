package Controller;

import Model.Model;
import Model.States.*;
import groupid1.UIController;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Controller implements IController{
    private final Model model;
    private final Visualisator view;
    private State currentState;
    private boolean isAlgorithmStart = false;

    public Controller(Model model,Visualisator view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void setGrid(int width, int height){
        if (!isAlgorithmStart){
            currentState = new AlgorithmState(model,view);
            currentState.Init(width,height);
        }
    }

    @Override
    public void nextStep(){
        if (!isAlgorithmStart){
            currentState = new AlgorithmState(model,view);
            currentState.startAlgorithm();
        }
        isAlgorithmStart = true;
        currentState.nextStep();
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void backStep() {
        if (isAlgorithmStart)
            currentState.backStep();
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void resetAlgorithm() {
        if (isAlgorithmStart) {
            isAlgorithmStart = false;
            currentState.resetAlgorithm();
        }
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void mousePressed(int posX, int posY, UIController.CellType type){
        if (!isAlgorithmStart){
            switch (type){
                case END:
                    currentState = new AddingState(model,view,"Finish");
                    break;
                case WALL:
                    currentState = new AddingState(model,view,"Block");
                    break;
                case BLANK:
                    currentState = new AddingState(model,view,"Empty");
                    break;
                case START:
                    currentState = new AddingState(model,view,"Start");
                    break;
            }
            currentState.mousePressed(posX,posY);
        }
    }
}
