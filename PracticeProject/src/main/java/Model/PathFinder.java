package Model;

import java.awt.Point;
import java.util.LinkedList;

//----------------------------- Класс поиска пути -----------------------------
public class PathFinder {
    //в классе поиска пути хранятся список открытых и закрытх вершин!
    private LinkedList g_openList;
    private LinkedList g_closedList;
    private LinkedList<Memento> history;

    public PathFinder(){
        this.g_openList = new LinkedList();
        this.g_closedList = new LinkedList();
        this.history = new LinkedList();
    }

    public LinkedList findPath(Grid grid1) throws NullPointerException, ClassNotFoundException{
        Grid grid = grid1;

        this.g_openList.clear();//очищаем списки
        this.g_closedList.clear();
        history.clear();

        //небольшое уточнение
        //TheContentsOfTheCell.Start.class.getName() -
        //возвращает имя класса, представленной этим объектом класса, в виде строки.
        //далее с помощью метода getObjectPoint создастся точка strat а затем и finish
        Point start = new Point(grid.getObjectPoint(TheContentsOfTheCell.Start.class.getName()));
        Point finish = new Point(grid.getObjectPoint(Model.TheContentsOfTheCell.Finish.class.getName()));
        TheContentsOfTheCell finishTop = new TheContentsOfTheCell.Finish(finish);
        TheContentsOfTheCell startTop = new TheContentsOfTheCell.Start(start);
        TheContentsOfTheCell temp = new TheContentsOfTheCell.Open(start);


        this.g_openList.add(temp);//добавляем в список откр вершин начальную вершину
        while(!this.g_openList.isEmpty()){//пока список откр вершин не пуст...
            if(temp.getLocation().equals(finish)){//если расположение текущей точки = finish
                return this.constructPath(temp);//то строим путь!!!
            }
            //иначе
            temp = this.lookingForBestTop();//текущая вершина = лучшей из доступный
            /////////////////////
            TheContentsOfTheCell close = new TheContentsOfTheCell.Close(temp.getLocation());
            this.g_closedList.addLast(temp);//добавляем её в закрытый список
            grid.setObject(temp.getLocation().x,temp.getLocation().y,close);
            history.add(new Memento(grid));
            /////////////////////
            this.addNeighbor(temp, startTop, finishTop, grid);
            //вызываем метод добавляения соседней вершины
        }

        return null;
    }

    public LinkedList<Memento> getHistory(){
        return history;
    }

    private void addNeighbor(TheContentsOfTheCell parent, TheContentsOfTheCell start, TheContentsOfTheCell finish, Grid grid){
        int x = parent.getLocation().x;//получаем координаты x и y родителя
        int y = parent.getLocation().y;
        //выполняем проход:
        // -----------
        //| 1 | 2 | 3 |
        // -----------
        //| 4 | T | 5 |
        // -----------
        //| 6 | 7 | 8 |
        // -----------
        for(int newY = y - 1; newY < y + 2; newY++){
            for(int newX = x - 1; newX < x + 2; newX++){
                try{
                    if(newY == y && newX == x){
                        //пропускаем самого себя
                    }
                    else if(!grid.getObject(newX, newY).isObstacle()){//если в ячейке нет БЛОКА
                        TheContentsOfTheCell top = new TheContentsOfTheCell.Open(new Point(newX, newY));//создаем новую вершину,
                        top.set(parent, finish);
                        // которую мы рассматриваем
                        int index = this.openListIndexOf(top);//получаем её индекс в открытом списке
                        if(this.closedListContains(top)){
                            //если она в закрытом списке, то пропускаем
                        }
                        else if(index != -1){//если она есть в открытом списке, то
                            TheContentsOfTheCell old = (TheContentsOfTheCell) this.g_openList.get(index);//создаем вершину, точнее достаем её из открытого списка
                            //если предыдущий родитель находится дальше от старта, чем новый, то...
                            if(old.getParent().getCostFromStart() > top.getParent().getCostFromStart()){
                                this.g_openList.set(index, top);//меняем старое значение на новое
                            }
                        }
                        else{
                            TheContentsOfTheCell open = new TheContentsOfTheCell.Close(top.getLocation());
                            grid.setObject(top.getLocation().x,top.getLocation().y,open);
                            this.g_openList.add(top);//если вершины не было, то добавляем в откр список её
                        }
                    }
                }
                catch(ArrayIndexOutOfBoundsException ae){//учитываем ошибку:
                    //когда мы пытаемся обратиться к элементу массива
                    //по отрицательному или превышающему размер массива индексу
                    System.out.println(ae);
                }
            }
        }
    }

    private int openListIndexOf(TheContentsOfTheCell top){//возвращает индекс из открытого списка вершин
        for(int index = 0; index < this.g_openList.size(); index++){
            TheContentsOfTheCell anotherTop = (TheContentsOfTheCell) this.g_openList.get(index);
            if(anotherTop.equals(top)){
                return index;
            }
        }
        return -1;
    }

    private boolean closedListContains(TheContentsOfTheCell top){//проверка находится ли вершина в закрытом списке!!!
        for(int index = 0; index < this.g_closedList.size(); index++){//проход по закрытым вершинам
            TheContentsOfTheCell anotherTop = (TheContentsOfTheCell) this.g_closedList.get(index);
            if(anotherTop.equals(top)){
                return true;
            }
        }
        return false;
    }

    private TheContentsOfTheCell lookingForBestTop(){//поиск лучшего узла сетки
        if(this.g_openList.isEmpty()){//проверка что список открытых вершин не пуст
            return null;
        }
        int lowestCostIndex = 0;// индекс наименьшей стоимости
        float cost = ((TheContentsOfTheCell)this.g_openList.get(0)).getTotalCost();//изначально оценка =...
        for(int index = 1; index < this.g_openList.size(); index++){//проходимся по вершинам из открытого списка
            TheContentsOfTheCell top = (TheContentsOfTheCell) this.g_openList.get(index);
            if(top.getTotalCost() < cost){//если оценка из вершины меньше чем была, то
                cost = top.getTotalCost();//изменяем оценку на наименьшую(она же наилучшая)
                lowestCostIndex = index;//меняем индекс
            }
        }
        TheContentsOfTheCell top = (TheContentsOfTheCell) this.g_openList.remove(lowestCostIndex);
        //после прохода:
        // удаляем вершину которая оказалась лучшей из списка открытых вершин
        return top;
    }

    private LinkedList constructPath(TheContentsOfTheCell finish){//метод построения пути!
        LinkedList path = new LinkedList();//создаем новый список
        while(finish != null){
            path.addFirst(finish);//добавляем в путь вершину
            finish = finish.getParent();//и получаем её родителя
        }
        return path;
    }

}