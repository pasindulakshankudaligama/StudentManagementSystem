package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.RegisteredStudentController;
import dao.custom.RegisterStudentManage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.RegisterStudent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.RegisterStudentTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class RegisterStudentFormController {
    static ObservableList<RegisterStudentTM> List = FXCollections.observableArrayList();
    public JFXTextField txtStudentName;
    public TableView<RegisterStudentTM> tblRegister;
    public TableColumn colRegisterId;
    public TableColumn colStudentName;
    public AnchorPane registerContext;
    public JFXButton btnCancel;
    public TextField txtSearch;
    public Label lblRegister;
    public Label lblStudentId;
    public JFXButton btnRegister;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    private final RegisterStudentManage dao = new RegisteredStudentController();

    Pattern studentNamePattern = Pattern.compile("^[A-z ]{5,30}$");
    private Object JREmptyDataSource;

    public void initialize() {
        btnRegister.setDisable(true);
        storeValidations();
        try {
            setRegisteredStudentToTable();
            setRegisterId();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

    }

    private void storeValidations() {
        map.put(txtStudentName, studentNamePattern);
    }

    private void setRegisteredStudentToTable() throws SQLException, ClassNotFoundException {
        ObservableList<RegisterStudentTM> register = dao.getList();
        List.addAll(register);
        colRegisterId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        tblRegister.setItems(register);
    }

    public void registerStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        RegisterStudent r1 = new RegisterStudent(
                lblRegister.getText(),
                txtStudentName.getText()
        );
        if (dao.add(r1)) {
            new AddNotifications().sceneNotifications("Saved", "Registered Student Successfully", 0, 3);
            setRegisteredStudentToTable();
            setRegisterId();
            clearField();


        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);
        }
        clearField();
    }

    private void clearField() {
        lblStudentId.setText("");
        txtStudentName.clear();
        txtStudentName.requestFocus();
    }

    public void updateStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        RegisterStudentTM r = new RegisterStudentTM(
                lblStudentId.getText(),
                txtStudentName.getText()
        );
        if (dao.update(r)) {
            new AddNotifications().sceneNotifications("Updated", "update registered student Successfully", 0, 0);
            setRegisteredStudentToTable();
            clearField();
        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);
        }

    }

    public void deleteStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        RegisterStudentTM studentTM = tblRegister.getSelectionModel().getSelectedItem();
        String studentId = studentTM.getStudentId();

        if (dao.delete(studentId)) {
            new AddNotifications().sceneNotifications("Deleted", "delete register student Successfully", 0, 1);
            setRegisteredStudentToTable();

        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);
        }
        clearField();

    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();

    }

    public void onMouseHandle(MouseEvent mouseEvent) {
        try {
            RegisterStudentTM reg = tblRegister.getSelectionModel().getSelectedItem();
            lblStudentId.setText(reg.getStudentId());
            txtStudentName.setText(reg.getStudentName());
        } catch (Exception e) {

        }
    }

        public void searchOnAction(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value) {
        try {
            java.util.List<RegisterStudent> registerStudents = dao.searchRegisterStudent(value);
            ObservableList<RegisterStudentTM> parent = FXCollections.observableArrayList();

            for (RegisterStudent register : registerStudents
            ) {
                parent.add(new RegisterStudentTM(
                        register.getStudentId(),
                        register.getStudentName()
                ));

            }
            tblRegister.getItems().setAll(parent);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setRegisterId() {
        try {
            lblRegister.setText(dao.getRegisterId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void keyEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnRegister);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new AddNotifications().sceneNotifications("INFORMATION", "Added to TextField", 0, 0);
            }
        }
    }

    public void onpressed(KeyEvent keyEvent) {
    }

    public void onClick(MouseEvent mouseEvent) {


    }

    public void printIdOnClick(MouseEvent mouseEvent) {
        JasperDesign design = null;
        try {
            design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/Id_Form_A4.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            String registerId = lblStudentId.getText();
            String studentName = txtStudentName.getText();
            HashMap map3 = new HashMap();
            map3.put("Id", registerId);
            map3.put("Name", studentName);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map3, new JREmptyDataSource());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}

