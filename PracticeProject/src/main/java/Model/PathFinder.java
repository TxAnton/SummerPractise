package Model;

import java.awt.Point;
import java.util.LinkedList;

//----------------------------- Класс поиска пути -----------------------------
public class PathFinder {
    //в классе поиска пути хранятся список открытых и закрытх вершин!
    private LinkedList g_openList;
    private LinkedList g_closedList;

    public PathFinder(){
        this.g_openList = new LinkedList();
        this.g_closedList = new LinkedList();
    }

    public LinkedList findPath(Grid grid) throws NullPointerException, ClassNotFoundException{
        this.g_openList.clear();//очищаем списки
        this.g_closedList.clear();
        //небольшое уточнение
        //TheContentsOfTheCell.Start.class.getName() -
        //возвращает имя класса, представленной этим объектом класса, в виде строки.
        //далее с помощью метода getObjectPoint создастся точка strat а затем и finish
        Point start = new Point(grid.getObjectPoint(TheContentsOfTheCell.Start.class.getName()));
        Point finish = new Point(grid.getObjectPoint(TheContentsOfTheCell.Finish.class.getName()));
        Top finishTop = new Top(finish, null, null);
        Top startTop = new Top(start, null, null);
        Top temp = new Top(start, null, finishTop);

        this.g_openList.add(temp);//добавляем в список откр вершин начальную вершину
        while(!this.g_openList.isEmpty()){//пока список откр вершин не пуст...
            if(temp.getLocation().equals(finish)){//если расположение текущей точки = finish
                return this.constructPath(temp);//то строим путь!!!
            }
            //иначе
            temp = this.lookingForBestTop();//текущая вершина = лучшей из доступный
            this.g_closedList.addLast(temp);//добавляем её в закрытый список
            this.addNeighbor(temp, startTop, finishTop, grid);
            //вызываем метод добавляения соседней вершины
        }

        return null;
    }

    private void addNeighbor(Top parent, Top start, Top finish, Grid grid){
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
                        Top top = new Top(new Point(newX, newY), parent, finish);//создаем новую вершину,
                        // которую мы рассматриваем
                        int index = this.openListIndexOf(top);//получаем её индекс в открытом списке
                        if(this.closedListContains(top)){
                            //если она в закрытом списке, то пропускаем
                        }
                        else if(index != -1){//если она есть в открытом списке, то
                            Top old = (Top) this.g_openList.get(index);//создаем вершину, точнее достаем её из открытого списка
                            //если предыдущий родитель находится дальше от старта, чем новый, то...
                            if(old.getParent().getCostFromStart() > top.getParent().getCostFromStart()){
                                this.g_openList.set(index, top);//меняем старое значение на новое
                            }
                        }
                        else{
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

    private int openListIndexOf(Top top){//возвращает индекс из открытого списка вершин
        for(int index = 0; index < this.g_openList.size(); index++){
            Top anotherTop = (Top) this.g_openList.get(index);
            if(anotherTop.equals(top)){
                return index;
            }
        }
        return -1;
    }

    private boolean closedListContains(Top top){//проверка находится ли вершина в закрытом списке!!!
        for(int index = 0; index < this.g_closedList.size(); index++){//проход по закрытым вершинам
            Top anotherTop = (Top) this.g_closedList.get(index);
            if(anotherTop.equals(top)){
                return true;
            }
        }
        return false;
    }

    private Top lookingForBestTop(){//поиск лучшего узла сетки
        if(this.g_openList.isEmpty()){//проверка что список открытых вершин не пуст
            return null;
        }
        int lowestCostIndex = 0;// индекс наименьшей стоимости
        float cost = ((Top)this.g_openList.get(0)).getTotalCost();//изначально оценка =...
        for(int index = 1; index < this.g_openList.size(); index++){//проходимся по вершинам из открытого списка
            Top top = (Top) this.g_openList.get(index);
            if(top.getTotalCost() < cost){//если оценка из вершины меньше чем была, то
                cost = top.getTotalCost();//изменяем оценку на наименьшую(она же наилучшая)
                lowestCostIndex = index;//меняем индекс
            }
        }
        Top top = (Top) this.g_openList.remove(lowestCostIndex);
        //после прохода:
        // удаляем вершину которая оказалась лучшей из списка открытых вершин
        return top;
    }

    private LinkedList constructPath(Top finish){//метод построения пути!
        LinkedList path = new LinkedList();//создаем новый список
        while(finish != null){
            path.addFirst(finish);//добавляем в путь вершину
            finish = finish.getParent();//и получаем её родителя
        }
        return path;
    }

    public class Top {
        private Top g_parent;
        private Point g_location;//её расположение на сетке( Х и У )
        private float g_costFromStart;//функция(g) - расстояние от начальной вершины
        private float g_costToFinish;//функция(h) - оценка расстояния до финиша

        public Top(Point location, Top parent, Top finish){
            this.g_parent = parent;
            this.g_location = location;
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

        //ниже это функция g(v) - наименьшая стоимость пути в v из стартовой вершины
        private float calculateCostFromStart(Top parent){
            float x = this.getLocation().x - parent.getLocation().x;
            float y = this.getLocation().y - parent.getLocation().y;
            float sum = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            sum += this.getParent().getCostFromStart();
            return sum;
        }
        //то есть сначала родитель будет - start, а затем и другие пойдут
        //и это всё будет суммироваться

        public boolean equals(Top top){
            return this.g_location.equals(top.getLocation());
        }

        public Top getParent(){
            return this.g_parent;
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
    }
}