import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

/**
*@author Group1
*/
public class TimerController implements Initializable{
    public static TimerController timerInst;
    private int h;
    private int m;
    private int s;
    private int ms;
    private int secCounter;

    public Label minLabel;
    public Label secLabel;
    public Label msecLabel;

    Timeline msecTimer = new Timeline(
    new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if(ms < 99){
                ms += 1;
            }else{
                ms = 0;
                msecTimer.stop();
                msecTimer.play();
            }
            msecLabel.setText(castToString(ms));
        }
    }));

    Timeline secTimer = new Timeline(
    new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            if(s < 59){
                s += 1;
            }else{
                s = 0;
                secTimer.stop();
                secTimer.play();
            }
            secLabel.setText(castToString(s));
            secCounter++;
        }
    }));

    Timeline minTimer = new Timeline(
    new KeyFrame(Duration.minutes(1), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            if(m < 59){
                m += 1;
            }else{
                m = 0;
                h += 1;
                minTimer.stop();
                minTimer.play();
            }
            if(h == 0){
                minLabel.setText(castToString(m));
            }else{
                minLabel.setText("" + castToString(h) + " : " + castToString(m));
            }
        }
    }));

    /**
    *MapGameControllerの初期化の前に呼び出される。
    *タイマーの表示を初期化し、アニメーションを再生する。
    */
    public void initialize(URL url, ResourceBundle rb){
        initTimer();
        startTimer();
    }

    /**
    *3つのアニメーションのサイクルを無限にし、経過時間などを格納する変数に0を代入。
    *ラベルの表示も「00」にし、timerInstの参照先をこのインスタンスにする。
    */
    public void initTimer(){
        msecTimer.setCycleCount(Timeline.INDEFINITE);
        secTimer.setCycleCount(Timeline.INDEFINITE);
        minTimer.setCycleCount(Timeline.INDEFINITE);
        h = 0;
        m = 0;
        s = 0;
        ms = 0;
        secCounter = 0;
        msecLabel.setText("00");
        secLabel.setText("00");
        minLabel.setText("00");
        timerInst = this;
    }

    /**
    *全てのアニメーションを一括で再生する。
    */
    public void startTimer(){
        msecTimer.play();
        secTimer.play();
        minTimer.play();
    }

    /**
    *全てのアニメーションを一括で停止し、再生ヘッドを先頭に戻す。
    */
    public void stopTimer(){
        msecTimer.stop();
        secTimer.stop();
        minTimer.stop();
    }

    /**
    *インスタンスの参照を返す。
    *@return インスタンスの参照
    */
    public static TimerController getInstance(){
        return timerInst;
    }

    /**
    *経過秒数を返す。
    *@return 経過秒数
    */
    public int getElapsedSec(){
        return secCounter;
    }

    /**
    *経過時間を「00 : 00 : 00」のような文字列で返す。
    *@return 経過時間
    */
    public String getTime(){
        if(h == 0){
            return castToString(m) + " : " + castToString(s) + " : " + castToString(ms);
        }else{
            return castToString(h) + " : " +castToString(m) + " : " + castToString(s) + " : " + castToString(ms);
        }
    }

    /**
    *引数を2桁、0埋め、右詰めの文字列に変換する。
    *@param i 変換したい数字
    *@return 変換結果
    */
    public String castToString(int i){
        return String.format("%02d", i);
    }
}
