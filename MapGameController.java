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

/**
*@author Group1
*/
public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;

    /**
    *コントローラを初期化する。
    *適切なマップデータを選択し、ゲーム画面を設定する。
    *@param url ルートオブジェクトの相対パスの解決に使用される場所
    *@param rb ルートオブジェクトのローカライズに使用されるリソース
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int stageIndex = MapGame.getInstance().getStageCounter();
        if (stageIndex == 1){
            mapData = new MapData1(21,15);
        }
        else if(stageIndex == 2){
            mapData = new MapData2(21,15);
        }
        else if(stageIndex == 3){
            mapData = new MapData3(21,15);
        }
        else {
            System.exit(0);
        }
        chara = new MoveChara(1,1,mapData);
        mapImageViews = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x,y);
            }
        }
        mapPrint(chara, mapData);
    }

    /**
    *配列mapImageViews[]の参照先を設定する。
    */
    public void imageRestore(){
        for(int y = 0; y < mapData.getHeight(); y++){
            for(int x = 0; x < mapData.getWidth(); x++){
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }
    }

    /**
    *マップとキャラクターを表示する。
    *@param c キャラクター
    *@param m マップデータ
    */
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

    /**
    *引数で指定された座標以外を黒塗りする。
    *@param cx x座標
    *@param cy y座標
    *@param m マップデータ
    */
    public void removeBlack(int cx,int cy,MapData m){
        m.fillBlack();
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

    /**
    *キャラクターの現在の座標がゴールの座標と一致していればリザルト画面に遷移する。
    */
    public void judgeAndGoal(){
        if(chara.getPosX() == 19 && chara.getPosY() == 13){
            MapGame.getInstance().showResult();
            mapPrint(chara, mapData);
            chara.goal();
        }
    }

    /**
    *タイマーを止め、ダイアログウィンドウを表示する。
    *@param event 入力
    */
    public void func1ButtonAction(ActionEvent event) {
        TimerController.getInstance().stopTimer();
        showInfo("press \"OK\" to restart", "PAUSE");
        TimerController.getInstance().startTimer();
    }
    public void func2ButtonAction(ActionEvent event) { }
    public void func3ButtonAction(ActionEvent event) { }
    public void func4ButtonAction(ActionEvent event) { }

    /**
    *キーボードからの入力を受け付ける。
    */
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

    /**
    *プレイヤーの移動や方向転換の様子をコンソールに出力する。
    *@param actionString
    */
    public void outputAction(String actionString) {
        System.out.println("Select Action: " + actionString);
    }

    /**
    *移動したことをコンソールに表示し、キャラの向きと座標を設定。マップの表示を更新し、ゴールしたかどうかを判定する。
    *以下、他の方向についても同様の内容。
    */
    public void downButtonAction(){
        outputAction("DOWN");
        chara.setCharaDir(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        mapPrint(chara, mapData);
        judgeAndGoal();
    }

    /**
    *ボタンのクリックではなく、キーの押下で操作されたときのためのメソッド。
    */
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

    /**
    *引数に従ってダイアログボックスを表示する。
    *@param message 表示するメッセージ
    *@param title ウィンドウのタイトル
    */
    public void showInfo(String message, String title){
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle(title);
        info.setHeaderText(null);
        info.setContentText(message);
        info.setX(500);
        info.showAndWait();
    }
}
