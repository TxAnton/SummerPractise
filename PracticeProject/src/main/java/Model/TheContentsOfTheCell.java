package Model;

/*
* это абстрактный класс, в котором будет ещё 5 классов:
* Пустая клетка, клетка-блок, начало, финиш и клетка-путь,
*  то есть каждая из них будет иметь свой цвет
* и какой-то метод вывода клетки на экран)
* */



//----------------------------- Класс содержимого клетки -----------------------------
public abstract class TheContentsOfTheCell {
    public static class Empty extends ObstacleObject {
        public Empty(){
            super(false);//вызывается конструктор родительского класса
            //МЫ ГОВОРИМ ЧТО КЛЕТКА ПУСТАЯ
        }

    }

    public static class Block extends ObstacleObject {
        public Block(){
            super(true);
            // МЫ ГОВОРИМ ЧТО КЛЕТКА !НЕ! ПУСТАЯ - блок
        }

    }

    public static class Start extends ObstacleObject {
        public Start(){
            super(false);
        }

    }

    public static class Goal extends ObstacleObject {
        public Goal(){
            super(false);
        }

    }

    public static class Path extends ObstacleObject {
        public Path(){
            super(false);
        }

    }
}