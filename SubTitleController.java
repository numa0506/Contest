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
public class SubTitleController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
    *プロローグ画面を閉じる。
    */
    public void closePrologue(){
        MapGame.getInstance().subStage.close();
    }

    /**
    *ストーリー画面を閉じ、タイマーを動かす。
    */
    public void closeDescription(){
        MapGame.getInstance().subStage.close();
        TimerController.getInstance().startTimer();
    }
}
