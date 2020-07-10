package groupid1;

import Controller.IController;
import Model.Grid;
import Model.Memento;
import Model.States.Visualisator;
import Model.TheContentsOfTheCell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;


public class UIController implements Visualisator {

    public
    int width;
    @FXML
    public javafx.scene.control.MenuItem menuOpen;
    @FXML
    public javafx.scene.control.MenuItem menuSave;
    @FXML
    public javafx.scene.control.MenuItem menuNew;
    @FXML
    public javafx.scene.control.MenuItem menuReset;
    @FXML
    public javafx.scene.control.MenuItem menuAbout;
    @FXML
    public javafx.scene.control.MenuItem menuHelp;
    @FXML
    public ToggleButton buttonPlay;
    @FXML
    public TextField textStep;
    @FXML
    public CheckBox checkBoxForward;
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

    int height;
    double lineWidth;
    double tile;
    Point pressPoint;
    Point releasePoint;
    CellType curType;
    IController iController;
    //--------Interface implementation
    DialogController dialogController;
    Timeline playTimer;

    public UIController() {
        width = 10;
        height = 10;
        tile = 0;

        //drawGrid();
    }

    @Override
    public void sendPath(LinkedList<TheContentsOfTheCell> path) {
        for (TheContentsOfTheCell cell : path) {
            drawCell((int) cell.getLocation().getX(), (int) cell.getLocation().getY(), CellType.PATH);


            TheContentsOfTheCell parent = cell.getParent();
            if (parent != null) {


                Point loc = cell.getLocation();
                Point ploc = parent.getLocation();

                drawArrow((int) ((ploc.getX() + 1) * tile - tile / 2), (int) ((ploc.getY() + 1) * tile - tile / 2), (int) ((loc.getX() + 1) * tile - tile / 2), (int) ((loc.getY() + 1) * tile - tile / 2), Color.RED);
            }
        }

        for (TheContentsOfTheCell cell : path) {
        }


    }

