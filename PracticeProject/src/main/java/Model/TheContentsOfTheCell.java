package Model;

/*
 * это абстрактный класс, в котором 4 класса:
 * Пустая клетка, клетка-блок, начало, финиш,
 *  то есть каждая из них будет иметь свой цвет
 *
 * */

/*
* ТИПЫ:
* 1 - empty
* 2 - block
* 3 - start
* 4 - finish
* 5 - open
* 6 - close
* ????????7 - path
* */

import java.awt.*;

//----------------------------- Класс содержимого клетки -----------------------------
public class TheContentsOfTheCell {
    private boolean g_isObstacle;//есть препятствие или нет
    private TheContentsOfTheCell g_parent;
    private Point g_location;//её расположение на сетке( Х и У )
    private float g_costFromStart;//функция(g) - расстояние от начальной вершины
    private float g_costToFinish;//функция(h) - оценка расстояния до финиша
    private int type;

    public TheContentsOfTheCell(Point location, boolean isObstacle, TheContentsOfTheCell parent, TheContentsOfTheCell finish, int type){
        //this.setObstacle(location, isObstacle, parent, finish);
        this.g_isObstacle = isObstacle;
        this.g_parent = parent;
        this.g_location = location;
        this.type = type;
        float x;
        float y;
        if(parent != null){//если это НЕ стратовая вершина, то
            //вычисляем расстояние от начальной вершины до текщей
            this.g_costFromStart = this.calculateCostFromStart(parent);//устанавливаем функцию g(v)
        }
        if(finish != null){//если это НЕ стратовая вершина, то
            //вычисляем функцию оценки до финиша!
            x = location.x - finish.getLocation().x;
            y = location.y - finish.getLocation().y;
            this.g_costToFinish = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            //эвристическая оценка выбрана как евклидово расстояние
            //может в дальнейшем изменить..?
        }

    }

    /*public void setObstacle(Point location, boolean isObstacle, TheContentsOfTheCell parent, TheContentsOfTheCell finish){
        this.g_isObstacle = isObstacle;
        this.g_location = location;
        this.g_parent = parent;
    }

    public void set(TheContentsOfTheCell parent, TheContentsOfTheCell finish) {
        float x;
        float y;
        if(parent != null){//если это НЕ стратовая вершина, то
            //вычисляем расстояние от начальной вершины до текщей
            this.g_costFromStart = this.calculateCostFromStart(parent);//устанавливаем функцию g(v)
        }
        if(finish != null){//если это НЕ стратовая вершина, то
            //вычисляем функцию оценки до финиша!
            x = this.g_location.x - finish.g_location.x;
            y = this.g_location.y - finish.g_location.x;
            this.g_costToFinish = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            //эвристическая оценка выбрана как евклидово расстояние
            //может в дальнейшем изменить..?
        }
        this.g_parent = parent;
    }*/

    public int getType(){
        return this.type;
    }

    public void getType(int newType){
        this.type = newType;
    }

    private float calculateCostFromStart(TheContentsOfTheCell parent){
        float x = this.g_location.x - parent.g_location.x;
        float y = this.g_location.y - parent.g_location.y;
        float sum = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        //if(this.getParent()!=null) {
        sum += this.getParent().getCostFromStart();
        //}
        return sum;
    }

    public TheContentsOfTheCell getParent(){
        return this.g_parent;
    }

    public boolean equals(TheContentsOfTheCell top){
        return this.g_location.equals(top.getLocation());
    }

    public Point getLocation(){
        return this.g_location;
    }

    public float getTotalCost(){
        return this.g_costFromStart + this.g_costToFinish;
    }

    public float getCostFromStart(){
        return this.g_costFromStart;
    }

    public float getCostToFinish(){
        return this.g_costToFinish;
    }

    public String getParentMessage(){
        if(this.g_parent == null){
            return null;
        }
        return "[" + this.g_parent.getLocation().x + "," + this.g_parent.getLocation().y + "]";
    }

    public String getLocationMessage(){
        return "[" + this.g_location.x + "," + this.g_location.y + "]";
    }

    public String toString(){
        return "[" + this.g_location.x + "," + this.g_location.y + "]" + "\tparent: " + this.g_parent;
    }

    public boolean isObstacle(){
        return this.g_isObstacle;
    }

    public static class Empty extends TheContentsOfTheCell {

        public Empty(Point location, TheContentsOfTheCell parent, TheContentsOfTheCell finish){
            super(location, false, parent, finish, 1);//вызывается конструктор родительского класса
            //МЫ ГОВОРИМ ЧТО КЛЕТКА ПУСТАЯ
        }

    }

    public static class Open extends TheContentsOfTheCell {
        public Open(Point location, TheContentsOfTheCell parent, TheContentsOfTheCell finish){
            super(location,false, parent, finish, 5);

        }
    }

    public static class Close extends TheContentsOfTheCell {

        public Close(Point location, TheContentsOfTheCell parent, TheContentsOfTheCell finish){
            super(location,false, parent, finish, 6);//вызывается конструктор родительского класса
            //МЫ ГОВОРИМ ЧТО КЛЕТКА close
        }

    }

    public static class Block extends TheContentsOfTheCell {

        public Block(Point location, TheContentsOfTheCell parent, TheContentsOfTheCell finish){
            super(location,true, parent, finish, 2);

            // МЫ ГОВОРИМ ЧТО КЛЕТКА !НЕ! ПУСТАЯ - блок
        }

    }

    public static class Start extends TheContentsOfTheCell {

        public Start(Point location, TheContentsOfTheCell parent, TheContentsOfTheCell finish){
            super(location,false, parent, finish, 3);

        }
    }

    public static class Finish extends TheContentsOfTheCell {

        public Finish(Point location, TheContentsOfTheCell parent, TheContentsOfTheCell finish){
            super(location,false, parent, finish, 4);

        }
    }

    public static class Path extends TheContentsOfTheCell {

        public Path(Point location, TheContentsOfTheCell parent, TheContentsOfTheCell finish){
            super(location,false, parent, finish, 7);

        }
    }

}