package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.TeacherManage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClassDetail;
import model.Teacher;
import view.tm.TeacherTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherController implements TeacherManage {
    @Override
    public boolean add(Teacher t) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con.setAutoCommit(false);
            if (CrudUtil.executeUpdate("INSERT INTO Teacher VALUES(?,?,?,?)",t.getTeacherId(),t.getTeacherName(),t.getAddress(),t.getPhoneNumber())) {
                if (saveClassDetails(t.getClasses())) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            } else {
                con.rollback();
                return false;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(String teacherId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Teacher WHERE Tea_ID= '" + teacherId + "'");

    }

    @Override
    public boolean update(TeacherTM t) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Teacher SET Teacher_Name=?, Address=?, Phone_Number=? WHERE Tea_ID=?", t.getTeacherName(), t.getAddress(), t.getPhoneNumber(), t.getTeacherId());

    }

    @Override
    public ArrayList<Teacher> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ObservableList<TeacherTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<TeacherTM> teachers = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Teacher");
        while (rst.next()) {
            teachers.add(new TeacherTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4)));
        }
        return teachers;
    }
    @Override
    public boolean saveClassDetails(List<ClassDetail> classes) throws SQLException, ClassNotFoundException {

        for (ClassDetail temp : classes
        ) {
            return CrudUtil.executeUpdate("INSERT INTO Class_Detail VALUES(?,?,?,?)", temp.getTeacherId(), temp.getSubjectId(), temp.getClassId(), temp.getTeacherName());
        }
        return true;
    }
}
