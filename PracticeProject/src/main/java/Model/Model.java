package Model;

import java.awt.*;
import java.util.LinkedList;
import Model.States.Visualisator;

public class Model {
    private Grid g_grid;
    private PathFinder g_finder;
    private LinkedList g_path;
    private LinkedList<Memento> mem; //это будет массив снимков
    private int cur;//индекс снимка
    private static final int WIDTH = 25;
    private static final int HEIGHT = 20;
    public Visualisator visualisator;



    public void createDefault() {//создается поле по умолчанию
        this.g_grid = new Grid(Model.WIDTH, Model.HEIGHT);
        this.cur = 0;

    }

    public void createGrid(int width, int height){
        this.g_grid = new Grid(width, height);
        this.cur = 0;
    }


    public void SetObject(int x, int y, String className) {

        Point location = new Point(x,y);

        if(className.equals("Start")){
            TheContentsOfTheCell start = new TheContentsOfTheCell.Close(location);
            this.g_grid.setObject(x,y, start);
        }
        else if(className.equals("Finish")){
            TheContentsOfTheCell finish = new Model.TheContentsOfTheCell.Open(location);
            this.g_grid.setObject(x,y, finish);
        }
        else if(className.equals("Block")){
            TheContentsOfTheCell block = new TheContentsOfTheCell.Block(location);
            this.g_grid.setObject(x,y, block);
        }
        else {
            TheContentsOfTheCell empty = new TheContentsOfTheCell.Empty(location);
            this.g_grid.setObject(x,y, empty);
        }

    }

    public void Execute() throws ClassNotFoundException {
        this.g_finder = new PathFinder();//вызвали конструктор
        this.g_path = this.g_finder.findPath(this.g_grid);//вызвали метод и нашли путь
        this.mem = this.g_finder.getHistory();//получаем mementos
        visualisator.sendPath(this.g_path);
        //this.mem.add(new Memento(this.g_grid));

    }

    public void Reset(){
        visualisator.sendMemento(mem.get(0));
        this.mem.clear();

        //потом: возвращает поле изначальное Антону
    }

    public Memento Next(){
        visualisator.sendMemento(mem.get(++this.cur));
        if(cur == this.mem.size()-1){
            visualisator.sendPath(this.g_path);
        }
        return this.mem.get(this.cur);

    }

    public Memento Prev(){
        if(this.cur != 0) {
            visualisator.sendMemento(mem.get(--this.cur));
            return this.mem.get(this.cur);
        }
        return null;
    }


}
