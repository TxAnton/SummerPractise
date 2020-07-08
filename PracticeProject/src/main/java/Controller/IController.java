package Controller;

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
    void setStartState();
    void setFinishState();
    void setStateOfAddingBlock();
    void setStateOfDelete();
    void nextStep();
    void backStep();
    void startAlgorithm();
    void resetAlgorithm();
    void showAlgorithm();
    void mousePressed(int posX, int posY);
//    void saveGraph();
//    void loadGraph();
}
