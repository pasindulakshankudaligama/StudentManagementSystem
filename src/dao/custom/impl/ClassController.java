package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ClassManage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Class;
import view.tm.ClassTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassController implements ClassManage {
    @Override
    public boolean add(Class c) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Class VALUES(?,?)", c.getClassId(), c.getGrade());

    }

    @Override
    public boolean delete(String classId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Class WHERE Class_ID='" + classId + "'");

    }

    @Override
    public boolean update(ClassTM aClass) throws SQLException, ClassNotFoundException {
        return false;
    }


    @Override
    public ArrayList<Class> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ObservableList<ClassTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<ClassTM> classes = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Class");
        while (rst.next()) {
            classes.add(new ClassTM(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }
        return classes;
    }
    @Override
    public List<String> getAllClassIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Class");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
    @Override
    public Class passClassDetails(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Class WHERE Class_ID='" + id + "'");
        if (rst.next()) {
            return new Class(
                    rst.getString(1),
                    rst.getString(2)
            );

        } else {
            return null;
        }
    }
}

