package dao.custom;

import dao.CrudDAO;
import model.ClassDetail;
import model.Teacher;
import view.tm.TeacherTM;

import java.sql.SQLException;
import java.util.List;

public interface TeacherManage extends CrudDAO<Teacher,String, TeacherTM> {
    boolean saveClassDetails(List<ClassDetail> classes) throws SQLException, ClassNotFoundException;

}
