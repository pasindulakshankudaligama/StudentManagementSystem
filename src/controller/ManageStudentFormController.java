package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.StudentController;
import dao.custom.StudentManage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Student;
import util.ValidationUtil;
import view.tm.StudentTM;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageStudentFormController {
    public JFXTextField txtStudentId;
    public JFXTextField txtStudentName;
    public JFXTextField txtAddress;
    public JFXTextField txtPhoneNumber;
    public JFXTextField txtDateOfBirth;
    public JFXTextField txtAge;
    public TableView<StudentTM> tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colAddress;
    public TableColumn colPhoneNumber;
    public TableColumn colDateOfBirth;
    public TableColumn colAge;
    public TextField txtSearch;
    public JFXButton btnUpdateStudent;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    private final StudentManage dao = new StudentController();


    Pattern studentIdPattern = Pattern.compile("^(Stu)[-]?[0-9]{3}$");
    Pattern studentNamePattern = Pattern.compile("^[A-z ]{5,30}$");
    Pattern addressPattern = Pattern.compile("^[A-z 0-9 \\/\\,]{2,50}[A-z 0-9]{1,50}$");
    Pattern phoneNumberPattern = Pattern.compile("^(07|03|01)[0-9]{8}$");
    Pattern DOBPattern = Pattern.compile("^((19|2[0-9])[0-9]{2})(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$");
    Pattern agePattern = Pattern.compile("^(0)[5-9]{1}|(1)[1-9]{1}$");


    public void initialize() {
        btnUpdateStudent.setDisable(true);
        storeValidations();
        try {
            setStudentInToTable();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    search(newValue);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void storeValidations() {
        map.put(txtStudentId, studentIdPattern);
        map.put(txtStudentName, studentNamePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtPhoneNumber, phoneNumberPattern);
        map.put(txtDateOfBirth, DOBPattern);
        map.put(txtAge, agePattern);
    }

    private void setStudentInToTable() throws SQLException, ClassNotFoundException {
        ObservableList<StudentTM> student = dao.getList();
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        tblStudent.setItems(student);
    }

    public void deleteStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StudentTM student = tblStudent.getSelectionModel().getSelectedItem();
        String studentId = student.getStudentId();

        if (dao.delete(studentId)) {
            new AddNotifications().sceneNotifications("Deleted", "delete student Successfully", 0, 1);

            setStudentInToTable();

        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);
        }
        clearField();
    }

    public void clearFieldOnAction(ActionEvent actionEvent) {
        clearField();
    }

    private void clearField() {
        txtStudentId.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtPhoneNumber.clear();
        txtDateOfBirth.clear();
        txtAge.clear();
        txtStudentId.requestFocus();
    }

    public void updateStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StudentTM student = new StudentTM(
                txtStudentId.getText(),
                txtStudentName.getText(),
                txtAddress.getText(),
                Integer.parseInt(txtPhoneNumber.getText()),
                Integer.parseInt(txtDateOfBirth.getText()),
                Integer.parseInt(txtAge.getText())

        );
        if (dao.update(student)) {
            new AddNotifications().sceneNotifications("Updated", "Add student Successfully", 0, 0);
            setStudentInToTable();

        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);


        }
    }

    public void studentMouseEvent(MouseEvent mouseEvent) {
        try {
            StudentTM student = tblStudent.getSelectionModel().getSelectedItem();
            txtStudentId.setText(student.getStudentId());
            txtStudentName.setText(student.getStudentName());
            txtAddress.setText(student.getAddress());
            txtPhoneNumber.setText("" + student.getPhoneNumber());
            txtDateOfBirth.setText("" + student.getDateOfBirth());
            txtAge.setText("" + student.getAge());
        } catch (Exception e) {

        }
    }

        public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        List<Student> students = dao.searchStudent(value);
        ObservableList<StudentTM> parentList = FXCollections.observableArrayList();

        for (Student student : students
        ) {
            parentList.add(new StudentTM(
                    student.getStudentId(),
                    student.getStudentName(),
                    student.getAddress(),
                    student.getPhoneNumber(),
                    student.getDateOfBirth(),
                    student.getAge()
            ));
        }
        tblStudent.getItems().setAll(parentList);
    }

    public void keyEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnUpdateStudent);
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }else if(response instanceof Boolean){
                new AddNotifications().sceneNotifications("INFORMATION","Added to TextField",0,0);
            }
        }
    }
}
