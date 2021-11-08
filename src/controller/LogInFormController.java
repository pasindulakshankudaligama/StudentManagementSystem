package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.UserController;
import dao.custom.UserManage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class LogInFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public AnchorPane logInContext;

    private final UserManage dao = new UserController();

    public void logInOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        User u = new User(
                txtUserName.getText(),
                txtPassword.getText()
        );
        String role = dao.login(u);

        if (role.equals("Admin")) {
            Stage window = (Stage) logInContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
            window.centerOnScreen();

        } else if (role.equals("Coordinator")) {
            Stage window = (Stage) logInContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CoordinatorForm.fxml"))));
            window.centerOnScreen();

        } else {
            new AddNotifications().sceneNotifications("Wrong", "Invalid user name or password", 0, 4);
        }
    }
}
