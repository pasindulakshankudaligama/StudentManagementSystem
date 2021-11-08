package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.UserManage;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import view.tm.UserTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController implements UserManage {
    @Override
    public boolean add(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate
                ("INSERT INTO `User` VALUES(?,?,?,?,?,md5(?))", user.getUserName(), user.getName(), user.getAddress(), user.getTelephoneNumber(), user.getRole(), user.getUserPassword());

    }

    @Override
    public boolean delete(String userName) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `User` WHERE User_Name='" + userName + "'");


    }

    @Override
    public boolean update(UserTM user) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `User` SET  Name=?, Address=?, Tele_No=?, Role=? WHERE User_Name=?", user.getName(), user.getAddress(), user.getTelephoneNumber(), user.getRole(), user.getUserName());

    }

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ObservableList<UserTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<UserTM> users = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `User`");
        while (rst.next()) {
            users.add(new UserTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)

            ));
        }
        return users;
    }

    @Override
    public String login(User u) throws SQLException, ClassNotFoundException {
        String username = u.getUserName();
        String password = u.getUserPassword();

        //ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `User` WHERE User_Name=? AND User_Password=md5(?) ");
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `User` WHERE User_Name=? AND User_Password=md5(?) ");
        stm.setObject(1, username);
        stm.setObject(2, password);
        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            return rst.getString(5);

        } else {

            return "";
        }
    }

}
