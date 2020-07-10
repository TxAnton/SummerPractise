package Model;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import Model.States.Visualisator;

public class Model {
    private Grid g_grid;
    private Grid g_grid_copy;
    private PathFinder g_finder;
    private LinkedList g_path;
    private LinkedList g_path_for_reset;
    public LinkedList<Memento> mem; //это будет массив снимков
    private int cur;//индекс снимка
    private static final int WIDTH = 25;
    private static final int HEIGHT = 20;
    private int flag;
    private int counter_reset;

    public Visualisator getVisualisator() {
        return visualisator;
    }

    public void setVisualisator(Visualisator visualisator) {
        this.visualisator = visualisator;
    }

    public Visualisator visualisator;

    public Model() {
        createDefault();
    }


    public void createDefault() {//создается поле по умолчанию
        this.g_grid = new Grid(Model.WIDTH, Model.HEIGHT);
        this.cur = 0;
        this.g_grid_copy = new Grid(Model.WIDTH, Model.HEIGHT);
        flag=0;
        counter_reset = 0;
        //visualisator.sendMemento(new Memento(g_grid));

    }

    public void createGrid(int width, int height){
        this.g_grid = new Grid(width, height);
        this.cur = 0;
    }


    public void SetObject(int x, int y, String className) {

        Point location = new Point(x,y);



        if(className.equals("Start")){
            TheContentsOfTheCell start = new TheContentsOfTheCell.Start(location, null, null);
            this.g_grid.setObject(x,y, start);
        }
        else if(className.equals("Finish")){
            TheContentsOfTheCell finish = new TheContentsOfTheCell.Finish(location, null, null);
            this.g_grid.setObject(x,y, finish);
        }
        else if(className.equals("Block")){
            TheContentsOfTheCell block = new TheContentsOfTheCell.Block(location, null, null);
            this.g_grid.setObject(x,y, block);
        }
        else {
            TheContentsOfTheCell empty = new TheContentsOfTheCell.Empty(location, null, null);
            this.g_grid.setObject(x,y, empty);
        }


        visualisator.sendMemento(new Memento(g_grid));
        //this.flag = 0;
        //g_grid_copy = g_grid;

    }

    public void Execute() throws ClassNotFoundException {

        this.g_finder = new PathFinder();//вызвали конструктор
        this.g_path = this.g_finder.findPath(this.g_grid);//вызвали метод и нашли путь
        this.mem = this.g_finder.getHistory();//получаем mementos
        //visualisator.sendPath(this.g_path);
        //this.mem.add(new Memento(this.g_grid));

    }

    public Grid cleanGrid(Grid grid){
        for(int y = 0; y < grid.getHeight(); y++){
            for(int x = 0; x < grid.getWidth(); x++){
                if(grid.getObject(x,y).getType() == 5 ) {
                    TheContentsOfTheCell empty = new TheContentsOfTheCell.Empty(new Point(x,y),null, null);
                    grid.setObject(x,y, empty);
                }
                if(grid.getObject(x,y).getType() == 6 ) {
                    TheContentsOfTheCell empty = new TheContentsOfTheCell.Empty(new Point(x,y),null, null);
                    grid.setObject(x,y, empty);
                }
                if(grid.getObject(x,y).getType() == 7 ) {
                    TheContentsOfTheCell empty = new TheContentsOfTheCell.Empty(new Point(x,y),null, null);
                    grid.setObject(x,y, empty);
                }
            }
        }
        return grid;


    }

