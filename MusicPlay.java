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
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import javafx.scene.media.AudioClip;
import java.net.MalformedURLException;
import javafx.event.ActionEvent;

public class MusicPlay {
  // used by MapGame.java
  // set
  public static Media bgm = new Media(new File("sound/bgm.wav").toURI().toString());
  public static Media sp1 = new Media(new File("sound/wolf.wav").toURI().toString());
  public static Media sp2 = new Media(new File("sound/wind.wav").toURI().toString());
  public static Media sp3 = new Media(new File("sound/fire.wav").toURI().toString());


  public static void playBgm(){
    MediaPlayer bgm_play = new MediaPlayer(bgm);
    // 無限ループ
    bgm_play.setCycleCount(MediaPlayer.INDEFINITE);
    bgm_play.setAutoPlay(true);
  }
  public static void playSp1(){
    MediaPlayer sp1_play = new MediaPlayer(sp1);
    sp1_play.play();
  }
  public static void playSp2(){
    MediaPlayer sp2_play = new MediaPlayer(sp2);
    sp2_play.play();
  }
  public static void playSp3(){
    MediaPlayer sp3_play = new MediaPlayer(sp3);
    sp3_play.play();
  }
}
