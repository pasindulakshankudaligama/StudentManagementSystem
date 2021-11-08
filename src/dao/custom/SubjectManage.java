package dao.custom;

import dao.CrudDAO;
import model.Subject;
import view.tm.SubjectTM;

import java.sql.SQLException;
import java.util.List;

public interface SubjectManage extends CrudDAO<Subject,String, SubjectTM> {

    List<String> getAllSubjectIds() throws SQLException, ClassNotFoundException;

    Subject passSubjectDetails(String id) throws SQLException, ClassNotFoundException;
}