    public void Reset(){

        cur = 0;
        this.g_grid = cleanGrid(this.g_grid);
        /*try {
            Point start = new Point(g_grid.getObjectPoint(TheContentsOfTheCell.Start.class.getName()));
            Point finish = new Point(g_grid.getObjectPoint(TheContentsOfTheCell.Finish.class.getName()));
            TheContentsOfTheCell finishTop = new TheContentsOfTheCell.Finish(finish, null, null);
            TheContentsOfTheCell startTop = new TheContentsOfTheCell.Start(start, null, null);
            g_grid_copy.setObject(start.x,start.y,startTop);
            g_grid_copy.setObject(finish.x,finish.y,finishTop);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/



        //this.flag = 1;//???????????????
        visualisator.sendMemento(mem.get(0));

        //++counter_reset;
        this.mem.clear();
        g_path.clear();
        visualisator.sendPath(g_path);

        /*try {
            Execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/


        //потом: возвращает поле изначальное Антону
    }

    public void Next() {

        /*if (this.flag ==1){
            //System.out.println("Я ЗДЕЕЕЕЕЕЕЕСЬ");
            mem.clear();
            this.g_path.clear();
            //visualisator.sendPath(g_path);
        }*/


            //System.out.println("И ЗачеМ Я ЗДЕСЬ??");
        if (this.cur < this.mem.size() - 1) {
            this.cur++;
        }
        if ((this.cur == this.mem.size() - 1) & this.cur != 0) {

            visualisator.sendPath(this.g_path);

        } else if (this.cur < this.mem.size() - 1) {
            visualisator.sendMemento(mem.get(this.cur));
        }


        //return this.mem.get(this.cur);

    }

    public void Prev(){
        if (flag ==1){
            mem.clear();
            g_path.clear();
            //visualisator.sendPath(this.g_path);
        }
        if((this.cur <= this.mem.size()-1) &&  this.cur > 0){
            --this.cur;
        }
        if((this.cur <= this.mem.size()-1) && this.cur >= 0) {
            visualisator.sendMemento(mem.get(this.cur));
            //return this.mem.get(this.cur);
        }
        //return null;
    }

    public void saveGraph(String fileName){
        try {
            FileWriter writer = new FileWriter(fileName, false);
            writer.write(g_grid.getWidth());
            writer.append('\n');
            writer.write(g_grid.getHeight());
            writer.append('\n');
            for(int y = 0; y < g_grid.getHeight(); y++) {
                for (int x = 0; x < g_grid.getWidth(); x++) {
                    switch (g_grid.getObject(x,y).getType()) {
                        case 1:
                            writer.append(' ');
                        case 2:
                            writer.append('#');
                        case 3:
                            writer.append('s');
                        case 4:
                            writer.append('f');
                        case 5:
                            writer.append('+');
                        case 6:
                            writer.append('-');
                        case 7:
                            writer.append('x');
                    }
                }
                writer.append('\n');
            }
            //writer.flush();
            writer.close();
        } catch (IOException e){

        }

    }

    public void loadGraph(String fileName){
        try {
            FileReader reader = new FileReader(fileName);
            char c;
            int width = reader.read();
            c = (char)reader.read();
            int height = reader.read();
            c = (char)reader.read();
            createGrid(width,height);
            for(int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Point location = new Point(x,y);
                    c = (char)reader.read();
                    switch (c) {
                        case ' ':
                            TheContentsOfTheCell empty = new TheContentsOfTheCell.Empty(location, null, null);
                            g_grid.setObject(x,y,empty);
                        case '#':
                            TheContentsOfTheCell block = new TheContentsOfTheCell.Block(location, null, null);
                            g_grid.setObject(x,y,block);
                        case 's':
                            TheContentsOfTheCell start = new TheContentsOfTheCell.Start(location, null, null);
                            g_grid.setObject(x,y,start);
                        case 'f':
                            TheContentsOfTheCell finish = new TheContentsOfTheCell.Finish(location, null, null);
                            g_grid.setObject(x,y,finish);
                        case '+':
                            TheContentsOfTheCell open = new TheContentsOfTheCell.Open(location, null, null);
                            g_grid.setObject(x,y,open);
                        case '-':
                            TheContentsOfTheCell close = new TheContentsOfTheCell.Close(location, null, null);
                            g_grid.setObject(x,y,close);
                        case 'x':
                            TheContentsOfTheCell path = new TheContentsOfTheCell.Path(location, null, null);
                            g_grid.setObject(x,y,path);
                    }
                }
                c = (char)reader.read();
            }
            reader.close();
            visualisator.sendMemento(new Memento(g_grid));
        } catch (IOException e){

        }
    }

}
