package groupid1;

import Model.Grid;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.paint.Color;


import Controller.IController;

import Model.States.Visualisator;
import Model.TheContentsOfTheCell;
import Model.Memento;

import java.util.LinkedList;


public class UIController implements Visualisator {
    @Override
    public void sendMemento(Memento memento) {
        assert (memento.getGrid().getHeight()==height);
        assert (memento.getGrid().getWidth()==width);

        for(int i =0; i<width;i++){
            for(int j =0; j<height;i++){
                TheContentsOfTheCell cell = memento.getObject(i,j);
                if(cell.getCostFromStart()==0){
                    drawCell((int)cell.getLocation().getX(),(int)cell.getLocation().getY(), CellType.START,true);
                }else if ((cell.getCostToFinish()==0)){
                    drawCell((int)cell.getLocation().getX(),(int)cell.getLocation().getY(), CellType.END,true);
                }else if(cell.isObstacle()){
                    drawCell((int)cell.getLocation().getX(),(int)cell.getLocation().getY(), CellType.WALL,true);
                } else if(true){
                    drawCell((int)cell.getLocation().getX(),(int)cell.getLocation().getY(), CellType.BLANK,true);
                }
            }
        }
    }

    @Override
    public void sendPath(LinkedList<TheContentsOfTheCell> path) {
        for(TheContentsOfTheCell cell:path){
            drawCell((int)cell.getLocation().getX(),(int)cell.getLocation().getY(), CellType.PATH,true);
        }
    }

    enum CellType{
    BLANK,
    WALL,
    START,
    END,
    OPEN,
    CLOSE,
    PATH
    }

    IController iController;

    public IController getiController() {
        return iController;
    }

    public void setiController(IController iController) {
        this.iController = iController;
    }

    boolean isControllerSet(){
        if(iController==null){
            System.err.println("Visualiser: Controller Not Set!");
            return false;
        }
        return true;
    }

    @FXML
    public Canvas mainCanvas;
    @FXML
    public Button nextButton;
    @FXML
    public Button prevButton;
    @FXML
    public Button buttonBlank;
    @FXML
    public Button buttonWall;
    @FXML
    public Button buttonStart;
    @FXML
    public Button buttonEnd;
    @FXML
    public Button buttonReset;
    @FXML
    public Button buttonShow;



    public
    int width;
    int height;
    double lineWidth;
    double tile;
    boolean begun;


    public UIController() {
        width = 10;
        height = 10;
        tile = 0;

        //drawGrid();
    }

    public void init(){
        begun = false;
        width = 25;
        height = 20;
        lineWidth=2;
        tile = Math.min(mainCanvas.getWidth()/width, mainCanvas.getHeight()/height);
        drawGrid();

        if(isControllerSet()){
            iController.setGrid(width,height);
        }

        drawCell(0,0,CellType.BLANK,true);
        drawCell(1,0,CellType.WALL,true);
        drawCell(2,0,CellType.START,true);
        drawCell(3,0,CellType.END,true);
        drawCell(4,0,CellType.OPEN,true);
        drawCell(5,0,CellType.CLOSE,true);
        drawCell(6,0,CellType.PATH,true);
    }

    private void drawGrid(){
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setLineWidth(lineWidth);gc.stroke();

        for(int i=0;i<width+1;i++){
            gc.beginPath();
            gc.moveTo(i*tile,0);
            gc.lineTo(i*tile,height*tile);
            gc.stroke();

        }
        for(int i=0;i<height+1;i++){
            gc.beginPath();
            gc.moveTo(0,i*tile);
            gc.lineTo(width*tile,i*tile);
            gc.stroke();
        }
    }




    @FXML
    public void OnCanvasClicked(javafx.scene.input.MouseEvent event){
        /*
        System.out.println(""+event.getX()+" "+event.getY());
        System.out.println(Math.min((int)mainCanvas.getWidth()/width,(int)mainCanvas.getHeight()/height));

        GraphicsContext gc  =mainCanvas.getGraphicsContext2D();
*/
        drawCell((int)(event.getX()/tile),(int)(event.getY()/tile), CellType.WALL,true);

        if(isControllerSet())iController.mousePressed((int)(event.getX()/tile),(int)(event.getY()/tile));

    }

    @FXML
    public void OnButtonBlankClicked(){
        if(isControllerSet())iController.setStateOfDelete();

    }
    @FXML
    public void OnButtonWallClicked(){
        if(isControllerSet())iController.setStateOfAddingBlock();

    }
    @FXML
    public void OnButtonStartClicked(){
        if(isControllerSet())iController.setStartState();

    }
    @FXML
    public void OnButtonEndClicked(){
        if(isControllerSet())iController.setFinishState();

    }

    @FXML
    public void OnNextClicked(){
        if(!begun){
            if(isControllerSet()) {
                iController.startAlgorithm();
                iController.nextStep();
            }
        }else{
            iController.nextStep();
        }
        //System.out.println("OnNextClicked()");
    }
    @FXML
    public void OnPrevClicked(){
        if(isControllerSet())iController.backStep();


    }

    @FXML
    public void OnResetClicked(){
        if(begun){
            begun=false;
            if(isControllerSet())iController.resetAlgorithm();
        }

    }

    @FXML
    public void OnShowClicked(){
        if(isControllerSet())iController.showAlgorithm();

    }


    private void drawCell(int x, int y, CellType cellType, boolean withStroke){

        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setLineWidth(lineWidth);
        gc.setStroke(Color.BLACK);
        switch (cellType){


            case BLANK:
                gc.setFill(Color.DARKBLUE);
                break;
            case WALL:
                gc.setFill(Color.GRAY);
                break;
            case START:
                gc.setFill(Color.GREEN);
                break;
            case END:
                gc.setFill(Color.RED);
                break;
            case OPEN:
                gc.setFill(Color.CYAN);
                break;
            case CLOSE:
                gc.setFill(Color.BLUE);
                break;
            case PATH:
                gc.setFill(Color.YELLOW);
                break;
        }
        gc.fillRect(x*tile,y*tile,tile,tile);
        if(withStroke)gc.strokeRect(x*tile,y*tile,tile,tile);
        //drawGrid();
    }

}
