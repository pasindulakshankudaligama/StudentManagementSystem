package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.ClassController;
import dao.custom.impl.FeeController;
import dao.CrudDAO;
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
import model.Fee;
import util.ValidationUtil;
import view.tm.ClassTM;
import view.tm.FeeTM;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageClassAndFeeFormController {
    public JFXTextField txtClassId;
    public JFXTextField txtGrade;
    public TableView<ClassTM> tblClass;
    public TableColumn colClassId;
    public TableColumn colGrade;
    public JFXTextField txtFeeId;
    public TableView<FeeTM> tblFee;
    public TableColumn colFeeId;
    public TableColumn colFeeAmount;
    public JFXTextField txtFeeAmount;
    public JFXButton btnAddClass;
    public JFXButton btnUpdateFee;
    private final CrudDAO<Class,String,ClassTM> dao = new ClassController();
    private final CrudDAO<Fee,String,FeeTM> dao1 = new FeeController();

    LinkedHashMap<TextField, Pattern> map1 = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> map2 = new LinkedHashMap<>();
    Pattern classIdPattern = Pattern.compile("^(C)[-]?[0-9]{3}$");
    Pattern classGradePattern = Pattern.compile("^(0)?[1-9]{1}$|(1)[0-3]{1}$");
    Pattern feeIdPattern = Pattern.compile("^(F)[-]?[0-9]{3}$");
    Pattern feeAmountPattern = Pattern.compile("^[1-9][0-9]{3,4}$");


    public void initialize() {
        btnAddClass.setDisable(true);
        btnUpdateFee.setDisable(true);
        storeValidation();
        storeValidationFee();
        try {
            setClassesToTable();
            setFeesToTable();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void storeValidationFee() {
        map1.put(txtFeeId, feeIdPattern);
        map1.put(txtFeeAmount, feeAmountPattern);
    }

    private void storeValidation() {
        map2.put(txtClassId, classIdPattern);
        map2.put(txtGrade, classGradePattern);
    }

    public void addClassOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Class c1 = new Class(
                txtClassId.getText(),
                txtGrade.getText()
        );
        if (dao.add(c1)) {
            new AddNotifications().sceneNotifications("Saved","Add class Successfully",0,3);

            setClassesToTable();
            clearField();
        } else {
            new AddNotifications().sceneNotifications("","Try again",0,1);

        }
    }

    private void setClassesToTable() throws SQLException, ClassNotFoundException {
        ObservableList<ClassTM> classes = dao.getList();
        colClassId.setCellValueFactory(new PropertyValueFactory<>("classId"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        tblClass.setItems(classes);
    }

    public void removeClassOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ClassTM classTM = tblClass.getSelectionModel().getSelectedItem();
        String classId = classTM.getClassId();

        if (dao.delete(classId)) {
            new AddNotifications().sceneNotifications("Deleted","Delete Class Successfully",0,1);

            setClassesToTable();

        } else {
            new AddNotifications().sceneNotifications("","Try again",0,1);

        }
        clearField();
    }

    public void ClearOnActionInClass(ActionEvent actionEvent) {
        clearField();
    }

    private void clearField() {
        txtClassId.clear();
        txtGrade.clear();
        txtClassId.requestFocus();
    }

    private void setFeesToTable() throws SQLException, ClassNotFoundException {
        ObservableList<FeeTM> fee = dao1.getList();
        colFeeId.setCellValueFactory(new PropertyValueFactory<>("feeId"));
        colFeeAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tblFee.setItems(fee);

    }

    public void deleteFeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        FeeTM fee = tblFee.getSelectionModel().getSelectedItem();
        String feeId = fee.getFeeId();

        if (dao1.delete(feeId)) {
            new AddNotifications().sceneNotifications("Deleted","delete Fee Successfully",0,1);

            setFeesToTable();

        } else {
            new AddNotifications().sceneNotifications("","Try again",0,4);

        }
        clearFieldInFee();

    }

    private void clearFieldInFee() {
        txtFeeId.clear();
        txtFeeAmount.clear();
        txtFeeId.requestFocus();
    }

    public void ClearOnActionInFee(ActionEvent actionEvent) {
        clearFieldInFee();
    }

    public void updateFeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        FeeTM fee = new FeeTM(
                txtFeeId.getText(),
                Double.parseDouble(txtFeeAmount.getText())
        );
        if (dao1.update(fee)) {
            new AddNotifications().sceneNotifications("Updated","update Fee Successfully",0,0);

            setFeesToTable();

        } else {
            new AddNotifications().sceneNotifications("","Try again",0,4);

        }
    }

    public void mouseHandleOnAction(MouseEvent mouseEvent) {
        try {
            FeeTM feeTM = tblFee.getSelectionModel().getSelectedItem();
            txtFeeId.setText(feeTM.getFeeId());
            txtFeeAmount.setText("" + feeTM.getAmount());
        } catch (Exception e) {

        }
    }

        public void keyEventFee(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map1, btnUpdateFee);
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }else if(response instanceof Boolean){
                new AddNotifications().sceneNotifications("INFORMATION","Added to TextField",0,0);
            }
        }

    }

    public void keyEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map2, btnAddClass);
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
