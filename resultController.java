import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class resultController extends Application {
    Stage stage;




    public void startbtn(ActionEvent event){
      try{
        MapGame.getInstance().startGame();
      }catch(Exception e){

      }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
	stage = primaryStage;
	primaryStage.setTitle("result");
	Pane myPane_top = (Pane)FXMLLoader.load(getClass().getResource("result.fxml"));
	Scene myScene = new Scene(myPane_top);
	primaryStage.setScene(myScene);
	primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
