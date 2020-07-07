package Model;

/*
 * это абстрактный класс, в котором 4 класса:
 * Пустая клетка, клетка-блок, начало, финиш,
 *  то есть каждая из них будет иметь свой цвет
 *
 * */

//----------------------------- Класс содержимого клетки -----------------------------
public abstract class TheContentsOfTheCell {
    private boolean g_isObstacle;//есть препятствие или нет

    public TheContentsOfTheCell(boolean isObstacle){
        this.setObstacle(isObstacle);
    }

    public void setObstacle(boolean isObstacle){
        this.g_isObstacle = isObstacle;
    }

    public boolean isObstacle(){
        return this.g_isObstacle;
    }


    public static class Empty extends TheContentsOfTheCell {
        public Empty(){
            super(false);//вызывается конструктор родительского класса
            //МЫ ГОВОРИМ ЧТО КЛЕТКА ПУСТАЯ
        }

    }

    public static class Block extends TheContentsOfTheCell {
        public Block(){
            super(true);
            // МЫ ГОВОРИМ ЧТО КЛЕТКА !НЕ! ПУСТАЯ - блок
        }

    }

    public static class Start extends TheContentsOfTheCell {
        public Start(){
            super(false);
        }

    }

    public static class Finish extends TheContentsOfTheCell {
        public Finish(){
            super(false);
        }

    }


}