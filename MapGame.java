import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.File;
import javafx.scene.media.AudioClip;
import java.net.MalformedURLException;


public class MapGame extends Application {
  public static Stage stage;
  public static MapGame startIns;
  public static Scene title;

  @Override
  public void start(Stage primaryStage) throws Exception {
    try{
      stage = primaryStage;
      startIns = this;
      primaryStage.setTitle("MAP GAME");
      Pane myPane_top = (Pane)FXMLLoader.load(getClass().getResource("Title.fxml"));
      title = new Scene(myPane_top);
      primaryStage.setScene(title);
      primaryStage.show();
    }catch(Exception e){
      e.printStackTrace();
    }


  }
  public void startGame() throws Exception{//なんのメソッドなのかわからないけど流用させてもらいます　長沼
    //Scene game = setScene("MapGame.fxml");
    Scene game = new Scene((Pane)FXMLLoader.load(getClass().getResource("MapGame.fxml")));
    stage.setScene(game);
  }

  public void resultShow(){
    Scene result = setScene("result.fxml");
    stage.setScene(result);
  }

  public static MapGame getInstance(){
    return startIns;
  }

  private Scene setScene(String fxmlName){
    try{
      Pane newPane = (Pane)FXMLLoader.load(getClass().getResource(fxmlName));
      Scene newScene = new Scene(newPane);
      return newScene;
    } catch (Exception e) {
      System.err.println(e);
    }
    return null;
  }

  public static void main(String[] args)throws Exception{
    AudioClip bgm = new AudioClip(new File("sound/loop3.wav").toURI().toString());
    bgm.setCycleCount(AudioClip.INDEFINITE);
    bgm.play();
    launch(args);
  }
}
