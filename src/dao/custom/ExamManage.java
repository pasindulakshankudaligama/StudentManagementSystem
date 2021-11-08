package dao.custom;

import dao.CrudDAO;
import model.Exam;
import model.ExamDetail;
import view.tm.ExamTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ExamManage extends CrudDAO<Exam,String, ExamTM> {
    boolean saveExamDetails(String examId, ArrayList<ExamDetail> exams) throws SQLException, ClassNotFoundException;

}
