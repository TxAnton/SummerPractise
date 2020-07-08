package Model;

public class Memento implements IMemento {
    private final Grid previous;
    private final TheContentsOfTheCell m_grid[][];
    private final int m_width;//ширина
    private final int m_height;//высота

    public Memento(Grid grid){//конструктор
        this.previous = grid;
        this.m_width = grid.getWidth();
        this.m_height = grid.getHeight();
        this.m_grid = new TheContentsOfTheCell[grid.getHeight()][grid.getWidth()];
        for(int y = 0; y < grid.getHeight(); y++)
            for(int x = 0; x < grid.getWidth(); x++)
                this.m_grid[y][x] = grid.getObject(x,y);
    }

    public Grid getGrid(){
        return this.previous;
    }

    public TheContentsOfTheCell getObject(int x, int y){
        return this.m_grid[y][x];
    }
}
