package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    private Label time;

    @FXML
    private ImageView image;

    @FXML
    private Label number;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            image.setImage(new Image(new FileInputStream(new File("src/sample/HNY.gif"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> doSomething()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        MediaPlayer m = new MediaPlayer(new Media(new File("src/sample/HNY.mp3").toURI().toString()));
        m.setCycleCount(MediaPlayer.INDEFINITE);
        m.play();
    }

    public void doSomething() {
        Calendar c = Calendar.getInstance();
        int seconds = 84_600 - c.get(Calendar.HOUR) * 3600 - c.get(Calendar.MINUTE) * 60 - c.get(Calendar.SECOND);
        int h = seconds / 3_600;
        int m = (seconds % 3_600) / 60;
        int s = (seconds % 3_600) % 60;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        c.set(0, 1, 0, h, m, s);

        if (time.getText().equals("00:00:00") == false) {
            time.setText(sdf.format(c.getTime()));
        }

        if (seconds < 11 && seconds > -1) {
            number.setText(seconds + "");
        }
    }

}
