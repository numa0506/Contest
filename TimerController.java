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
    public static TimerController timerInst;
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
            ms = countUp(ms, 99);
            msecLabel.setText(castToString(ms));
        }
    }));

    Timeline secTimer = new Timeline(
    new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            s = countUp(s, 59);
            secCounter++;
            secLabel.setText(castToString(s));
        }
    }));

    Timeline minTimer = new Timeline(
    new KeyFrame(Duration.minutes(1), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            m = countUp(m, 59);
            minLabel.setText(castToString(m));
        }
    }));

    public void initialize(URL url, ResourceBundle rb){
        msecTimer.setCycleCount(Timeline.INDEFINITE);
        secTimer.setCycleCount(Timeline.INDEFINITE);
        minTimer.setCycleCount(Timeline.INDEFINITE);
        startTimer();
    }

    public int countUp(int t, int limit){
        t = (t == limit ? 0 : t + 1);
        return t;
    }

    public String castToString(int i){
        return String.format("%02d", i);
    }

    public TimerController getInstance(){
        return timerInst;
    }

    public void startTimer(){
        m = 0;
        s = 0;
        ms = 0;
        secCounter = 0;
        msecLabel.setText("00");
        secLabel.setText("00");
        minLabel.setText("00");
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
}
