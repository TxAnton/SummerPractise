package Model;

import java.awt.*;
import java.io.*;
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
    private int flag_start;
    private int flag_finish;
    private int counter_reset;
    private int prohibition_flag;

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
        flag_start = 0;
        flag_finish = 0;
        counter_reset = 0;
        prohibition_flag = 0;

    }

    public void createGrid(int width, int height){
        this.g_grid = new Grid(width, height);
        this.cur = 0;
        flag_start = 0;
        flag_finish = 0;
        prohibition_flag = 0;
    }


    public void SetObject(int x, int y, String className) {

        Point location = new Point(x,y);

        if(className.equals("Start")){
            if(flag_start == 0) {
                TheContentsOfTheCell start = new TheContentsOfTheCell.Start(location, null, null);
                this.g_grid.setObject(x, y, start);
                flag_start = 1;
            }
            else if(flag_start == 1){
                removeStart();
                TheContentsOfTheCell start = new TheContentsOfTheCell.Start(location, null, null);
                this.g_grid.setObject(x, y, start);
            }
        }
        else if(className.equals("Finish")){
            if(flag_finish == 0) {
                TheContentsOfTheCell finish = new TheContentsOfTheCell.Finish(location, null, null);
                this.g_grid.setObject(x, y, finish);
                flag_finish = 1;
            }
            else if(flag_finish == 1)
            {
                removeFinish();
                TheContentsOfTheCell finish = new TheContentsOfTheCell.Finish(location, null, null);
                this.g_grid.setObject(x, y, finish);
            }
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

        if(checkingTheStartAndFinish()) {
            this.g_finder = new PathFinder();//вызвали конструктор
            this.g_path = this.g_finder.findPath(this.g_grid);//вызвали метод и нашли путь
            this.mem = this.g_finder.getHistory();//получаем mementos
            //visualisator.sendPath(this.g_path);
            //this.mem.add(new Memento(this.g_grid));
        }


    }

    public boolean checkingTheStartAndFinish(){

        int f1 = 0;
        int f2 = 0;

        for(int y = 0; y < g_grid.getHeight(); y++){
            for(int x = 0; x < g_grid.getWidth(); x++){
                if(g_grid.getObject(x,y).getType() == 3 ) {
                    f1 = 1;
                }
                if(g_grid.getObject(x,y).getType() == 4){
                    f2 = 1;
                }

            }
        }
        if(f1 == 1 & f2 == 1){
            prohibition_flag = 2;
            return true;
        }
        else
            return false;
    }

    public void removeStart(){
        for(int y = 0; y < g_grid.getHeight(); y++){
            for(int x = 0; x < g_grid.getWidth(); x++){
                if(g_grid.getObject(x,y).getType() == 3 ) {
                    TheContentsOfTheCell empty = new TheContentsOfTheCell.Empty(new Point(x,y),null, null);
                    g_grid.setObject(x,y, empty);
                }

            }
        }
    }

    public void removeFinish(){
        for(int y = 0; y < g_grid.getHeight(); y++){
            for(int x = 0; x < g_grid.getWidth(); x++){
                if(g_grid.getObject(x,y).getType() == 4 ) {
                    TheContentsOfTheCell empty = new TheContentsOfTheCell.Empty(new Point(x,y),null, null);
                    g_grid.setObject(x,y, empty);
                }

            }
        }
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

        if(prohibition_flag == 2) {

            cur = 0;
            this.g_grid = cleanGrid(this.g_grid);

            visualisator.sendMemento(mem.get(0));

            this.mem.clear();
            if(g_path!=null) {
                g_path.clear();
                visualisator.sendPath(g_path);
            }
        }

        //потом: возвращает поле изначальное Антону
    }

    public void Next() {

        if(prohibition_flag == 2) {

            if (this.cur < this.mem.size() - 1) {
                this.cur++;
            }
            if ((this.cur == this.mem.size() - 1) & this.cur != 0) {

                if(g_path != null)
                    visualisator.sendPath(this.g_path);

            } else if (this.cur < this.mem.size() - 1) {
                visualisator.sendMemento(mem.get(this.cur));
            }
        }



    }

    public void Prev(){

        if(prohibition_flag == 2) {

            if ((this.cur <= this.mem.size() - 1) && this.cur > 0) {
                --this.cur;
            }
            if ((this.cur <= this.mem.size() - 1) && this.cur >= 0) {
                visualisator.sendMemento(mem.get(this.cur));

            }
        }

    }

    public void saveGrid(String fileName){
        try {
            FileWriter writer = new FileWriter(fileName, false);
            writer.write(mem.get(cur).getGrid().getWidth());
            writer.append('\n');
            writer.write(mem.get(cur).getGrid().getHeight());
            writer.append('\n');
            writer.write(this.cur);
            writer.append('\n');
            for(int y = 0; y < mem.get(cur).getGrid().getHeight(); y++) {
                for (int x = 0; x < mem.get(cur).getGrid().getWidth(); x++) {
                    switch (mem.get(cur).getObject(x,y).getType()) {
                        case 1:
                            writer.append(' ');
                            break;
                        case 2:
                            writer.append('#');
                            break;
                        case 3:
                            writer.append('s');
                            break;
                        case 4:
                            writer.append('f');
                            break;
                        case 5:
                            writer.append('+');
                            break;
                        case 6:
                            writer.append('-');
                            break;
                        case 7:
                            writer.append('x');
                            break;
                    }
                }
                writer.append('\n');
            }
            //writer.flush();
            writer.close();
        } catch (IOException e){

        }
    }

    public void loadGrid(String fileName) {
        try {
            if (this.cur != 0) {
                this.g_grid = cleanGrid(g_grid);
                this.mem.clear();
                this.g_path.clear();
                this.cur = 0;
            }
            FileReader reader = new FileReader(fileName);
            char c;
            int width = reader.read();
            c = (char) reader.read();
            int height = reader.read();
            c = (char) reader.read();
            int current = reader.read();
            c = (char) reader.read();
            createGrid(width, height);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Point location = new Point(x, y);
                    c = (char) reader.read();
                    switch (c) {
                        case ' ':
                            TheContentsOfTheCell empty = new TheContentsOfTheCell.Empty(location, null, null);
                            g_grid.setObject(x, y, empty);
                            break;
                        case '+':
                            TheContentsOfTheCell open = new TheContentsOfTheCell.Open(location, null, null);
                            g_grid.setObject(x, y, open);
                            break;
                        case '#':
                            TheContentsOfTheCell block = new TheContentsOfTheCell.Block(location, null, null);
                            g_grid.setObject(x, y, block);
                            break;
                        case 's':
                            TheContentsOfTheCell start = new TheContentsOfTheCell.Start(location, null, null);
                            g_grid.setObject(x, y, start);
                            break;
                        case 'f':
                            TheContentsOfTheCell finish = new TheContentsOfTheCell.Finish(location, null, null);
                            g_grid.setObject(x, y, finish);
                            break;
                        case '-':
                            TheContentsOfTheCell close = new TheContentsOfTheCell.Close(location, null, null);
                            g_grid.setObject(x, y, close);
                            break;
                        case 'x':
                            TheContentsOfTheCell path = new TheContentsOfTheCell.Path(location, null, null);
                            g_grid.setObject(x, y, path);
                            break;
                    }
                }
                c = (char) reader.read();
            }
            reader.close();
            if (current != 0) {
                try {
                    Execute();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                this.cur = current;
                visualisator.sendMemento(mem.get(cur));
            }
        } catch (IOException e) {

        }
    }
}
