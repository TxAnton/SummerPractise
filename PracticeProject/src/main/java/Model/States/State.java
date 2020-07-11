package Model.States;

import java.io.FileNotFoundException;

/**
 * Интерфейс состояний
 *  void Init(int width, int height)
 *  void nextStep() - следующий шаг алгоритма
 *  void backStep() - предыдущий шаг алгоритма
 *  void startAlgorithm()
 *  void resetAlgorithm()
 *  void loadGrid();
 *  void saveGrid();
 *  void mousePressed(int posX, int posY) - реакция на нажатие кнопки мыши
 */

public interface State {
    void Init(int width, int height);
    void nextStep();
    void backStep();
    void startAlgorithm();
    void resetAlgorithm();
    void loadGrid();
    void saveGrid() ;
    void mousePressed(int posX, int posY);
}
