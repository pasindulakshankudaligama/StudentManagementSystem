package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.SubjectController;
import dao.CrudDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Subject;
import util.ValidationUtil;
import view.tm.SubjectTM;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageSubjectFormController {

    public JFXTextField txtSubjectId;
    public JFXTextField txtSubjectName;
    public TableView<SubjectTM> tblSubject;
    public TableColumn colSubjectId;
    public TableColumn colSubjectName;
    public JFXButton btnAddSubject;

    private final CrudDAO<Subject,String,SubjectTM> dao = new SubjectController();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern subjectIdPattern = Pattern.compile("^(Sub|sub)[-]?[0-9]{3}$");
    Pattern subjectNamePattern = Pattern.compile("^[A-z]{4,15}$");

    public void initialize() {
        btnAddSubject.setDisable(true);
        storeValidations();
        try {
            setSubjectToTable();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void storeValidations() {
        map.put(txtSubjectId, subjectIdPattern);
        map.put(txtSubjectName, subjectNamePattern);
    }

    private void setSubjectToTable() throws SQLException, ClassNotFoundException {
        ObservableList<SubjectTM> subject = dao.getList();
        colSubjectId.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

        tblSubject.setItems(subject);
    }

    public void addSubjectOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Subject s1 = new Subject(
                txtSubjectId.getText(),
                txtSubjectName.getText()
        );
        if (dao.add(s1)) {
            new AddNotifications().sceneNotifications("Saved", "saved subject Successfully", 0, 3);

            clearField();
            setSubjectToTable();
        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);

        }

    }

    public void deleteSubjectOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SubjectTM subject = tblSubject.getSelectionModel().getSelectedItem();
        String subjectId = subject.getSubjectId();

        if (dao.delete(subjectId)) {
            new AddNotifications().sceneNotifications("Deleted", "delete subject Successfully", 0, 1);

            clearField();
            setSubjectToTable();
        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);

        }

    }

    public void clearFieldOnAction(ActionEvent actionEvent) {
        clearField();
    }

    private void clearField() {
        txtSubjectId.clear();
        txtSubjectName.clear();
        txtSubjectId.requestFocus();
    }

    public void keyEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddSubject);
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
