package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.UserManage;
import dao.custom.impl.UserController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.User;
import util.ValidationUtil;
import view.tm.UserTM;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;


public class ManageUserFormController {
    public JFXTextField txtUserName;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtTelePhoneNumber;
    public JFXTextField txtRole;
    public JFXTextField txtPassword;
    public JFXButton btnAddUser;
    public TableView<UserTM> tblUser;
    public TableColumn colUserName;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colTelephoneNumber;
    public TableColumn colRole;
    public JFXButton btnUpdateUser;

    private final UserManage dao = new UserController();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern userNamePattern = Pattern.compile("^[A-z0-9]{3,20}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{5,30}$");
    Pattern addressPattern = Pattern.compile("^[A-z 0-9 \\/\\,]{2,50}[A-z 0-9]{1,50}$");
    Pattern phoneNumberPattern = Pattern.compile("^(07|03|01)[0-9]{8}$");
    Pattern rolePattern = Pattern.compile("^(Admin|Coordinator)$");
    Pattern passwordPattern = Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");

    public void initialize() throws SQLException, ClassNotFoundException {
        btnAddUser.setDisable(true);
        storeValidations();
        setUsersToTable();
    }

    private void storeValidations() {
        map.put(txtUserName, userNamePattern);
        map.put(txtName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtTelePhoneNumber, phoneNumberPattern);
        map.put(txtRole, rolePattern);
        map.put(txtPassword, passwordPattern);
    }

    public void setUsersToTable() throws SQLException, ClassNotFoundException {
        ObservableList<UserTM> user = dao.getList();
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTelephoneNumber.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        tblUser.setItems(user);
    }

    public void addUserOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        User u1 = new User(
                txtUserName.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtTelePhoneNumber.getText(),
                txtRole.getText(),
                txtPassword.getText()

        );

        if (dao.add(u1)) {
            new AddNotifications().sceneNotifications("Saved", "save user Successfully", 0, 3);
            setUsersToTable();

        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);

        }
    }

    private void clearField() {
        txtUserName.clear();
        txtName.clear();
        txtAddress.clear();
        txtTelePhoneNumber.clear();
        txtRole.clear();
        txtPassword.clear();
        txtUserName.requestFocus();
    }

    public void clearOnAction(ActionEvent actionEvent) {

        clearField();
    }

    public void deleteUserOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        UserTM user = tblUser.getSelectionModel().getSelectedItem();
        String userName = user.getUserName();

        if (dao.delete(userName)) {
            new AddNotifications().sceneNotifications("Deleted", "delete user Successfully", 0, 1);

            setUsersToTable();

        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);

        }
        clearField();
    }

    public void handleMouseOnAction(MouseEvent mouseEvent) {
        try {
            UserTM user = null;


            user = tblUser.getSelectionModel().getSelectedItem();
            txtUserName.setText(user.getUserName());
            txtName.setText(user.getName());
            txtAddress.setText(user.getAddress());
            txtTelePhoneNumber.setText(user.getTelephoneNumber());
            txtRole.setText(user.getRole());

        } catch (Exception e) {

        }
    }
        public void updateUserOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        UserTM user = new UserTM(
                txtUserName.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtTelePhoneNumber.getText(),
                txtRole.getText()

        );

        if (dao.update(user)) {
            new AddNotifications().sceneNotifications("Updated", "update user Successfully", 0, 0);
            setUsersToTable();

        } else {
            new AddNotifications().sceneNotifications("", "Try again", 0, 4);

        }
    }

    public void keyEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddUser);
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

