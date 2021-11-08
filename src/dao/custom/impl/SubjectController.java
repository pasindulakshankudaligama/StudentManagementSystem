package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.SubjectManage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Subject;
import view.tm.SubjectTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectController implements SubjectManage {
    @Override
    public boolean add(Subject subject) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Subject VALUES(?,?)", subject.getSubjectId(), subject.getSubjectName());
    }

    @Override
    public boolean delete(String subjectId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Subject WHERE Sub_ID='" + subjectId + "'");
    }

    @Override
    public boolean update(SubjectTM subject) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Subject SET Subject_Name=? WHERE Sub_ID=?", subject.getSubjectName(), subject.getSubjectId());
    }

    @Override
    public ArrayList<Subject> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ObservableList<SubjectTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<SubjectTM> subjects = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Subject");
        while (rst.next()) {
            subjects.add(new SubjectTM(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }

        return subjects;
    }
    @Override
    public List<String> getAllSubjectIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Subject");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public Subject passSubjectDetails(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Subject WHERE Sub_ID='" + id + "'");
        if (rst.next()) {
            return new Subject(
                    rst.getString(1),
                    rst.getString(2)
            );
        } else {
            return null;
        }
    }
}
