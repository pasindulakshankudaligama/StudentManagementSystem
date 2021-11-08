package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RegisterStudentManage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RegisterStudent;
import view.tm.RegisterStudentTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisteredStudentController implements RegisterStudentManage {
    @Override
    public boolean add(RegisterStudent r) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Registered_Student Values(?,?)", r.getStudentId(), r.getStudentName());

    }

    @Override
    public boolean delete(String registerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Registered_Student WHERE Reg_ID='" + registerId + "'");

    }

    @Override
    public boolean update(RegisterStudentTM r) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Registered_Student SET Student_Name=? WHERE Reg_ID=?", r.getStudentName(), r.getStudentId());

    }

    @Override
    public ArrayList<RegisterStudent> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ObservableList<RegisterStudentTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<RegisterStudentTM> reg = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Registered_Student");
        while (rst.next()) {
            reg.add(new RegisterStudentTM(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }
        return reg;
    }

    public List<String> getRegisteredStudentIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Registered_Student");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1));
        }
        return ids;
    }

    @Override
    public List<RegisterStudent> searchRegisterStudent(String value) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Registered_Student WHERE Student_Name LIKE '%" + value + "%'");
        List<RegisterStudent> registerStudents = new ArrayList<>();
        while (rst.next()) {
            registerStudents.add(new RegisterStudent(
                    rst.getString(1),
                    rst.getString(2)));
        }
        return registerStudents;
    }
    @Override
    public String getRegisterId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Registered_Student ORDER BY Reg_ID DESC LIMIT 1");
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;

            if (tempId < 10) {
                return "R-00" + tempId;

            } else if (tempId < 100) {
                return "R-0" + tempId;

            } else {
                return "R-" + tempId;
            }

        } else {
            return "R-001";

        }

    }
    @Override
    public RegisterStudent passRegisteredStudentDetails(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Registered_Student WHERE Reg_ID='" + id + "'");
        if (rst.next()) {
            return new RegisterStudent(
                    rst.getString(1),
                    rst.getString(2));
        } else {
            return null;
        }
    }
}