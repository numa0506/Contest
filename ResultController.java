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


public class ResultController implements Initializable {
  Stage stage;
  @FXML public Label scoreShow;
  @FXML public Label timeShow;
  @FXML public Label itemShow;

  public String castToString(int i){
    return String.format("%02d", i);
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    //int itemCount = 0;
    int itemCount = MapData.itemCount();
    int item = itemCount *30;
    int time = 120 - TimerController.getInstance().getElapsedSec();
    String jikan  = TimerController.getInstance().getTime();
    int score = time + item;
    timeShow.setText(jikan);
    scoreShow.setText(castToString(score));
    itemShow.setText(castToString(itemCount));
  }

  public void startbtn(ActionEvent event){
    try{

      MapGame.getInstance().startGame();

    }catch(Exception e){e.printStackTrace();}

  }
}
