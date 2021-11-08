package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ExamManage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Exam;
import model.ExamDetail;
import view.tm.ExamTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamController implements ExamManage {
    @Override
    public boolean add(Exam exam) throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            con.setAutoCommit(false);
            if (CrudUtil.executeUpdate("INSERT INTO Exam VALUES(?,?)",exam.getExamId(),exam.getSubject())) {
                if (saveExamDetails(exam.getExamId(), exam.getExams())) {
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
    public boolean delete(String examId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Exam WHERE Exam_ID='" + examId + "'");
    }

    @Override
    public boolean update(ExamTM exam) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Exam> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ObservableList<ExamTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<ExamTM> exams = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Exam");
        while (rst.next()) {
            exams.add(new ExamTM(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }
        return exams;
    }

    @Override
    public boolean saveExamDetails(String examId, ArrayList<ExamDetail> exams) throws SQLException, ClassNotFoundException {
        for (ExamDetail temp : exams
        ) {
            return CrudUtil.executeUpdate("INSERT INTO Exam_Detail VALUES(?,?,?)", temp.getDate(),temp.getExamId(),temp.getRegisterId());
        }
        return true;
    }


}
