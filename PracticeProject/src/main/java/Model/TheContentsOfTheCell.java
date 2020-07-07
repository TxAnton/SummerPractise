package Model;

/*
 * это абстрактный класс, в котором 4 класса:
 * Пустая клетка, клетка-блок, начало, финиш,
 *  то есть каждая из них будет иметь свой цвет
 *
 * */

import java.awt.*;

//----------------------------- Класс содержимого клетки -----------------------------
public class TheContentsOfTheCell {
    private boolean g_isObstacle;//есть препятствие или нет
    private TheContentsOfTheCell g_parent;
    private Point g_location;//её расположение на сетке( Х и У )
    private float g_costFromStart;//функция(g) - расстояние от начальной вершины
    private float g_costToFinish;//функция(h) - оценка расстояния до финиша

<<<<<<< HEAD
    public TheContentsOfTheCell(Point location, boolean isObstacle){
        this.setObstacle(location, isObstacle);
    }

    public void setObstacle(Point location, boolean isObstacle){
        this.g_isObstacle = isObstacle;
        this.g_location = location;
=======
    public TheContentsOfTheCell(int x, int y, boolean isObstacle){
        this.setObstacle(x, y, isObstacle);
    }

    public void setObstacle(int x, int y, boolean isObstacle){
        this.g_isObstacle = isObstacle;
        this.g_location.x = x;
>>>>>>> cc386ac65f455489adbb5b224ee4674a74122783

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
    }

    private float calculateCostFromStart(TheContentsOfTheCell parent){
        float x = this.g_location.x - parent.g_location.x;
        float y = this.g_location.y - parent.g_location.y;
        float sum = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        sum += this.getParent().getCostFromStart();
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
<<<<<<< HEAD
        public Empty(Point location){
            super(location, false);//вызывается конструктор родительского класса
=======
        public Empty(int x, int y){
            super(x , y, false);//вызывается конструктор родительского класса
>>>>>>> cc386ac65f455489adbb5b224ee4674a74122783
            //МЫ ГОВОРИМ ЧТО КЛЕТКА ПУСТАЯ
        }

    }

    public static class Close extends TheContentsOfTheCell {
<<<<<<< HEAD
        public Close(Point location){
            super(location,false);//вызывается конструктор родительского класса
=======
        public Close(int x, int y){
            super(x, y,false);//вызывается конструктор родительского класса
>>>>>>> cc386ac65f455489adbb5b224ee4674a74122783
            //МЫ ГОВОРИМ ЧТО КЛЕТКА close
        }

    }

    public static class Block extends TheContentsOfTheCell {
<<<<<<< HEAD
        public Block(Point location){
            super(location,true);
=======
        public Block(int x, int y){
            super(x, y,true);
>>>>>>> cc386ac65f455489adbb5b224ee4674a74122783
            // МЫ ГОВОРИМ ЧТО КЛЕТКА !НЕ! ПУСТАЯ - блок
        }

    }

    public static class Start extends TheContentsOfTheCell {
<<<<<<< HEAD
        public Start(Point location){
            super(location,false);
=======
        public Start(int x, int y){
            super(x, y,false);
>>>>>>> cc386ac65f455489adbb5b224ee4674a74122783
        }
    }

    public static class Finish extends TheContentsOfTheCell {
<<<<<<< HEAD
        public Finish(Point location){
            super(location,false);
=======
        public Finish(int x, int y){
            super(x, y,false);
>>>>>>> cc386ac65f455489adbb5b224ee4674a74122783
        }
    }
}