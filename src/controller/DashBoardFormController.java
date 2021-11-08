package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class DashBoardFormController extends Component {
    public StackPane manageContext;
    public StackPane dashBoardStackPane;
    public JFXButton manageUserContext;

    public void initialize() {
       manageUserContext.fire();
    }

    public void manageUserOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ManageUserForm.fxml"));
        pane = fxmlLoader.load();
        manageContext.getChildren().setAll(pane);
    }

    public void manageTeacherOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ManageTeacherForm.fxml"));
        pane = fxmlLoader.load();
        manageContext.getChildren().setAll(pane);

    }

    public void manageSubjectOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ManageSubjectForm.fxml"));
        pane = fxmlLoader.load();
        manageContext.getChildren().setAll(pane);

    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to logout?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(response==JOptionPane.YES_OPTION) {
            Stage window = (Stage) dashBoardStackPane.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LogInForm.fxml"))));
            window.centerOnScreen();
        }else{
        }
    }

    public void ManageStudentOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ManageStudentForm.fxml"));
        pane = fxmlLoader.load();
        manageContext.getChildren().setAll(pane);

    }

    public void openClassAndFeeOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ManageClassAndFeeForm.fxml"));
        pane = fxmlLoader.load();
        manageContext.getChildren().setAll(pane);
    }

    public void systemReportsOnAction(ActionEvent actionEvent) throws IOException {

        try {
            JasperDesign   design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/sqlReport_A4.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
