package Model;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public void Next() {

        this.cur++;


        if(this.cur == this.mem.size()-1){
            /*try {
                this.g_finder = new PathFinder();//вызвали конструктор
                this.g_path = this.g_finder.findPath(this.g_grid);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }*/

            //System.out.println(this.g_path);
            visualisator.sendPath(this.g_path);
            //System.exit(0);
        }
        else if (this.cur < this.mem.size()-1){
            visualisator.sendMemento(mem.get(this.cur));
        }


        //return this.mem.get(this.cur);

    }

    public Memento Prev(){
        if(this.cur != 0) {
            visualisator.sendMemento(mem.get(--this.cur));
            return this.mem.get(this.cur);
        }
        return null;
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
            int c;
            int width = (char)reader.read();
            c = (char)reader.read();
            int height = (char)reader.read();
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
