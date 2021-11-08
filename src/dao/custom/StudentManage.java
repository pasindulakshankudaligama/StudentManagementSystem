package dao.custom;

import dao.CrudDAO;
import model.Student;
import view.tm.StudentTM;

import java.sql.SQLException;
import java.util.List;

public interface StudentManage extends CrudDAO<Student,String, StudentTM> {

    Student getStudent(String Stu_ID) throws SQLException, ClassNotFoundException;

    List<String> getStudentIds() throws SQLException, ClassNotFoundException;

    List<Student> searchStudent(String value) throws SQLException, ClassNotFoundException;

}