    @Override
    public void sendMemento(Memento memento) {
        assert (memento.getGrid().getHeight() == height);
        assert (memento.getGrid().getWidth() == width);
        Grid grid = memento.getGrid();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                TheContentsOfTheCell cell = memento.getObject(i, j);
                if (cell.isObstacle()) {
                    //drawCell((int)cell.getLocation().getX(),(int)cell.getLocation().getY(), CellType.WALL,true);
                    drawCell((int) i, (int) j, CellType.WALL);
                } else if (cell.getType() == 6) {
                    drawCell((int) i, (int) j, CellType.CLOSE);
                } else if (cell.getType() == 3) {
                    drawCell((int) i, (int) j, CellType.START);
                } else if (cell.getType() == 4) {
                    drawCell((int) i, (int) j, CellType.END);
                } else if (cell.getType() == 5) {
                    drawCell((int) i, (int) j, CellType.OPEN);
                }

                /*else if(cell.getType() == 7){
                    drawCell((int)i,(int)j, CellType.PATH,true);
                }*/

                else if (true) {
                    drawCell((int) i, (int) j, CellType.BLANK);
                }

            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                TheContentsOfTheCell cell = memento.getObject(i, j);
                if (cell.getParent() != null) {

                    TheContentsOfTheCell parent = cell.getParent();
                    Point loc = cell.getLocation();
                    Point ploc = parent.getLocation();

                    drawArrow((int) ((ploc.getX() + 1) * tile - tile / 2), (int) ((ploc.getY() + 1) * tile - tile / 2), (int) ((i + 1) * tile - tile / 2), (int) ((j + 1) * tile - tile / 2), Color.DARKGRAY);
                }

            }
        }







        /*try {
            Point st = grid.getObjectPoint(TheContentsOfTheCell.Start.class.getName());
            drawCell((int)st.getX(),(int)st.getY(), CellType.START,true);

            st = grid.getObjectPoint(TheContentsOfTheCell.Finish.class.getName());
            drawCell((int)st.getX(),(int)st.getY(), CellType.END,true);

            st = grid.getObjectPoint(TheContentsOfTheCell.Close.class.getName());
            drawCell((int)st.getX(),(int)st.getY(), CellType.CLOSE,true);

            st = grid.getObjectPoint(TheContentsOfTheCell.Empty.Close.class.getName());
            drawCell((int)st.getX(),(int)st.getY(), CellType.OPEN,true);

        }catch (Exception e){
            System.err.println(e);
            System.err.println(e.getMessage());

        }*/
    }


    public IController getiController() {
        return iController;
    }

    public void setiController(IController iController) {
        this.iController = iController;
    }

    boolean isControllerSet() {
        if (iController == null) {
            System.err.println("Visualiser: Controller Not Set!");
            return false;
        }
        return true;
    }

    public void init() {
        curType = CellType.WALL;
        width = 25;
        height = 20;
        lineWidth = 2;
        tile = Math.min(mainCanvas.getWidth() / width, mainCanvas.getHeight() / height);
        drawGrid();

        if (isControllerSet()) {
            iController.setGrid(width, height);
        }

        //drawArrow(5, 5, 50, 50, Color.BLUE);


    }


    @FXML
    public void OnResetClicked() {
        if (isControllerSet()) iController.resetAlgorithm();
    }

    @FXML
    public void OnOpenClicked() {
        if (isControllerSet()) iController.loadGraph();
    }


    @FXML
    public void OnCanvasClicked(javafx.scene.input.MouseEvent event) {
        /*
        System.out.println(""+event.getX()+" "+event.getY());
        System.out.println(Math.min((int)mainCanvas.getWidth()/width,(int)mainCanvas.getHeight()/height));

        GraphicsContext gc  =mainCanvas.getGraphicsContext2D();
*/
        //drawCell((int)(event.getX()/tile),(int)(event.getY()/tile), CellType.PATH,true);

        if (isControllerSet())
            iController.mousePressed((int) (event.getX() / tile), (int) (event.getY() / tile), curType);

    }

    @FXML
    public void OnButtonBlankClicked() {
        curType = CellType.BLANK;
        //iController.mousePressed();
        //if(isControllerSet())iController.setStateOfDelete();

    }

    @FXML
    public void OnButtonWallClicked() {
        curType = CellType.WALL;
//        if(isControllerSet())iController.setStateOfAddingBlock();

    }

    @FXML
    public void OnButtonStartClicked() {
        curType = CellType.START;
        //if(isControllerSet())iController.setStartState();

    }

    @FXML
    public void OnButtonEndClicked() {
        curType = CellType.END;
        //if(isControllerSet())iController.setFinishState();

    }

    @FXML
    public void OnNextClicked() {
        if (isControllerSet()) {
            iController.nextStep();
        }
        //System.out.println("OnNextClicked()");
    }

    @FXML
    public void OnPrevClicked() {
        if (isControllerSet()) iController.backStep();


    }

    @FXML
    public void OnSaveClicked() {
        if (isControllerSet()) iController.saveGraph();
    }

    @FXML
    public void OnNewClicked() {
        dialogController = new DialogController(this);
    }

    @FXML
    public void OnPlayClicked() {


        if (buttonPlay.isSelected()) {
            try {
                playTimer.stop();
            } catch (Exception e) {
            }
            playTimer = new Timeline(new KeyFrame(Duration.millis(Integer.parseInt(textStep.getCharacters().toString())), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (checkBoxForward.isSelected()) {
                        OnNextClicked();
                    } else {
                        OnPrevClicked();
                    }
                }
            }));
            playTimer.setCycleCount(Timeline.INDEFINITE);
            playTimer.play();
        } else {
            try {
                playTimer.stop();
            } catch (Exception e) {
            }
        }


    }

    @FXML
    public void OnAboutClicked() {
        Parent root;
        URL xmlUrl;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            xmlUrl = getClass().getResource("/About.fxml");
            loader.setLocation(xmlUrl);
            root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("About");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void OnHelpClicked() {
        Parent root;
        URL xmlUrl;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            xmlUrl = getClass().getResource("/Help.fxml");
            loader.setLocation(xmlUrl);
            root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Help");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newField(int width, int height, FieldType fieldType) {
        clearField();
        this.width = width;
        this.height = height;
        tile = Math.min(mainCanvas.getWidth() / width, mainCanvas.getHeight() / height);
        drawGrid();

        if (isControllerSet()) {
            iController.setGrid(width, height);
        }
    }

    @FXML
    public void OnCanvasPressed(javafx.scene.input.MouseEvent event) {
        if (curType != CellType.END && curType != CellType.START)
            pressPoint = new Point((int) event.getX(), (int) event.getY());
    }

    @FXML
    public void OnCanvasReleased(javafx.scene.input.MouseEvent event) {
        if (pressPoint != null) {
            releasePoint = new Point((int) event.getX(), (int) event.getY());
            /*
            pressPoint=new Point((int)(pressPoint.x),(int)(pressPoint.y));
            releasePoint=new Point((int)(releasePoint.x),(int)(releasePoint.y));
            */

            int steps = (int) (mainCanvas.getHeight() + mainCanvas.getWidth());
            double r = 1;
            HashSet<Point> s = new HashSet<Point>();

            for (int i = 0; i <= steps; i++) {
                double ratio = ((double) i) / steps;
                double dx = releasePoint.getX() - pressPoint.getX();
                double dy = releasePoint.getY() - pressPoint.getY();

                Point cur = new Point((int) pressPoint.getX(), (int) pressPoint.getY());
                cur = new Point((int) (cur.getX() + (dx * ratio)), (int) (cur.getY() + (dy * ratio)));
                Point pt = new Point((int) (cur.getX() / tile), (int) (cur.getY() / tile));
                if (!s.contains(pt)) {
                    s.add(pt);
                    iController.mousePressed((int) pt.getX(), (int) pt.getY(), curType);
                }
                //iController.mousePressed((int) (cur.getX() / tile), (int) (cur.getY() / tile), curType);

                /*
                for(int xx=(int)(cur.getX()-r);xx<=cur.getX()+r;xx++){
                    for(int yy=(int)(cur.getY()-r);yy<=cur.getY()+r;yy++){

                    }
                }

                 */
            }


            pressPoint = releasePoint = null;
        }

    }

    private void drawGrid() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setLineWidth(lineWidth);
        gc.stroke();
        gc.setStroke(Color.BLACK);

        for (int i = 0; i < width + 1; i++) {
            gc.beginPath();
            gc.moveTo(i * tile, 0);
            gc.lineTo(i * tile, height * tile);
            gc.stroke();

        }
        for (int i = 0; i < height + 1; i++) {
            gc.beginPath();
            gc.moveTo(0, i * tile);
            gc.lineTo(width * tile, i * tile);
            gc.stroke();
        }
    }

    private void clearField() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setLineWidth(0);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

    }

    void drawArrow(int x1, int y1, int x2, int y2, Color color) {

        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        var tr = gc.getTransform();
        int ARR_SIZE = 8;
        gc.setFill(color);
        gc.setStroke(color);

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) (Math.sqrt(dx * dx + dy * dy) * 0.90);

        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.strokeLine(0, 0, len, 0);
        gc.fillPolygon(new double[]{len, len - ARR_SIZE, len - ARR_SIZE, len}, new double[]{0, -ARR_SIZE, ARR_SIZE, 0},
                4);
        gc.setTransform(tr);
    }

    private void drawCell(int x, int y, CellType cellType) {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setLineWidth(lineWidth);
        gc.setStroke(Color.BLACK);
        switch (cellType) {

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
        gc.fillRect(x * tile, y * tile, tile, tile);
        if (true) gc.strokeRect(x * tile, y * tile, tile, tile);
        //drawGrid();
    }

    public enum CellType {
        BLANK,
        WALL,
        START,
        END,
        OPEN,
        CLOSE,
        PATH
    }

    public enum FieldType {
        BLANK,
        FILLED,
        RANDOM,
        LABYRINTH
    }



    /*
    Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("this is called every 5 seconds on UI thread");
        }
    }));
    */


}
