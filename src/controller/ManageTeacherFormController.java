package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.ClassController;
import dao.custom.impl.SubjectController;
import dao.custom.impl.TeacherController;
import dao.custom.ClassManage;
import dao.custom.SubjectManage;
import dao.custom.TeacherManage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Class;
import model.ClassDetail;
import model.Subject;
import model.Teacher;
import util.ValidationUtil;
import view.tm.TeacherTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageTeacherFormController {
    public JFXTextField txtTeacherId;
    public JFXTextField txtTeacherName;
    public JFXTextField txtAddress;
    public JFXTextField txtPhoneNumber;
    public TableView<TeacherTM> tblAddTeacher;
    public TableColumn colTeacherId;
    public TableColumn colTeacherName;
    public TableColumn colAddress;
    public TableColumn colTelephoneNumber;
    public JFXComboBox<String> cmbClassID;
    public JFXComboBox<String> cmbSubjectId;
    public JFXTextField txtGrade;
    public JFXTextField txtSubject;
    public JFXButton btnAddTeacher;

    private final TeacherManage dao = new TeacherController();
    private final ClassManage dao1 = new ClassController();
    private final SubjectManage dao2 = new SubjectController();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern teacherIdPattern = Pattern.compile("^(T)[-]?[0-9]{3}$");
    Pattern teacherNamePattern = Pattern.compile("^[A-z ]{5,30}$");
    Pattern addressPattern = Pattern.compile("^[A-z 0-9 \\/\\,]{2,50}[A-z 0-9]{1,50}$");
    Pattern phoneNumberPattern = Pattern.compile("^(07|03|01)[0-9]{8}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        btnAddTeacher.setDisable(true);
        storeValidations();
        try {
            setTeachersToTable();
            loadSubjectIds();
            loadClassIds();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbSubjectId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setSubjectData(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cmbClassID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setClassData(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void storeValidations() {
        map.put(txtTeacherId, teacherIdPattern);
        map.put(txtTeacherName, teacherNamePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtPhoneNumber, phoneNumberPattern);
    }

    public void setTeachersToTable() throws SQLException, ClassNotFoundException {
        ObservableList<TeacherTM> teacher = dao.getList();

        colTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTelephoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        tblAddTeacher.setItems(teacher);

    }

    private void loadClassIds() throws SQLException, ClassNotFoundException {
        List<String> subjectIds = dao1.getAllClassIds();
        cmbClassID.getItems().addAll(subjectIds);

    }

    private void loadSubjectIds() throws SQLException, ClassNotFoundException {
        List<String> subjectIds = dao2.getAllSubjectIds();
        cmbSubjectId.getItems().addAll(subjectIds);
    }

    public void AddTeacherOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        List<ClassDetail> classes = new ArrayList<>();

        classes.add(new ClassDetail(
                txtTeacherId.getText(),
                cmbSubjectId.getValue(),
                cmbClassID.getValue(),
                txtTeacherName.getText()
        ));

        Teacher t1 = new Teacher(
                txtTeacherId.getText(),
                txtTeacherName.getText(),
                txtAddress.getText(),
                Integer.parseInt(txtPhoneNumber.getText()),
                classes
        );
        if (dao.add(t1)) {
            new AddNotifications().sceneNotifications("Saved", "save teacher successfully", 0, 3);

            setTeachersToTable();
            clearField();
        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);

        }
    }

    private void clearField() {
        txtTeacherId.clear();
        txtTeacherName.clear();
        txtAddress.clear();
        txtPhoneNumber.clear();
        txtTeacherId.requestFocus();
    }

    public void UpdateTeacherOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TeacherTM t = new TeacherTM(
                txtTeacherId.getText(),
                txtTeacherName.getText(),
                txtAddress.getText(),
                Integer.parseInt(txtPhoneNumber.getText())
        );
        if (dao.update(t)) {
            new AddNotifications().sceneNotifications("Updated", "update teacher Successfully", 0, 0);

            setTeachersToTable();
            clearField();

        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);


        }
    }

    public void ClearOnAction(ActionEvent actionEvent) {
        clearField();
    }

    public void DeleteTeacherOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TeacherTM teacher = tblAddTeacher.getSelectionModel().getSelectedItem();
        String teacherId = teacher.getTeacherId();

        if (dao.delete(teacherId)) {
            new AddNotifications().sceneNotifications("Deleted", "delete teacher Successfully", 0, 1);

            setTeachersToTable();

        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);

        }
        clearField();

    }

    public void handleTeacherMouseEvent(MouseEvent mouseEvent) {
        try {
            TeacherTM teacher = null;

            teacher = tblAddTeacher.getSelectionModel().getSelectedItem();
            txtTeacherId.setText(teacher.getTeacherId());
            txtTeacherName.setText(teacher.getTeacherName());
            txtAddress.setText(teacher.getAddress());
            txtPhoneNumber.setText("" + teacher.getPhoneNumber());
        } catch (Exception e) {

        }
    }

        private void setSubjectData(String id) throws SQLException, ClassNotFoundException {
        Subject s1 = dao2.passSubjectDetails(id);
        if (s1 == null) {
            new AddNotifications().sceneNotifications("Warning", "Empty Result Set", 0, 4);
        } else {
            txtSubject.setText(s1.getSubjectName());
        }
    }

    private void setClassData(String id) throws SQLException, ClassNotFoundException {
        Class c1 = dao1.passClassDetails(id);
        if (c1 == null) {
            new AddNotifications().sceneNotifications("Warning", "Empty Result Set", 0, 4);
        } else {
            txtGrade.setText(c1.getGrade());
        }
    }

    public void keyEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddTeacher);
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

