package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.ExamController;
import dao.custom.impl.RegisteredStudentController;
import dao.custom.ExamManage;
import dao.custom.RegisterStudentManage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Exam;
import model.ExamDetail;
import model.RegisterStudent;
import util.ValidationUtil;
import view.tm.ExamTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class AddExamFormController {
    public JFXTextField txtExamId;
    public JFXTextField txtSubject;
    public TableView<ExamTM> tblExam;
    public TableColumn colExamId;
    public TableColumn colSubject;
    public AnchorPane ExamContext;
    public JFXButton btnCancel;
    public Label lblDate;
    public JFXComboBox<String> cmbRegisterId;
    public JFXTextField txtStudentName;
    public JFXButton btnAddExam;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern examIdPattern = Pattern.compile("^(E)[-]?[0-9]{3}$");
    Pattern subjectPattern = Pattern.compile("^[A-z]{4,15}$");

    private final ExamManage dao = new ExamController();
    private  final RegisterStudentManage dao1= new RegisteredStudentController();

    public void initialize() {
        btnAddExam.setDisable(true);
        storeValidations();
        loadDate();
        try {
            setExamToTable();
            loadRegisterStudentsIds();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbRegisterId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setRegStudentData(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void storeValidations(){
        map.put(txtExamId, examIdPattern);
        map.put(txtSubject, subjectPattern);
    }

    private void setExamToTable() throws SQLException, ClassNotFoundException {
        ObservableList<ExamTM> exam = dao.getList();

        colExamId.setCellValueFactory(new PropertyValueFactory<>("examId"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        tblExam.setItems(exam);
    }

    public void addExamOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<ExamDetail> exams = new ArrayList<>();

        exams.add(
                new ExamDetail(
                        lblDate.getText(),
                        txtExamId.getText(),
                        cmbRegisterId.getValue()
                ));

        Exam e1 = new Exam(
                txtExamId.getText(),
                txtSubject.getText(),
                exams
        );
        if (dao.add(e1)) {
            new AddNotifications().sceneNotifications("Saved", "Successfully Saved To Exam", 0, 3);
            setExamToTable();
        } else {
            new AddNotifications().sceneNotifications("Try again", "you may make a fault", 0, 4);
        }

    }

    public void deleteExamOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ExamTM examTM = tblExam.getSelectionModel().getSelectedItem();
        String examId = examTM.getExamId();

        if (dao.delete(examId)) {
            new AddNotifications().sceneNotifications("Deleted", "Deleted Exam", 0, 1);

            setExamToTable();
        } else {
            new AddNotifications().sceneNotifications("Something Wrong ", "Try Again", 0, 4);

        }
        clearField();
    }

    private void clearField() {
        txtExamId.clear();
        txtSubject.clear();
        txtExamId.requestFocus();
    }

    public void CancelOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    private void loadRegisterStudentsIds() throws SQLException, ClassNotFoundException {
        List<String> regIds = dao1.getRegisteredStudentIds();
        cmbRegisterId.getItems().addAll(regIds);
    }

    private void setRegStudentData(String id) throws SQLException, ClassNotFoundException {
        RegisterStudent r1 =dao1.passRegisteredStudentDetails(id);
        if (r1 == null) {
            new AddNotifications().sceneNotifications("Warning", "Empty Result Set", 0, 4);
        } else {
            txtStudentName.setText(r1.getStudentName());
        }
    }
    public void keyEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddExam);
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
