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

public class TimerController implements Initializable{
    public static TimerController timerInst; //これは使わなければ消す
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
                minLabel.setText(castToString(m));
            }else{
                m = 0;
                h += 1;
                minTimer.stop();
                minTimer.play();
                minLabel.setText("" + h + " : " + castToString(m));
            }
        }
    }));

    public void initialize(URL url, ResourceBundle rb){
        initTimer();
        startTimer();
    }

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
    }

    public void startTimer(){
        msecTimer.play();
        secTimer.play();
        minTimer.play();
        timerInst = this;
    }

    public void stopTimer(){
        msecTimer.stop();
        secTimer.stop();
        minTimer.stop();
    }

    public TimerController getInstance(){
        return timerInst;
    }

    public int getElapsedSec(){
        return secCounter;
    }

    public String getTime(){
        if(h == 0){
            return castToString(m) + " : " + castToString(s) + " : " + castToString(ms);
        }else{
            return "" + h + castToString(m) + " : " + castToString(s) + " : " + castToString(ms);
        }
    }

    public String castToString(int i){
        return String.format("%02d", i);
    }
}
