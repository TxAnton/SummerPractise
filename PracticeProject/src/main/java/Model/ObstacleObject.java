package Model;


//----------------------------- Класс объекта, из которого сост сетка -----------------------------
public abstract class ObstacleObject {
    private boolean g_isObstacle;//есть препятствие или нет

    public ObstacleObject(boolean isObstacle){
        this.setObstacle(isObstacle);
    }

    public void setObstacle(boolean isObstacle){
        this.g_isObstacle = isObstacle;
    }

    public boolean isObstacle(){
        return this.g_isObstacle;
    }


}