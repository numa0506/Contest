import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;

/**
*@author Group1
*/
public class ResultController implements Initializable {
    Stage stage;
    @FXML public Label scoreShow;
    @FXML public Label timeShow;
    @FXML public Label itemShow;

    /**
    *引数を2桁、0埋め、右詰めの文字列に変換する。
    *@param i 数字
    *@return 変換後の文字列
    */
    public String castToString(int i){
        return String.format("%02d", i);
    }

    /**
    *経過時間や取得したアイテム数からスコアを算出する。
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int itemCount = MapData.itemCount();
        int item = itemCount *30;
        int time = 120 - TimerController.getInstance().getElapsedSec();
        String jikan  = TimerController.getInstance().getTime();
        int score = time + item;
        timeShow.setText(jikan);
        scoreShow.setText(castToString(score));
        itemShow.setText(castToString(itemCount));
    }

    /**
    *ボタン操作を受け、次のステージへ進む。
    *ストーリー画面を表示している間、タイマーを止める。
    *@param event 入力
    */
    public void startbtn(ActionEvent event){
        try{
            MapGame.getInstance().startGame();
            MapGame.getInstance().setStory();
            MapGame.getInstance().subStage.show();
            TimerController.getInstance().stopTimer();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
