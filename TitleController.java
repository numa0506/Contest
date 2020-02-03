import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.stage.Window;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.Event;

/**
*@author Gruop1
*/
public class TitleController implements Initializable{
    @Override
    public void initialize(URL url, ResourceBundle rb){}

    /**
    *ゲーム画面に遷移する。
    *ストーリー画面を表示している間、タイマーは止まっている。
    */
    public void onStart() throws Exception{
        try{
            MapGame.getInstance().startGame();
            TimerController.getInstance().stopTimer();
            MapGame.getInstance().setStory();
            MapGame.getInstance().subStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
