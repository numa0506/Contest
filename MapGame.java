import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import java.io.File;
import javafx.scene.media.AudioClip;
import java.net.MalformedURLException;


public class MapGame extends Application {
  public static Stage stage;
  public static MapGame startInst;
  public static int stageCounter = 0;

  @Override
  public void start(Stage primaryStage) throws Exception {
    try{
      stage = primaryStage;
      startInst = this;
      stage.setTitle("MAP GAME");
      Pane myPane_top = (Pane)FXMLLoader.load(getClass().getResource("Title.fxml"));
      Scene title = new Scene(myPane_top);
      stage.setScene(title);

      Stage subStage = new Stage();
      Button subStageButton = new Button("NEXT");
      Label subStageLabel = new Label("時代はドッグ帝国との戦時下。あなたは帝国の機密文書を入手することに成功した！\nしかし、逃走する際に見つかってしまう。一刻も早く自国に文書を持ち帰ろう！");
      BorderPane myPane_sub = new BorderPane(subStageLabel);
      myPane_sub.setCenter(subStageLabel);
      myPane_sub.setBottom(subStageButton);
      Scene subScene = new Scene(myPane_sub, 320, 240);
      subStage.setWidth(640);
      subStage.setHeight(300);
      subStage.setTitle("MAP GAME2");
      subStage.setScene(subScene);

      subStageButton.setOnAction((ActionEvent) -> {
          subStageButton.getScene().getWindow().hide();
      });

      stage.show();
      subStage.show();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  public void startGame() throws Exception{//なんのメソッドなのかわからないけど流用させてもらいます　長沼
    stageCounter++;
    Scene game = new Scene((Pane)FXMLLoader.load(getClass().getResource("MapGame.fxml")));
    stage.setScene(game);
  }

  public void showResult(){
    Scene result = setScene("result.fxml");
    stage.setScene(result);
  }

  public static MapGame getInstance(){
    return startInst;
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
