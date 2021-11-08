package controller;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherPreloader extends Preloader {

    private Stage proLoaderStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.proLoaderStage = primaryStage;
        Parent load = FXMLLoader.load(getClass().getResource("../view/InitPreloader.fxml"));
        Scene scene = new Scene(load);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {

        if (info.getType() == StateChangeNotification.Type.BEFORE_START) {
            proLoaderStage.hide();
        }
    }
}
