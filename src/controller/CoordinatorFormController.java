package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.StudentController;
import dao.CrudDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Student;
import util.ValidationUtil;
import view.tm.StudentTM;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CoordinatorFormController extends Component {
    private final CrudDAO<Student, String, StudentTM> dao = new StudentController();
    public AnchorPane CoordinatorContext;
    public TableView<StudentTM> tblCoordinator;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colAddress;
    public TableColumn colPhoneNumber;
    public TableColumn colDOB;
    public TableColumn colAge;
    public JFXTextField txtStudentId;
    public JFXTextField TxtStudentName;
    public JFXTextField TxtAddress;
    public JFXTextField TxtPhoneNumber;
    public JFXTextField TxtDOB;
    public JFXTextField TxtAge;
    public JFXButton btnAddStudent;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern studentIdPattern = Pattern.compile("^(Stu)[-]?[0-9]{3}$");
    Pattern studentNamePattern = Pattern.compile("^[A-z ]{5,30}$");
    Pattern addressPattern = Pattern.compile("^[A-z 0-9 \\/\\,]{2,50}[A-z 0-9]{1,50}$");
    Pattern phoneNumberPattern = Pattern.compile("^(07|03|01)[0-9]{8}$");
    Pattern DOBPattern = Pattern.compile("^((19|2[0-9])[0-9]{2})(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$");
    Pattern agePattern = Pattern.compile("^(0)[5-9]{1}|(1)[1-9]{1}$");

    public void initialize() {
        btnAddStudent.setDisable(true);
        storeValidations();
        try {
            setItemToTable();
        } catch (SQLException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void storeValidations() {
        map.put(txtStudentId, studentIdPattern);
        map.put(TxtStudentName, studentNamePattern);
        map.put(TxtAddress, addressPattern);
        map.put(TxtPhoneNumber, phoneNumberPattern);
        map.put(TxtDOB, DOBPattern);
        map.put(TxtAge, agePattern);
    }


    private void setItemToTable() throws SQLException, ClassNotFoundException {
        ObservableList<StudentTM> studentTMS = dao.getList();
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        tblCoordinator.setItems(studentTMS);

    }

    public void addExamOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddExamForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Exam");
        stage.show();
    }

    public void collectFeesOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/CollectFeesForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Fee");
        stage.show();
    }

    public void registrationStudentOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/RegisterStudentForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Register Student");
        stage.show();
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to logout?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            Stage window = (Stage) CoordinatorContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LogInForm.fxml"))));
            window.centerOnScreen();
        } else {
        }

    }

    public void AddStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Student s1 = new Student(
                txtStudentId.getText(),
                TxtStudentName.getText(),
                TxtAddress.getText(),
                Integer.parseInt(TxtPhoneNumber.getText()),
                Integer.parseInt(TxtDOB.getText()),
                Integer.parseInt(TxtAge.getText())
        );
        if (dao.add(s1)) {
            new AddNotifications().sceneNotifications("Saved", "Save Student Successfully", 0, 3);
            setItemToTable();
            clear();
        } else {
            new AddNotifications().sceneNotifications("Wrong", "Not saving to student", 0, 4);

        }
    }

    private void clear() {
        txtStudentId.clear();
        TxtStudentName.clear();
        TxtAddress.clear();
        TxtPhoneNumber.clear();
        TxtDOB.clear();
        TxtAge.clear();
        txtStudentId.requestFocus();
    }

    public void keyEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddStudent);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new AddNotifications().sceneNotifications("INFORMATION", "Added to TextField", 0, 0);
            }
        }

    }
}
