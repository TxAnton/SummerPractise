package Controller;

import groupid1.UIController;

/**
 * Публичный интерфейс контроллера(Выступает контекстом для состояний)
 * void setGrid(int width, int height) - задать поле
 * void nextStep() - следующий шаг алгоримта
 * void backStep() - предыдущий шаг алгоритма
 * void resetAlgorithm();
 * void mousePressed(int posX, int posY, UIController.CellType type);
 * void saveGrid();
 * void loadGrid();
 */

public interface IController {
    void setGrid(int width, int height);
    void nextStep();
    void backStep();
    void resetAlgorithm();
    void mousePressed(int posX, int posY, UIController.CellType type);
    void saveGrid();
    void loadGrid();
}
