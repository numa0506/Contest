import java.net.URL;
import java.util.ResourceBundle;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;

public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;
    public ButtonType button_Yes = new ButtonType("Yes", ButtonData.YES);
    public ButtonType button_No = new ButtonType("No", ButtonData.NO);
    //    public Group[] mapGroups;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if ( MapGame.stageCounter== 1){
            mapData = new MapData1(21,15);
        }
        else if(MapGame.stageCounter == 2){
            mapData = new MapData2(21,15);
        }
        else if(MapGame.stageCounter == 3){
            mapData = new MapData3(21,15);
        }
        else {
            System.exit(0);
        }
        chara = new MoveChara(1,1,mapData);
        //        mapGroups = new Group[mapData.getHeight()*mapData.getWidth()];
        mapImageViews = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x,y);
            }
        }
        mapPrint(chara, mapData);
    }

    public void imageRestore(){
        for(int y = 0; y < mapData.getHeight(); y++){
            for(int x = 0; x < mapData.getWidth(); x++){
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }
    }

    public void mapPrint(MoveChara c, MapData m){
        int cx = c.getPosX();
        int cy = c.getPosY();
        removeBlack(cx, cy, m);
        imageRestore();
        mapGrid.getChildren().clear();
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                if (x==cx && y==cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                } else {
                    mapGrid.add(mapImageViews[index], x, y);
                }
            }
        }
    }

    public void resetMapIfGoal(){//Restart MapGame if catch GOAL
        if(chara.goal()){
            mapData = new MapData(21,15);
            chara = new MoveChara(1,1,mapData);
            //           mapGroups = new Group[mapData.getHeight()*mapData.getWidth()];
            mapImageViews = new ImageView[mapData.getHeight()*mapData.getWidth()];
            for(int y=0; y<mapData.getHeight(); y++){
                for(int x=0; x<mapData.getWidth(); x++){
                    int index = y*mapData.getWidth() + x;
                    mapImageViews[index] = mapData.getImageView(x,y);
                }
            }
            mapPrint(chara, mapData);
        }
    }

    public void removeBlack(int cx,int cy,MapData m){
        for(int dy=-1;dy<=1;dy++){
            for(int dx=-1;dx<=1;dx++){
                try{
                    m.setBlackOut(cx+dx,cy+dy);
                }catch(Exception e){
                    continue;
                }
            }
        }
        m.setImageViews();
    }

    public void judgeAndGoal(){
        if(chara.getPosX() == 19 && chara.getPosY() == 13){
            MapGame.getInstance().showResult();
            mapPrint(chara, mapData);
            chara.goal();
        }
    }

    public void func1ButtonAction(ActionEvent event) {
        TimerController.getInstance().stopTimer();
        showInfo("press \"OK\" to restart", "PAUSE");
        TimerController.getInstance().startTimer();
    }
    public void func2ButtonAction(ActionEvent event) { }
    public void func3ButtonAction(ActionEvent event) { }
    public void func4ButtonAction(ActionEvent event) { }

    public void keyAction(KeyEvent event){
        KeyCode key = event.getCode();
        if (key == KeyCode.DOWN){
            downButtonAction();
        }else if (key == KeyCode.RIGHT){
            rightButtonAction();
        }else if (key == KeyCode.LEFT){
            leftButtonAction();
        }else if (key == KeyCode.UP){
            upButtonAction();
        }
    }

    public void outputAction(String actionString) {
        System.out.println("Select Action: " + actionString);
    }

    public void downButtonAction(){
        outputAction("DOWN");
        chara.setCharaDir(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        mapPrint(chara, mapData);
        judgeAndGoal();
    }
    public void downButtonAction(ActionEvent event) {
        downButtonAction();
    }

    public void rightButtonAction(){
        outputAction("RIGHT");
        chara.setCharaDir(MoveChara.TYPE_RIGHT);
        chara.move( 1, 0);
        mapPrint(chara, mapData);
        judgeAndGoal();
    }
    public void rightButtonAction(ActionEvent event) {
        rightButtonAction();
    }

    public void leftButtonAction(){
        outputAction("LEFT");
        chara.setCharaDir(MoveChara.TYPE_LEFT);
        chara.move( -1, 0);
        mapPrint(chara, mapData);
        judgeAndGoal();
    }
    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }

    public void upButtonAction(){
        outputAction("UP");
        chara.setCharaDir(MoveChara.TYPE_UP);
        chara.move( 0, -1);
        mapPrint(chara, mapData);
        judgeAndGoal();
    }
    public void upButtonAction(ActionEvent event) {
        upButtonAction();
    }

    public boolean isYes(String question, String title){
        Alert dialog = new Alert(AlertType.NONE, question, button_Yes, button_No);
        dialog.setTitle(title);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == button_Yes){
            return true;
        }else{
            return false;
        }
    }

    public void showInfo(String message, String title){
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle(title);
        info.setHeaderText(null);
        info.setContentText(message);
        info.showAndWait();
    }
}
