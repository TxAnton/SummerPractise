package Model;

import java.awt.Point;
//----------------------------- Класс сетки -----------------------------
public class Grid {
    private TheContentsOfTheCell g_grid[][];//сама сетка, сост. из объектов
    private int g_width;//ширина
    private int g_height;//высота

    public Grid(int width, int height){
        this.initGrid(width, height);
    }

    public void initGrid(int width, int height){
        this.setWidth(width);
        this.setHeight(height);
        this.g_grid = new TheContentsOfTheCell[this.getHeight()][this.getWidth()];
        //создали сетку и ниже инициализируем каждую клетку как пустую
        for(int y = 0; y < this.getHeight(); y++){
            for(int x = 0; x < this.getWidth(); x++){
                this.g_grid[y][x] = new TheContentsOfTheCell.Empty(new Point(x,y),null, null);
            }
        }
    }



    public void setGrid(TheContentsOfTheCell[][] newGrid){

        this.g_grid = newGrid;
        this.setWidth(newGrid.length);
        this.setHeight(newGrid.length);
    }

    public void setObject(int x, int y, TheContentsOfTheCell g){//меняем значение клетки с одного объекта на другой
        this.g_grid[y][x] = g;
    }


    public TheContentsOfTheCell getObject(int x, int y){//ПОЛУЧИТЬ объект клетки
        return this.g_grid[y][x];
    }

    public Point getObjectPoint(String className) throws ClassNotFoundException{
        //Этот метод принимает параметр className,
        //который является классом, для которого требуется его экземпляр
        Point p = null;
        for(int y = 0; y < this.getHeight(); y++){
            for(int x = 0; x < this.getWidth(); x++){
                //оператор isInstance позволяет проверять,
                //является ли объект g_grid[y][x] экземпляром класса, на который ссылается className
                if(Class.forName(className).isInstance(this.getObject(x, y))){
                    return p = new Point(x, y);
                }
            }
        }
        return p;
    }
    /*
    то есть пояснение к вышеизложенному методу
    он используется в PathFinder
    Например, создается точка start, то есть в метод передается имя класса Start,
    и далее начинается проход по всему полю
    и если объект клетки равен классу Start, то бинго! мы создаем точку старта Point
    * */

    protected void setWidth(int width){
        this.g_width = width;
    }

    protected void setHeight(int height){
        this.g_height = height;
    }

    public int getWidth(){
        return this.g_width;
    }

    public int getHeight(){
        return this.g_height;
    }

    // Снимок — неизменяемый объект, поэтому "Создатель"=сетка передаёт
    // все своё состояние через параметры конструктора.
    public Memento createMemento(Grid grid) {
        return new Memento(grid);
    }


    // В нужный момент владелец снимка может восстановить состояние сетки
    public Grid restoreMemento(Memento memento){
        return memento.getGrid();
    }
}

