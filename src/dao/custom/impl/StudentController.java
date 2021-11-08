package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.StudentManage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Student;
import view.tm.StudentTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentController implements StudentManage {
    @Override
    public boolean add(Student student) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate
                ("INSERT INTO Student VALUES(?,?,?,?,?,?)",
                        student.getStudentId(), student.getStudentName(), student.getAddress(), student.getPhoneNumber(), student.getDateOfBirth(), student.getAge());

    }

    @Override
    public boolean delete(String studentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Student WHERE Stu_ID='" + studentId + "'");
    }

    @Override
    public boolean update(StudentTM student) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate
                ("UPDATE Student SET Student_name=?, Address=?, Phone_Number=?, DOB=?, Age=?  WHERE Stu_ID=?",
                        student.getStudentName(), student.getAddress(), student.getPhoneNumber(), student.getDateOfBirth(), student.getAge(), student.getStudentId());

    }

    @Override
    public ArrayList<Student> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Student");
        ArrayList<Student> students = new ArrayList<>();
        while (rst.next()) {
            students.add(new Student(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getInt(6)
            ));
        }
        return students;
    }

    @Override
    public ObservableList<StudentTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<StudentTM> students = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Student");
        while (rst.next()) {
            students.add(new StudentTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getInt(6)
            ));
        }
        return students;
    }
    @Override
    public Student getStudent(String Stu_ID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Student WHERE Stu_ID=?");
        if (rst.next()) {
            return new Student(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getInt(6)
            );
        } else {
            return null;
        }
    }

    @Override
    public List<String> getStudentIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Student");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
    @Override
    public List<Student> searchStudent(String value) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Student WHERE Student_name LIKE '%" + value + "%'");
      List<Student> students = new ArrayList<>();
        while (rst.next()) {
            students.add(new Student(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getInt(6)
            ));
        }
        return students;
    }
}
