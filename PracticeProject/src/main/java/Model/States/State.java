package Model.States;

/**
 * Интерфейс состояний
 *  void Init(int width, int height)
 *  void nextStep() - следующий шаг алгоритма
 *  void backStep() - предыдущий шаг алгоритма
 *  void startAlgorithm()
 *  void showAlgorithm()
 *  void resetAlgorithm()
 *  String getStatus() - Возвращает текущие состояние данного стейта
 *  void close() - устанавливает исходное состояние
 *  void mousePressed(int posX, int posY) - реакция на нажатие кнопки мыши
 */

public interface State {
    void Init(int width, int height);
    void nextStep();
    void backStep();
    void startAlgorithm();
    void resetAlgorithm();
    String getStatus();
    void loadGrid();
    void saveGrid();
    void mousePressed(int posX, int posY);
}
