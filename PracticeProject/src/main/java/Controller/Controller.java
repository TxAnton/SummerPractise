package Controller;

import Model.Model;
import Model.States.*;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Controller implements IController{
    private final Model model;
    private final Visualisator view;
    private State currentState;

    public Controller(Model model,Visualisator view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void setGrid(int width, int height){
        currentState.Init(width,height);
    }

    @Override
    public void setStartState() {
        currentState.close();
        currentState = new AddingState(model, view, "Start");
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void setFinishState() {
        currentState.close();
        currentState = new AddingState(model,view, "Finish");
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void setStateOfAddingBlock() {
        currentState.close();
        currentState = new AddingState(model,view, "Block");
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void setStateOfDelete(){
        currentState.close();
        currentState = new AddingState(model,view, "Empty");
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void nextStep(){
        currentState.nextStep();
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void backStep() {
        currentState.backStep();
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void startAlgorithm() {
        currentState.startAlgorithm();
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void resetAlgorithm() {
        currentState.resetAlgorithm();
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void showAlgorithm(){
        currentState.showAlgorithm();
        //view.setLabelHelp(currentState.getStatus());
    }

    @Override
    public void mousePressed(int posX, int posY){
    }
/*
    @Override
    public void saveGraph() {
        File file = view.showFileChooserDialog("Сохранить граф");
        if (file != null) {
            try {
                model.saveGraph(file.getAbsoluteFile().toString());
            } catch (IOException e) {
                view.showErrorDialog("Ошибка", "Не удалось сохраненить граф. Попробуйте еще раз!");
            }
        }
    }

    @Override
    public void loadGraph() {
        File file = view.showFileChooserDialog("Загрузить граф");
        if (file != null) {
            try {
                model.loadGraph(file.getAbsoluteFile().toString());
            } catch (Exception e) {
                view.showErrorDialog("Ошибка", "Не удалось загрузить граф. Попробуйте еще раз!");
            }
        }
    }
    */
}
