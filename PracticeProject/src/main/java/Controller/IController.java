package Controller;

import groupid1.UIController;

import java.awt.event.MouseEvent;

/**
 * Публичный интерфейс контроллера(Выступает контекстом для состояний)
 * void setGrid(int width, int height) - задать поле
 * void setStartState() - установить начальную вершину
 * void setFinishState() - установить конечную вершину
 * void setStateOfAddingBlock() - установить состояние добавления стены
 * void setStateOfDelete() - установить пустую клетку
 * void nextStep() - следующий шаг алгоримта
 * void backStep() - предыдущий шаг алгоритма
 * void startAlgorithm() - построить путь
 * void showAlgorithm() - воспроизвести
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
