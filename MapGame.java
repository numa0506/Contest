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
import java.io.FileNotFoundException;
import javafx.scene.media.AudioClip;
import java.net.MalformedURLException;

/**
*@author Group1
*/
public class MapGame extends Application {
    public static Stage stage;
    public static Stage subStage;
    public static MapGame startInst;
    public static Scene scene;
    public static Scene subScene;
    private int stageCounter = 1;
    private int storyIndex = 1;

    /**
    *タイトル画面とストーリー画面を表示する。
    *@param primaryStage
    */
    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            stage = primaryStage;
            startInst = this;
            stage.setTitle("MAP GAME");
            Pane myPane_top = (Pane)FXMLLoader.load(getClass().getResource("Title.fxml"));
            scene = new Scene(myPane_top);
            stage.setScene(scene);
            subStage = new Stage();
            setStory();
            stage.show();
            subStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
        MusicPlay.playBgm();
    }

    /**
    *stageにゲーム用シーンをセットする。
    */
    public void startGame() throws Exception{
        Scene game = new Scene((Pane)FXMLLoader.load(getClass().getResource("MapGame.fxml")));
        stage.setScene(game);stageCounter++;
    }

    /**
    *ストーリー用のシーンを設定する。
    *fxmlファイルは順番通りにしか呼べない。
    */
    public void setStory() throws Exception{
        try{
            Pane myPane_sub = (Pane)FXMLLoader.load(getClass() .getResource("Story" + storyIndex + ".fxml"));
            subScene = new Scene(myPane_sub);
            subStage.setTitle("page" + (storyIndex - 1));
            subStage.setScene(subScene);
            storyIndex++;
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    /**
    *リザルト画面に遷移する。
    */
    public void showResult(){
        Scene result = setScene("Result.fxml");
        stage.setScene(result);
    }

    /**
    *@return startInst
    */
    public static MapGame getInstance(){
        return startInst;
    }

    /**
    *@return stageCounter
    */
    public int getStageCounter(){
        return stageCounter;
    }

    /**
    *引数に一致する名前のfxmlファイルを探し、同じ階層にあればシーンを生成する。
    *@param fxmlName fxmlファイルの名前
    *@return 生成されたシーン
    */
    private Scene setScene(String fxmlName){
        try{
            Pane newPane = (Pane)FXMLLoader.load(getClass().getResource(fxmlName));
            Scene newScene = new Scene(newPane);
            return newScene;
        }catch (Exception e){
            System.err.println(e);
        }
        return null;
    }
}
