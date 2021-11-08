package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.Objects;

public class AddNotifications {

    public void sceneNotifications(String title, String txt, int showMode, int image) {

        Image img;
        switch (image) {
            case 1:
                img = new Image("/assets/icon/delete48.png");
                break;
            case 2:
                img = new Image("/assets/icon/DeleteBin48.png");
                break;
            case 3:
                img = new Image("/assets/icon/Save48.png");
                break;
            case 4:
                img = new Image("/assets/icon/w4.png");
                break;
            default:
                img = new Image("/assets/icon/Ok48.png");
                break;
        }

        Notifications notificationBuilder = Notifications.create();
        Objects.requireNonNull(Notifications.class.getResource("/assets/styles/style.css")).toExternalForm();

        notificationBuilder.title(title)
                .text(txt)
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });

        switch (showMode) {
            case 1:
                notificationBuilder.darkStyle();
                notificationBuilder.showConfirm();
                break;
            case 2:
                notificationBuilder.darkStyle();
                notificationBuilder.showInformation();
                break;
            case 3:
                notificationBuilder.darkStyle();
                notificationBuilder.showError();
                break;
            case 4:
                notificationBuilder.darkStyle();
                notificationBuilder.showWarning();
                break;
            default:
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                break;
        }

    }
}
