package controller;

import animatefx.animation.Bounce;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitPreloader implements Initializable {

    public static Label lblLoadingg;
    public Label lblLoading;
    public AnchorPane loaderContext;
    public Circle Circle1;
    public Circle Circle2;
    public Circle Circle3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new Bounce(Circle1).setCycleCount(14).setDelay(Duration.valueOf("500ms")).play();
        new Bounce(Circle2).setCycleCount(14).setDelay(Duration.valueOf("1000ms")).play();
        new Bounce(Circle3).setCycleCount(14).setDelay(Duration.valueOf("1100ms")).play();
        lblLoadingg = lblLoading;

    }

    public String checkFunctions() {
        final String[] message = {"Loading"};
        Thread t1 = new Thread(() -> {
            message[0] = "Loading";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        Thread t2 = new Thread(() -> {
            message[0] = "Loading";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            message[0] = "Open Login Form";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        Stage stage = new Stage();
                        Parent load = FXMLLoader.load(getClass().getResource("../view/LogInForm.fxml"));
                        Scene scene = new Scene(load);
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        });

        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return message[0];
    }

}

